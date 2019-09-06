package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import core.world.World;
import core.world.WorldGenerationWrapper;
import input.Keys;
import rendering.Screen;
import rendering.Window;
import rendering.UI.IndependentLoadingScreen;
import utils.Config;


public class Core extends JPanel implements Runnable, KeyListener{

	private static final long serialVersionUID = -7471530975520076940L;

	private Thread thread;
	private boolean running;

	private Screen screen;
	private Window window;
	private World world;
	private IndependentLoadingScreen loadingScreen;

	private Core() {
		super();
		setComponent();
		init();
	}
	
	public static Core generate() {
		return new Core();
	}

	private void setComponent() {
		setPreferredSize(new Dimension(Config.WIDTH * Config.SCALE, Config.HEIGHT * Config.SCALE));
		setFocusable(true);
		requestFocus();
	}

	private void init() {
		initGraphics();
		this.loadingScreen = new IndependentLoadingScreen(this, screen, 3000);
		this.world = WorldGenerationWrapper.generatePredefined(screen);
	}

	private void initGraphics() {
		screen = Screen.of(this, new BufferedImage(Config.WIDTH, Config.HEIGHT, BufferedImage.TYPE_INT_RGB));
		window = new Window(Config.TITLE, this);
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / Config.UPS;
		int frames = 0;

		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();
		requestFocus();
		
		loadingScreen.start();
		
		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (unprocessed >= 1) {
				ticks++;
				update();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				window.setTitle(frames + " fps | " + ticks + " ups");
				lastTimer1 += 1000;
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void update() {
		world.update();
		screen.update();
		Keys.updateKeysBlockCounter();
	}

	public void render() {
		screen.clear(Color.WHITE);
		world.render(screen);
		screen.render();
	}

	public void start() {
		if (running) return;
		running = true;
		window.start();
	}

	public void stop() {
		if (!running) return;
		running = false;
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), false);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), true);
	}

}