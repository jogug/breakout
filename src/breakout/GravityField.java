package breakout;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


public class GravityField extends Sprite {
	private double radius;
	private double centerVelocity;
	private Vec2 closest; 

	public GravityField (Vec2 position,double radius,double centerVelocity, Vec2 speed, BufferedImage image, AffineTransform offset, AffineTransform rotation){
		setType(Type.GFIELD);
		setImage(image);
		setPosition(position);
		setSpeed(speed);
		setOffset(offset);
		setRotation(rotation);
		this.centerVelocity = centerVelocity;
		this.radius = radius;	
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(Sprite other) {
		switch(other.getType()){
		case BORDER:	/*Bound bound = ((Border)other).getBound();
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
						}*/		
						break;
		case BALL:		double dist = getPosition().distanceTo(other.getPosition());
						//System.out.println(radius + ","+((Ball)other).getRadius()+","+dist);
						Vec2 vecToCenter = getPosition().sub(other.getPosition()).normalize().multiply((centerVelocity+1)/(dist+4));
						other.setSpeed(other.getSpeed().add(vecToCenter));
						break;
		case RECT: 		Vec2 rect = ((Rectangle)other).getUlCornerDistance();
						double dist2 = intersectRect(other.getPosition().sub(rect), 
								new Vec2(other.getPosition().X()+rect.X(), other.getPosition().Y()-rect.Y()),
								other.getPosition().add(rect), 
								new Vec2(other.getPosition().X()-rect.X(), other.getPosition().Y()+rect.Y()));
								Vec2 vecToCenter2 = getPosition().sub(other.getPosition()).normalize().multiply(centerVelocity/dist2);
								other.setSpeed(other.getSpeed().sub(vecToCenter2));
						break;
		case GFIELD:
			break;
		default:
			break;
		}	
	}

	@Override
	public double distanceTo(Sprite other) {
		switch(other.getType()){
			case BORDER:/*Bound bound = ((Border)other).getBound();
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
						}*/	
						return 1;
			case BALL:	return getPosition().distanceTo(other.getPosition())-(radius+((Ball)other).getRadius());			
			case RECT:	Vec2 rect = ((Rectangle)other).getUlCornerDistance();
						return intersectRect(other.getPosition().sub(rect), 
											new Vec2(other.getPosition().X()+rect.X(), other.getPosition().Y()-rect.Y()),
											other.getPosition().add(rect), 
											new Vec2(other.getPosition().X()-rect.X(), other.getPosition().Y()+rect.Y()));
			case GFIELD:return 1;
			default: 	return 1;
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
