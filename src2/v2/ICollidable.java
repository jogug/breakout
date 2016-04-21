package v2;

import breakout.Vec2;

interface ICollidable {
	public static enum Type {BORDER, BALL, RECT, GFIELD, SPHERE};
	public Type getType();
	public Vec2 getSpeed();
	public void setSpeed(Vec2 speed);
	public void getPosition();
}
