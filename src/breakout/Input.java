package breakout;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener{
	private IControlable player;
	
	public Input(IControlable player){
		this.player = player;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==37){
			player.moveLeft();
		}
		if(e.getKeyCode()==38){
			player.moveUp();
		}
		if(e.getKeyCode()==39){
			player.moveRight();
		}
		if(e.getKeyCode()==40){
			player.moveDown();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==37){
			player.resetLeft();
		}
		if(e.getKeyCode()==38){
			player.resetUp();
		}
		if(e.getKeyCode()==39){
			player.resetRight();
		}
		if(e.getKeyCode()==40){
			player.resetDown();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
