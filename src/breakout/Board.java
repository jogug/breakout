package breakout;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

public class Board extends JPanel {
	private static final long serialVersionUID = 1L;
	private boolean debug;
	List<Sprite> sprites;
	int score;
	
	public Board(boolean debug){
		super();
		this.debug = debug;
		score = 0;
        setFocusable(true);
        setDoubleBuffered(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.drawString(""+score, 450,20);
        
        for(Sprite sprite: sprites){
    		switch(sprite.getType()){
			case BORDER:if(debug){
							if(sprite.getPosition().X()==0){
								//Vertical
								g2d.drawLine(-getHeight(), (int)sprite.getPosition().Y(), getHeight(), (int)sprite.getPosition().Y());
							}else{
								//Horizontal
								g2d.drawLine((int)sprite.getPosition().X(), -getWidth(), (int)sprite.getPosition().X(), getWidth());
							}
						}
						break;
			case BALL:  if(debug||sprite.getImage()==null){
				        	g2d.drawOval((int)sprite.getPosition().X()-1, (int)sprite.getPosition().Y()-1, 2, 2);
						}else{
							g2d.drawImage(sprite.getImage(), sprite.getAffineTransformationOp(), (int)sprite.getPosition().X(), (int)sprite.getPosition().Y());
						}
						break;
			case RECT:  if(debug||sprite.getImage()==null){
	        				g2d.drawOval((int)sprite.getPosition().X()-1, (int)sprite.getPosition().Y()-1, 2, 2);
							Vec2 rect = ((Rectangle)sprite).getUlCornerDistance();
							g2d.drawString("A "+sprite.getPosition().sub(rect).X() +","+ (int)sprite.getPosition().sub(rect).Y(),(int)sprite.getPosition().sub(rect).X()-1, (int)sprite.getPosition().sub(rect).Y()-1);
							g2d.drawOval((int)sprite.getPosition().sub(rect).X()-1, (int)sprite.getPosition().sub(rect).Y()-1, 2, 2);
							g2d.drawString("B "+(sprite.getPosition().X()+rect.X()-1)+","+(int)(sprite.getPosition().Y()-rect.Y()-1), (int)(sprite.getPosition().X()+rect.X()-1), (int)(sprite.getPosition().Y()-rect.Y()-1));
							g2d.drawOval((int)(sprite.getPosition().X()+rect.X()-1), (int)(sprite.getPosition().Y()-rect.Y()-1), 2, 2);
							g2d.drawString("C "+sprite.getPosition().add(rect).X()+","+(int)sprite.getPosition().add(rect).Y(), (int)sprite.getPosition().add(rect).X()-1, (int)sprite.getPosition().add(rect).Y()-1);
							g2d.drawOval((int)sprite.getPosition().add(rect).X()-1, (int)sprite.getPosition().add(rect).Y()-1, 2, 2);
							g2d.drawString("D "+(sprite.getPosition().X()-rect.X()-1) +","+ (int)(sprite.getPosition().Y()+rect.Y()-1), (int)(sprite.getPosition().X()-rect.X()-1), (int)(sprite.getPosition().Y()+rect.Y()-1));
							g2d.drawOval((int)(sprite.getPosition().X()-rect.X()-1), (int)(sprite.getPosition().Y()+rect.Y()-1), 2, 2);
	        			}else{
	        				 g2d.drawImage(sprite.getImage(), sprite.getAffineTransformationOp(), (int)sprite.getPosition().X(), (int)sprite.getPosition().Y());
	        			}
						break;
			case GFIELD: if(debug||sprite.getImage()==null){
							double radius = ((GravityField)sprite).getRadius(); 
							g2d.drawOval((int)sprite.getPosition().X()-1, (int)sprite.getPosition().Y()-1, 2, 2);
							g2d.drawOval((int)(sprite.getPosition().X()-radius), (int)(sprite.getPosition().Y()-radius), (int)radius*2, (int)radius*2);
						}else{
							g2d.drawImage(sprite.getImage(), sprite.getAffineTransformationOp(), (int)sprite.getPosition().X(), (int)sprite.getPosition().Y());
						}

				
						break;
			default:
				break;				
    		}	
        }
        g2d.dispose();
	}
	
	public void setScore(int score){
		this.score = score;
	}
    
    public void setSprites(List<Sprite> sprites) {
		this.sprites = sprites;
	}
}
