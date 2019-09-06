package rendering.UI;

import java.awt.Color;
import java.awt.Point;

import javax.swing.SwingUtilities;

import core.Core;
import rendering.Screen;
import rendering.config.TextRenderingConfig;
import utils.Config;
import utils.ScreenPositionCalculator;

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
		Point center = ScreenPositionCalculator.getCenter();
		
		screen.clear(Color.WHITE);
		screen.renderText(Config.TITLE.toUpperCase(), (int) (center.x - Config.TILE_SIZE * 2.5f), center.y - Config.TILE_SIZE, 24.0f, Color.BLACK);
		screen.renderText("Loading", (int) (center.x - Config.TILE_SIZE * 1.75f), center.y, 10.0f, Color.BLACK);
		screen.renderTextWithFont("...", TextRenderingConfig.RAW_RENDERING_FONT,
				(int) (center.x + Config.TILE_SIZE * 0.9f), (int) (center.y - (Config.TILE_SIZE * 0.05f)),  10.0f, Color.BLACK);
		screen.render();
	}
}
