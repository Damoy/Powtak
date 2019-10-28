package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import core.configs.CharacterSkin;
import core.configs.GameConfig;
import core.configs.OptionsConfig;
import core.gamestates.GameState;
import core.menus.MainMenu;
import core.world.World;
import core.world.WorldGeneration;
import input.Keys;
import rendering.Screen;
import rendering.Texture;
import rendering.Window;
import rendering.UI.IndependentLoadingScreen;
import utils.Log;
import utils.exceptions.PowtakException;

public class Core extends JPanel implements Runnable, KeyListener{

	private static final long serialVersionUID = -7471530975520076940L;

	private Thread thread;
	private boolean running;
	private GameState gameState;

	private MainMenu mainMenu;
	private Screen screen;
	private Window window;
	private World world;
	private IndependentLoadingScreen loadingScreen;
	
	private OptionsConfig optionsConfig;
	
	public Core() throws PowtakException {
		super();
		setComponent();
		init();
	}
	
	private void setComponent() {
		setPreferredSize(new Dimension(GameConfig.WIDTH * GameConfig.SCALE, GameConfig.HEIGHT * GameConfig.SCALE));
		setFocusable(true);
		requestFocus();
	}

	private void init() throws PowtakException {
		initGraphics();
		// this.loadingScreen = new IndependentLoadingScreen(this, screen, 3000);
		this.gameState = GameState.MAIN_MENU;
		this.optionsConfig = new OptionsConfig();
		this.mainMenu = new MainMenu(this, screen, optionsConfig);
	}

	private void initGraphics() {
		screen = Screen.of(this, new BufferedImage(GameConfig.WIDTH, GameConfig.HEIGHT, BufferedImage.TYPE_INT_RGB));
		window = new Window(GameConfig.TITLE, this);
	}
	
	public void launchGame() throws PowtakException {
		this.world = WorldGeneration.generateWorldFromPredefinedLevels(this, screen);
		gameState = GameState.IN_GAME;
	}
	
	public void focusMainMenuFromInGame() {
		Keys.update();
		gameState = GameState.MAIN_MENU;
	}
	
	public void exitGame() {
		Log.warn("Exiting game...");
		System.exit(0);
	}
	
	public Texture getCurrentCharacterSkin() throws PowtakException {
		return CharacterSkin.getCharacterSpriteSheet(optionsConfig.getCharacterSkin());
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / GameConfig.UPS;
		int frames = 0;

		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();
		requestFocus();
		
		// loadingScreen.start();
		
		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (unprocessed >= 1) {
				ticks++;
				try {
					update();
				} catch (PowtakException e) {
					e.printStackTrace();
				}
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
				try {
					render();
				} catch (PowtakException e) {
					e.printStackTrace();
				}
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				window.setTitle(frames + " fps | " + ticks + " ups");
				lastTimer1 += 1000;
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void update() throws PowtakException {
		switch (gameState) {
		case MAIN_MENU:
			this.mainMenu.update();
			Keys.update();
			break;
		case IN_GAME:
			world.update();
			screen.update();
			Keys.updateKeysBlockCounter();
			break;
		case PAUSED:
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + gameState);
		}
	}

	public void render() throws PowtakException {
		screen.clear(Color.WHITE);
		
		switch (gameState) {
		case MAIN_MENU:
			this.mainMenu.render();
			break;
		case IN_GAME:
			world.render(screen);
			break;
		case PAUSED:
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + gameState);
		}
		
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

	public OptionsConfig getOptionsConfig() {
		return optionsConfig;
	}
	
}