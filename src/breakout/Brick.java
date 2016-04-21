package breakout;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import breakout.Border.Bound;

public class Brick extends Rectangle {
	public Brick(Vec2 position, Vec2 ulCornerDistance, Vec2 speed,BufferedImage image, AffineTransform offset, AffineTransform rotation){
		super(position, ulCornerDistance, speed, image, offset, rotation);
	}
	@Override
	public void update() {
		setPosition(getPosition().add(getSpeed()));
		
	}
	
	@Override
	public void collide(Sprite other) {
		switch(other.getType()){
		case BORDER: 	Bound bound = ((Border)other).getBound();
						if(other.getPosition().X()==0){	//Horizontal							
							if(bound==Bound.upper){					
								getPosition().setY(other.getPosition().Y()+getUlCornerDistance().Y()+push);
								getSpeed().setY(0);	
							}else{
								getPosition().setY(other.getPosition().Y()-getUlCornerDistance().Y()-push);
								getSpeed().setY(0);	
							}
						}else{						// Vertical
							if(bound==Bound.upper){
								getPosition().setX(other.getPosition().X()+getUlCornerDistance().X()+push);
								getSpeed().setX(0);	
							}else{
								getPosition().setX(other.getPosition().X()-getUlCornerDistance().X()-push);
								getSpeed().setX(0);	
							}
						}
					break;
		case BALL:	delete();
					break;
		case RECT: break;
		case GFIELD:
			break;
		default:
			break;				
		}	
	};

	@Override
	public AffineTransformOp getAffineTransformationOp() {
		AffineTransform at = new AffineTransform();
		at.concatenate(getOffset());
		return new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
	}
}
