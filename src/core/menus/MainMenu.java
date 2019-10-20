package core.menus;

import core.Core;
import core.world.level.Level;
import core.world.level.loading.LevelConfig;
import core.world.level.loading.LevelLoader;
import core.world.level.menu.MenuLevel;
import core.world.light.LightEngine;
import rendering.Screen;
import utils.Utils;
import utils.exceptions.PowtakException;

public class MainMenu {
	
	private final static String MENU_LEVEL_FILE_NAME = "main_menu.lvl";
	
	private Core core;
	private Screen screen;
	private Level level;

	public MainMenu(Core core, Screen screen) throws PowtakException {
		this.screen = screen;
		LevelConfig menuLevelConfig = LevelLoader.get().loadCustomLevel(Utils.levelPath(MENU_LEVEL_FILE_NAME));
		this.level  = MenuLevel.from(screen, menuLevelConfig);
	}
	
	public void update() throws PowtakException {
		this.level.update();
		handleInput();
	}
	
	private void handleInput() {
		
	}
	
	public void render() {
		this.level.render();
		renderMenuLightning();
	}
	
	private void renderMenuLightning() {
		LightEngine lightEngine = LightEngine.get();
		lightEngine.enlightScreen(screen, 50);
	}
	
}
