import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{
	
	private Handler handler;
	private Camera camera;
	
	public MouseInput(Handler handler, Camera camera) {
		this.handler = handler;
		this.camera = camera;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = (int) (e.getX() + camera.getX());
		int my = (int) (e.getY() + camera.getY());
		 
		for(int i = 0; i < handler.entity.size(); i ++) {
			GameEntity tempEntity = handler.entity.get(i);
			
			if(tempEntity.getID() == ID.Player) {
				handler.addEntity(new Bullet(tempEntity.getX() + 16, tempEntity.getY() + 24, ID.Bullet, handler, mx, my, 20));
			} 	
		}
		System.out.println("Platforms: " + handler.platform.size() + ", Entities: " + handler.entity.size());
	}
}
