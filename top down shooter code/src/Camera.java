
public class Camera {
	
	private float x, y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameEntity entity) {
		
		x += ((entity.getX() - x) - Game.WIDTH/2) * 0.05f;
		y += ((entity.getY() - y) - Game.HEIGHT/2) * 0.05f;
		
		if(x <= 0) x = 0;
		if(x >= Game.WIDTH + 32) x = Game.WIDTH + 32;
		if(y <= 0) y = 0;
		if(y >= Game.HEIGHT + 48) y = Game.HEIGHT + 48;
		
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
}
