package breakout;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import breakout.Border.Bound;


public abstract class Sphere extends Sprite {
	private double radius;
	private Vec2 closest; 
	
	public Sphere(Vec2 position,double radius, Vec2 speed, BufferedImage image, AffineTransform offset, AffineTransform rotation){
		setType(Type.SPHERE);
		setImage(image);
		setPosition(position);
		setSpeed(speed);
		setOffset(offset);
		setRotation(rotation);
		this.radius = radius;
	}
	
	@Override
	public double distanceTo(Sprite other) {
		switch(other.getType()){
			case BORDER:Bound bound = ((Border)other).getBound();
						if(other.getPosition().X()==0){	//Horizontal							
							if(bound==Bound.upper){								
								return getPosition().Y() - other.getPosition().Y() - radius;
							}else{
								return other.getPosition().Y() - (getPosition().Y() + radius);
							}
						}else{						// Vertical
							if(bound==Bound.upper){
								return getPosition().X() - other.getPosition().X() - radius;
							}else{
								return other.getPosition().X() - (getPosition().X() + radius);
							}
						}						
			case BALL:	return (radius+((Sphere)other).radius)-getPosition().distanceTo(other.getPosition()) ;			
			case RECT:	Vec2 rect = ((Rectangle)other).getUlCornerDistance();
						return intersectRect(other.getPosition().sub(rect), 
											new Vec2(other.getPosition().X()+rect.X(), other.getPosition().Y()-rect.Y()),
											other.getPosition().add(rect), 
											new Vec2(other.getPosition().X()-rect.X(), other.getPosition().Y()+rect.Y()));
			case GFIELD: return 1;
			default: return 1;
		}	
	}
	

	@Override
	public void collide(Sprite other) {
		switch(other.getType()){
		case BORDER:	Bound bound = ((Border)other).getBound();
						if(other.getPosition().X()==0){		
							setSpeed(getSpeed().flipHorizontal());
							if(bound==Bound.upper){				
								setPosition(new Vec2(getPosition().X(), other.getPosition().Y()+radius+push));
							}else{
								setPosition(new Vec2(getPosition().X(), other.getPosition().Y()-radius-push));
							}							
						}else{	
							setSpeed(getSpeed().flipVertical());
							if(bound==Bound.upper){	
								setPosition(new Vec2(other.getPosition().X()+radius+push, getPosition().Y()));
							}else{
								setPosition(new Vec2(other.getPosition().X()-radius-push, getPosition().Y()));
							}
						}		
						break;
		case BALL:		break;
		case RECT: 		other.collide(this); //TODO terrible
						Vec2 rect = ((Rectangle)other).getUlCornerDistance();
						collideRect(other, other.getPosition().sub(rect), 
							new Vec2(other.getPosition().X()+rect.X(), other.getPosition().Y()-rect.Y()),
							other.getPosition().add(rect), 
							new Vec2(other.getPosition().X()-rect.X(), other.getPosition().Y()+rect.Y()));		
						break;
		case GFIELD:
			break;
		default:
			break;
		}	
	}
	
	private void collideRect(Sprite other, Vec2 A, Vec2 B, Vec2 C, Vec2 D){
		double dAB = closest.distanceToLine(A, B);
		double dAD = closest.distanceToLine(D, A);
		double dBC = closest.distanceToLine(B, C);
		double dCD = closest.distanceToLine(C, D);
		//System.out.println(dAB+","+dAD+","+dBC+","+dCD);	
		if(dAB<0&&dAD<0&&dBC<0&&dCD<0){
			//TODO handle when ball enters pad
		}
		//corners
		if(dAB==0&&dAD==0){
			setSpeed(new Vec2(-Math.abs(getSpeed().X()),-Math.abs(getSpeed().Y())));
			setPosition(getPosition().add(other.getSpeed()).add(new Vec2(-push,-push)));
		}else if(dAD==0&&dCD==0){
			setSpeed(new Vec2(-Math.abs(getSpeed().X()),Math.abs(getSpeed().Y())));
			setPosition(getPosition().add(other.getSpeed()).add(new Vec2(-push,push)));
		}else if(dAB==0&&dBC==0){
			setSpeed(new Vec2(Math.abs(getSpeed().X()),-Math.abs(getSpeed().Y())));
			setPosition(getPosition().add(other.getSpeed()).add(new Vec2(push,-push)));
		}else if(dCD==0&&dBC==0){
			setSpeed(new Vec2(Math.abs(getSpeed().X()),Math.abs(getSpeed().Y())));
			setPosition(getPosition().add(other.getSpeed()).add(new Vec2(push,push)));
		}else if(Math.abs(dAB)<Math.abs(dAD)&&Math.abs(dAB)<Math.abs(dBC)&&Math.abs(dAB)<Math.abs(dCD)){
			//top
			setSpeed(getSpeed().flipHorizontal());
			setPosition(getPosition().add(other.getSpeed()).sub(new Vec2(0,push)));
		}else if(Math.abs(dAD)<Math.abs(dAB)&&Math.abs(dAD)<Math.abs(dBC)&&Math.abs(dAD)<Math.abs(dCD)){
			//Left
			setSpeed(getSpeed().flipVertical());
			setPosition(getPosition().add(other.getSpeed()).sub(new Vec2(push,0)));
		}else if(Math.abs(dBC)<Math.abs(dAD)&&Math.abs(dBC)<Math.abs(dAB)&&Math.abs(dBC)<Math.abs(dCD)){
			//right
			setSpeed(getSpeed().flipVertical());
			setPosition(getPosition().add(other.getSpeed()).add(new Vec2(push,0)));
		}else{
			//bot
			setSpeed(getSpeed().flipHorizontal());
			setPosition(getPosition().add(other.getSpeed()).add(new Vec2(0,push)));
		}
	}
	
	private double intersectRect(Vec2 A, Vec2 B, Vec2 C, Vec2 D){
		//TODO rotation
		closest = new Vec2();
		if(getPosition().X()<A.X()){
			closest.setX(A.X());
		}else if(getPosition().X()>C.X()){
			closest.setX(C.X());
		}else{
			closest.setX(getPosition().X());
		}
		
		if(getPosition().Y()<A.Y()){
			closest.setY(A.Y());
		}else if(getPosition().Y()>C.Y()){
			closest.setY(C.Y());
		}else{
			closest.setY(getPosition().Y());
		}
		return getPosition().distanceTo(closest) - radius;
	}

	@Override
	public AffineTransformOp getAffineTransformationOp() {
		AffineTransform at = new AffineTransform();
		at.concatenate(getOffset());
		at.concatenate(getRotation());
		return new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
	}	
	
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

}
