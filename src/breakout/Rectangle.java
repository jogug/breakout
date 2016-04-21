package breakout;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import breakout.Border.Bound;


public abstract class Rectangle extends Sprite {
	private Vec2 ulCornerDistance;

	public Rectangle(Vec2 position, Vec2 ulCornerDistance, Vec2 speed, BufferedImage image, AffineTransform offset, AffineTransform rotation){
		setType(Type.RECT);
		setSpeed(speed);
		setImage(image);
		setPosition(position);
		setOffset(offset);
		setRotation(rotation);
		this.ulCornerDistance = ulCornerDistance;
	}
	
	@Override
	public void collide(Sprite other) {
		switch(other.getType()){
		case BORDER: 	Bound bound = ((Border)other).getBound();
						if(other.getPosition().X()==0){	//Horizontal							
							if(bound==Bound.upper){					
								getPosition().setY(other.getPosition().Y()+ulCornerDistance.Y()+push);
								getSpeed().setY(0);	
							}else{
								getPosition().setY(other.getPosition().Y()-ulCornerDistance.Y()-push);
								getSpeed().setY(0);	
							}
						}else{						// Vertical
							if(bound==Bound.upper){
								getPosition().setX(other.getPosition().X()+ulCornerDistance.X()+push);
								getSpeed().setX(0);	
							}else{
								getPosition().setX(other.getPosition().X()-ulCornerDistance.X()-push);
								getSpeed().setX(0);	
							}
						}

					break;
		case BALL:	break;
		case RECT: break;
		case GFIELD:
			break;
		default:
			break;				
		}	
	}

	@Override
	public double distanceTo(Sprite other) {
		switch(other.getType()){
		case BORDER:Bound bound = ((Border)other).getBound();
					if(other.getPosition().X()==0){	//Horizontal							
						if(bound==Bound.upper){								
							return getPosition().Y() - other.getPosition().Y() - ulCornerDistance.Y();
						}else{
							return other.getPosition().Y() - (getPosition().Y() + ulCornerDistance.Y());
						}
					}else{						// Vertical
						if(bound==Bound.upper){
							return getPosition().X() - other.getPosition().X() - ulCornerDistance.X();
						}else{
							return other.getPosition().X() - (getPosition().X() + ulCornerDistance.X());
						}
					}						
		case BALL:	return 1;
		case RECT:
				//TODO
			return 1;				
		default:
			return 1;	
		}	
	}

	public Vec2 getUlCornerDistance() {
		return ulCornerDistance;
	}

	public void setUlCornerDistance(Vec2 ulCornerDistance) {
		this.ulCornerDistance = ulCornerDistance;
	}
}
