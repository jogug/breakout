package breakout;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;



public class Breakout {
	private final float target_framerate = 60f;
	private final long time_per_frame = (long)(1000/target_framerate);
	private final int x = 500;
	private final int y = 800;
	private boolean debugmode = false;
	private JFrame frame;
	private Board board;
	private Input input;
	private int score;
	private List<Sprite> sprites;
	private boolean gameover;
	private Border bottom;
	private Ball ball;
	private Button startButton;
	
	public static void main(String[] args){
		 new Breakout();
	}

	public Breakout() {
		initialize();
	}
	
	private void initialize() {
		EventQueue.invokeLater(new Runnable() {
		    public void run() {
				frame = new JFrame();
				frame.setSize(new Dimension(x,y));
				board = new Board(debugmode);
				
				startButton = new Button();
				startButton.setLabel("Start Game");
				startButton.addActionListener(new StartButtonListener());
				board.setLayout(new GridBagLayout());
				board.add(startButton);	
				frame.add(board);
				frame.setTitle("Breakout");
				frame.setIgnoreRepaint(true);
			    frame.setResizable(false);
			    frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
				frame.setVisible(true);
		    }
		});
	}
		
	private class StartButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			startButton.setVisible(false);
			board.requestFocus();
			try {
				newGame();
		  		GameLoop loop = new GameLoop();
		  		loop.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}  	
		}			
	}
	
	private void newGame() throws IOException {
		sprites = new CopyOnWriteArrayList<Sprite>();
        sprites.add(new Border(new Vec2(0,1), Border.Bound.upper));
        bottom = new Border(new Vec2(0,board.getHeight()-1), Border.Bound.lower);
        sprites.add(bottom);
        sprites.add(new Border(new Vec2(1,0), Border.Bound.upper));
        sprites.add(new Border(new Vec2(board.getWidth()-1,0), Border.Bound.lower));
        
		BufferedImage image;
		AffineTransform offset = new AffineTransform();
		
  	  	//Gravity 	
		image = ImageIO.read(ResourceLoader.load("gravity.png"));
	    offset = new AffineTransform();
	    offset.translate(-50, -50);		 
	  	sprites.add(new GravityField(new Vec2(100,250),50,.4,new Vec2(),image,offset, new AffineTransform()));
	  	sprites.add(new GravityField(new Vec2(400,400),50,.4,new Vec2(),image,offset, new AffineTransform()));
	  	sprites.add(new GravityField(new Vec2(300,100),50,.4,new Vec2(),image,offset, new AffineTransform()));
	  	
		//Ball       
    	offset = new AffineTransform();
        offset.translate(-15, -15);
    	image = ImageIO.read(ResourceLoader.load("ball.png"));
    	ball = new Ball(new Vec2(board.getWidth()/2,board.getHeight()-100), 15, new Vec2(1,1), image, offset, new AffineTransform());
  		sprites.add(ball);


  		//Bricks
  		image = ImageIO.read(ResourceLoader.load("brick.png"));
  		for(int i = 0; i<9;i++){
  			for(int j = 0; j<9; j++){
  		    	offset = new AffineTransform();
  		        offset.translate(-25, -10);		    	
  		  		sprites.add(new Brick(new Vec2(43+i*51,35+j*21), new Vec2(25,10), new Vec2(0,0), image, offset, new AffineTransform()));
  			}
  		}		

	    //Player  
    	offset = new AffineTransform();
        offset.translate(-60, -15);
    	image = ImageIO.read(ResourceLoader.load("pad.png"));
    	Pad player = new Pad(new Vec2(board.getWidth()/2,board.getHeight()-26), new Vec2(60,15), new Vec2(0,0), image, offset, new AffineTransform());
  		sprites.add(player);
  		input = new Input(player);
    	board.addKeyListener(input);
   
  		board.setSprites(sprites);
  		
  		gameover = false;		
	}
	

	private class GameLoop extends Thread {
		
		@Override
		public void run() {  	
			long previousTime = System.currentTimeMillis();
			// double t = 0; todo
			while(!gameover){
				integrate(1);
				board.setScore(score);	
				board.repaint();
				long currentTime = System.currentTimeMillis();
				long sleepTime = time_per_frame + previousTime - currentTime;
				previousTime = System.currentTimeMillis();
				if(sleepTime>0){
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			startButton.setLabel("Game Over. Score:" + score+" \n restart?");
			startButton.setSize(new Dimension(200,30));
			startButton.setVisible(true);
		}
	}
	
	private void integrate(int steps){
		List<Sprite>toDelete = new CopyOnWriteArrayList<Sprite>();
		for(int i=0;i<steps;i++){
			checkCollision();			
				for(Sprite sprite:sprites){
				if(sprite.getDelete()) toDelete.add(sprite);
				else sprite.update();				
			}
			sprites.removeAll(toDelete);
		}
	}
	
	private void checkCollision(){
		score = 82;
		if(ball.distanceTo(bottom)<=1){
			gameover = true;
		}
		for(Sprite sprite: sprites){
			//TODO optimize with bounding box, search tree
			if(sprite.getType() == Sprite.Type.RECT){
				score--;
			}
			for(Sprite other: sprites){
				if(sprite!=other|sprite.getType() != Sprite.Type.BORDER){
					double dist = sprite.distanceTo(other);
					if(dist<0){
						sprite.collide(other);
					}
				}
			}
		}
	}
}
