package breakout;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Pad extends Rectangle implements IControlable {
	private double sp = 3;
	private boolean up, down, left, right;
	
	public Pad(Vec2 position, Vec2 ulCornerDistance, Vec2 speed,BufferedImage image, AffineTransform offset, AffineTransform rotation){
		super(position, ulCornerDistance, speed, image, offset, rotation);
	}

	@Override
	public void update() {
		setSpeed(new Vec2());
		if(up){
			//setSpeed(getSpeed().sub(new Vec2(0,sp)));
		}
		if(down){
			//setSpeed(getSpeed().add(new Vec2(0,sp)));
		}
		if(left){
			setSpeed(getSpeed().sub(new Vec2(sp,0)));
		}
		if(right){
			setSpeed(getSpeed().add(new Vec2(sp,0)));
		}
		setPosition(getPosition().add(getSpeed()));
	}



	@Override
	public AffineTransformOp getAffineTransformationOp() {
		AffineTransform at = new AffineTransform();
		at.concatenate(getOffset());
		return new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
	}
	
	public void moveUp(){
		up = true;
	}
	
	public void moveDown(){
		down = true;
	}
	
	public void moveLeft(){
		left = true;
	}
	
	public void moveRight(){
		right = true;
	}
	
	public void resetUp(){
		up = false;
	}
	
	public void resetDown(){
		down = false;
	}
	
	public void resetLeft(){
		left = false;
	}
	
	public void resetRight(){
		right = false;
	}
}
