import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player extends GameEntity{
	
	private Handler handler;
	private HUD hud;
	
	public Player(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);
		
		this.handler = handler;
		this.hud = hud;
		width = 32;
		height = 48;
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		collision();
		
		if(handler.isUp()) velY = -5;
		else if(!handler.isDown()) velY = 0;
		
		if(handler.isDown()) velY = 5;
		else if(!handler.isUp()) velY = 0;
		
		if(handler.isRight()) velX = 5;
		else if(!handler.isLeft()) velX = 0;
		
		if(handler.isLeft()) velX = -5;
		else if(!handler.isRight()) velX = 0;
		
		velX = Game.clamp((int)velX, -5, 5);
		velY = Game.clamp((int)velY, -5, 5);
	}
	
	private void collision() {
		for(int i = 0; i < handler.platform.size(); i ++) {
			
			GamePlatform tempPlatform = handler.platform.get(i);
			
			if(tempPlatform.getID() == ID.Block) {
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
			
			if(tempEntity.getID() == ID.EnemyBullet) {
				if(getBounds().intersects(tempEntity.getBounds())) {
					HUD.HEALTH -= 10;
				}
			}
			if(tempEntity.getID() == ID.BasicEnemy) {
				if(getBounds().intersects(tempEntity.getBounds())) {
					HUD.HEALTH -= 1;
				}
			}
		}
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
			g2d.setColor(Color.white);
			g2d.fillOval(x,y,width,height);
		}
	}
	
	public Rectangle getBounds(){
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
