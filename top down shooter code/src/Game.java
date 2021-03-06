import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 1000, HEIGHT = 563;
	
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private Camera camera;
	private HUD hud;
	
	private BufferedImage level = null;
	
	public enum STATE {
		Game,
		Menu,
		Dead,
		Pause
	}
	
	public STATE gameState = STATE.Menu;
	
	public Game() { 
		new Window(WIDTH, HEIGHT, "Top Down Shooter", this);
		start();
		
		handler = new Handler();
		camera = new Camera(0,0);
		hud = new HUD();
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(new MouseInput(handler, camera));
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/TopDownShooterMap.png");
		
		loadLevel(level);
		
	}
	
	private void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta --;
				updates ++;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void tick() {
		
		for(int i = 0; i < handler.entity.size(); i ++) {
			if(handler.entity.get(i).getID() == ID.Player) {
				camera.tick(handler.entity.get(i));
			}
		}
		
		handler.tick();
		hud.tick();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		//////////////////////////////////
		
		g.setColor(Color.black);
		g.fillRect(0,  0, WIDTH, HEIGHT);
		
		g2d.translate(-camera.getX(), -camera.getY());
	
		handler.render(g);
		
		g2d.translate(camera.getX(), camera.getY());
		
		hud.render(g);
		
		//////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	//loading the level
	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int xx = 0; xx < w; xx ++) {
			for(int yy = 0; yy < h; yy ++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255)
					handler.addPlatform(new Block(xx*32, yy*32, ID.Block));
				if(blue == 255)
					handler.addEntity(new Player(xx*32, yy*32, ID.Player, handler,hud));
				if(green == 255) 
					handler.addEntity(new BasicEnemy(xx*32, yy*32, ID.BasicEnemy, handler));
			}
		}
	}
	
	public static float clamp(float value, float min, float max) {
		if(value < min) {
			return min;
		} else if(value > max) {
			return max;
		} else {
			return value;
		}
	}
	
	public static void main(String args[]) {
		new Game();
	}
	
}
