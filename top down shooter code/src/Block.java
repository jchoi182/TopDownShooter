import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends GamePlatform{

	public Block(int x, int y, ID id) {
		super(x, y, id);
		w = 32;
		h = 32;
	}

	public void tick() {
	
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, 32, 32);
	}

	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}
	
	

}
