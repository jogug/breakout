package breakout;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Ball extends Sphere {
	
	public Ball(Vec2 position,double radius, Vec2 speed, BufferedImage image, AffineTransform offset, AffineTransform rotation){
		super(position, radius, speed, image, offset, rotation);
		setType(Type.BALL);
	}

	@Override
	public void update() {
		setPosition(getPosition().add(getSpeed()));
		
	}
}
