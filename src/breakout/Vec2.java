package breakout;

public class Vec2 {
	private double x;
	private double y;
	
	public Vec2(double x, double y){
		this.x = x;
		this.y = y;
	}

	public Vec2() {
		this.x = 0;
		this.y = 0;
	}

	public Vec2 add(Vec2 argVec){
		return new Vec2(x + argVec.x,y + argVec.y);
	}
	
	public Vec2 sub(Vec2 argVec){
		return new Vec2(x - argVec.x,y - argVec.y);
	}
	
	public double distanceTo(Vec2 argVec){
		return sub(argVec).value();
	}
	
	public double distanceToLine(Vec2 A, Vec2 B) {
			double norm = B.sub(A).value();
		    return ((x-A.x)*(B.y-A.y)-(y-A.y)*(B.x-A.x))/norm;
	}
	
	public Vec2 flipHorizontal(){
		return new Vec2(x, y*-1);
	}
	
	public Vec2 flipVertical(){
		return new Vec2(x*-1, y);
	}
	
	public Vec2 multiply(Double scalar){
		return new Vec2(x*scalar, y*scalar);
	}
	
	public Vec2 multiply(Vec2 argVec){
		return new Vec2(x*argVec.x, y*argVec.y);
	}
	
	public Vec2 divide(Double scalar){
		return new Vec2(x/scalar, y/scalar);
	}
	
	public Vec2 normalize(){
		return divide(value());
	}
	
	public double value(){
		return Math.sqrt(x*x+y*y);
	}
	
	public double X() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double Y() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Vec2 invert(){
		return new Vec2(x*-1, y*-1);
	}
	
	public String toString(){
		return x + "," + y;
	}
}
