import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

public class Handler {
	
	public final boolean HitBox = true;
	
	LinkedList<GameEntity> entity = new LinkedList<GameEntity>();
	
	ArrayList<GamePlatform> platform = new ArrayList<GamePlatform>();
	
	private boolean up = false, down = false, right = false, left = false;
	
	public void tick() {
		for(int i = 0; i < entity.size(); i++) {
			GameEntity tempEntity = entity.get(i);
			
			tempEntity.tick();
		}
		for(int i = 0; i < platform.size(); i++) {
			GamePlatform tempPlatform = platform.get(i);
			
			tempPlatform.tick();
		}
	}
	

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void render(Graphics g) {
		for(int i = 0; i < entity.size(); i++) {
			GameEntity tempObject = entity.get(i);
			
			tempObject.render(g);
		}
		for(int i = 0; i < platform.size(); i++) {
			GamePlatform tempPlatform = platform.get(i);
			
			tempPlatform.render(g);
		}
	}
	
	public void addEntity(GameEntity tempEntity) {
		entity.add(tempEntity);
	}
	
	public void removeEntity(GameEntity tempEntity) {
		entity.remove(tempEntity);
	}
	
	public void addPlatform(GamePlatform tempPlatform) {
		platform.add(tempPlatform);
	}
	
	public void removePlatform(GamePlatform tempPlatform) {
		platform.remove(tempPlatform);
	}
	
	public boolean isUp() {
		return up;
	}
	
	public void setUp(boolean up) {
		this.up = up;
	}
	
	public boolean isDown() {
		return down;
	}
	
	public void setDown(boolean down) {
		this.down = down;
	}
	
	public boolean isRight() {
		return right;
	}
	
	public void setRight(boolean right) {
		this.right = right;
	}
	
	public boolean isLeft() {
		return left;
	}
}
