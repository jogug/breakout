package v2;

import java.util.ArrayList;
import java.util.List;

public class Collisions {
	private List<Collision> collisions;
	
	public Collisions(){
		collisions = new ArrayList<Collision>();
	}
	
	private void resetCollisions(){
		collisions.clear();
	}
	
	private class Collision{
		public ICollidable a, b;
		
		public Collision(ICollidable a, ICollidable b){
			this.a = a;
			this.b = b;
		}		
	}
	
	public void addCollidable(ICollidable a, ICollidable b){
		collisions.add(new Collision(a, b));
	}
	
	public void resolveCollisions(){
		//Resolve Collision and
		for(Collision collision: collisions){
			if(collision.a.getType()==ICollidable.Type.BALL){
				
			}
		}
	}
}
