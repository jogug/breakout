package breakout;

import java.awt.image.AffineTransformOp;


public class Border extends Sprite {
	public static enum Bound {upper, lower};
	private Bound bound;

	public Border(Vec2 position,Bound bound){
		setType(Type.BORDER);
		setPosition( position);
		this.bound = bound;
	}
	
	public Bound getBound() {
		return bound;
	}
	
	@Override
	public void update() {
	}

	@Override
	public void collide(Sprite other) {
	}

	@Override
	public double distanceTo(Sprite other) {
		return 1;
	}

	@Override
	public AffineTransformOp getAffineTransformationOp() {
		return null;
	}

}
