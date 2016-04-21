package breakout;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class Sprite {
	public static enum Type {BORDER, BALL, RECT, GFIELD, SPHERE};
	private Type type;
	private Vec2 position;
	private Vec2 speed;
	private BufferedImage image;
	private AffineTransform rotation;	
	private AffineTransform offset;
	private boolean delete = false;
	public double push = 1.1;
	
	public abstract void update();	
	public abstract void collide(Sprite other);	
	
	/**
	 * return negative number if colliding else position
	 * @param other
	 * @return
	 */
	
	public abstract double distanceTo(Sprite other);

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	public Vec2 getSpeed() {
		return speed;
	}

	public void setSpeed(Vec2 speed) {
		this.speed = speed;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public AffineTransform getRotation() {
		return rotation;
	}

	public void setRotation(AffineTransform rotation) {
		this.rotation = rotation;
	}

	public AffineTransform getOffset() {
		return offset;
	}

	public void setOffset(AffineTransform offset) {
		this.offset = offset;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public boolean getDelete() {
		return delete;
	}
	
	public void delete() {
		this.delete = true;
	}

	public abstract AffineTransformOp getAffineTransformationOp();

}
