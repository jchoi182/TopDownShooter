import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyBullet extends GameEntity{
	
	private Handler handler;
	
	public EnemyBullet(int x, int y, ID id, Handler handler, int mx, int my, int speed) {
		super(x, y, id);
		this.handler = handler;
		width = 8;
		height = 8;
		calculateVelocity(x,y,mx,my,speed);
	}
	
	public void calculateVelocity(int fromX, int fromY, int toX, int toY, double speed) {
	  double distance = Math.sqrt(Math.pow((toX - fromX), 2) + Math.pow((toY - fromY), 2));
	  //set the speed in [2,n)  n should be < 20 for normal speed
	  //find Y
	  velY = (float)((toY - fromY) * speed / distance);
	  //find X
	  velX = (float)((toX - fromX) * speed / distance);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		for(int i = 0; i < handler.platform.size(); i ++) {
			GamePlatform tempPlatform = handler.platform.get(i);
			
			if(tempPlatform.getID() == ID.Block) {
				if(getBounds().intersects(tempPlatform.getBounds())) {
					handler.removeEntity(this);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect(x,y,width,height);
	}

	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}
	public Rectangle getBoundsU() {
		return null;
	}
	public Rectangle getBoundsD() {
		return null;
	}
	public Rectangle getBoundsR() {
		return null;
	}
	public Rectangle getBoundsL() {
		return null;
	}

}
