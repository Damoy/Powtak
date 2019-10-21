package rendering.UI;

import java.awt.Color;
import java.awt.Point;

import javax.swing.SwingUtilities;

import core.Core;
import core.configs.GameConfig;
import rendering.Screen;
import rendering.config.TextRenderingConfig;
import utils.ScreenComputation;

public class IndependentLoadingScreen implements Runnable{

	private final static int MIN = 0;
	private final static int MAX = 100;
	
	@SuppressWarnings("unused")
	private Core core;
	private ProgressBar progressBar;
	private Screen screen;
	private int sleepDelayMs;
	
	public IndependentLoadingScreen(Core core, Screen screen, int delayMs) {
		this.core = core;
		this.screen = screen;
		this.progressBar = new ProgressBar(core, -5, 0);
		this.sleepDelayMs = delayMs / MAX;
	}
	
	public void start() {
		run();
	}
	
	@Override
	public void run() {
		for(int i = MIN; i < MAX; ++i) {
			final int percent = i;
			try {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						update(percent);
						render();
					}
				});
				Thread.sleep(sleepDelayMs);
			} catch(Exception e) {
				;
			}
		}
	}
	
	private void update(int percent) {
		progressBar.update(percent);		
	}
	
	private void render() {
		Point center = ScreenComputation.getCenter();
		
		screen.clear(Color.WHITE);
		screen.renderText(GameConfig.TITLE.toUpperCase(), (int) (center.x - GameConfig.TILE_SIZE * 2.5f), center.y - GameConfig.TILE_SIZE, 24.0f, Color.BLACK);
		screen.renderText("Loading", (int) (center.x - GameConfig.TILE_SIZE * 1.75f), center.y, 10.0f, Color.BLACK);
		screen.renderTextWithFont("...", TextRenderingConfig.RAW_RENDERING_FONT,
				(int) (center.x + GameConfig.TILE_SIZE * 0.9f), (int) (center.y - (GameConfig.TILE_SIZE * 0.05f)),  10.0f, Color.BLACK);
		screen.render();
	}
}
