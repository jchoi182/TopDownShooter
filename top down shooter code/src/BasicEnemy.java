import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class BasicEnemy extends GameEntity{
	
	private Handler handler;
	private int health = 100;
	private Random r = new Random();
	
	public BasicEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		width = 32;
		height = 32;
	}

	public void tick() {
				
		for(int i = 0; i < handler.entity.size(); i ++) {
			GameEntity tempEntity = handler.entity.get(i);
			
			if(tempEntity.getID() == ID.Player) {
				//enemy wanders randomly until getting close to the player
				if(Math.abs(x - tempEntity.getX()) < 200 && Math.abs(y - tempEntity.getY()) < 200) {
					calculateVelocity(x,y,tempEntity.getX(),tempEntity.getY(),4	);
				} 
			}
		}
		
		collision();
		
		x += velX;
		y += velY;
	}
	
	private void collision() {
		for(int i = 0; i < handler.platform.size(); i ++) {
			
			GamePlatform tempPlatform = handler.platform.get(i);
			
			if(tempPlatform.getID() == ID.Block) {
				//prevents entity from getting stuck in a wall
				if(getBoundsU().intersects(tempPlatform.getBounds())) {
					velY = 0;
					y = tempPlatform.getY() + tempPlatform.getH();
				}
				if(getBoundsD().intersects(tempPlatform.getBounds())) {
					velY = 0;
					y = tempPlatform.getY() - height;
				}
				if(getBoundsR().intersects(tempPlatform.getBounds())) {
					velX = 0;
					x = tempPlatform.getX() - width;
				}
				if(getBoundsL().intersects(tempPlatform.getBounds())) {
					velX = 0;
					x = tempPlatform.getX() + tempPlatform.getW();
				}
			}
		}
		for(int i = 0; i < handler.entity.size(); i ++) {
			GameEntity tempEntity = handler.entity.get(i);
			
			if(tempEntity.getID() == ID.Bullet) {
				if(getBounds().intersects(tempEntity.getBounds())) {
					health -= 30;
					handler.removeEntity(tempEntity);
				}
			}
		}
		if(health <= 0) {
			handler.removeEntity(this);
		}
	}

	public void calculateVelocity(int fromX, int fromY, int toX, int toY, double speed) {
	  double distance = Math.sqrt(Math.pow((toX - fromX), 2) + Math.pow((toY - fromY), 2));
	  //set the speed in [2,n)  n should be < 20 for normal speed
	  //find Y
	  velY = (float)((toY - fromY) * speed / distance);
	  //find X
	  velX = (float)((toX - fromX) * speed / distance);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		if(handler.HitBox) {
			g2d.setColor(Color.blue);
			g2d.fill(getBoundsU());
			g2d.setColor(Color.red);
			g2d.fill(getBoundsD());
			g2d.setColor(Color.yellow);
			g2d.fill(getBoundsR());
			g2d.setColor(Color.green);
			g2d.fill(getBoundsL());
		} else {
			g2d.setColor(Color.green);
			g2d.fill(getBounds());
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}
	public Rectangle getBoundsU() {
		return new Rectangle(x+5,y-1,width-10,1);
	}
	public Rectangle getBoundsD() {
		return new Rectangle(x+5,y+height,width-10,1);
	}
	public Rectangle getBoundsR() {
		return new Rectangle(x+width-1,y+5,1,height-10);
	}
	public Rectangle getBoundsL() {
		return new Rectangle(x-1,y+5,1,height-10);
	}
}
