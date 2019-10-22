package core.menus;

import java.awt.Color;
import java.awt.Point;

import core.Core;
import core.configs.GameConfig;
import core.configs.OptionsConfig;
import core.world.level.Level;
import core.world.level.loading.LevelConfig;
import core.world.level.loading.LevelLoader;
import core.world.level.menu.MenuLevel;
import core.world.light.LightEngine;
import input.Keys;
import rendering.Screen;
import rendering.Texture;
import utils.Colors;
import utils.Utils;
import utils.exceptions.PowtakException;
import utils.exceptions.UnknownEnumOptionException;

public class MainMenu extends Menu {
	
	private final static String MENU_LEVEL_FILE_NAME = "main_menu.lvl";
	private final static int BACKGROUND_LIGHTNING_POWER = 80;
	public final static int POWTAK_INFO_FONT_SIZE = 40;
	public final static Color TEXT_RENDERING_WRAP_COLOR = Colors.DARK_GRAY;
	public final static Color TEXT_RENDERING_COLOR = Colors.LIGHT_GREEN;
	
	private MenuSelector menuSelector;
	private Level level;
	private MainMenuOption userSelectedOption;
	private Point iconRenderingStartPos;
	private int iconRenderingYOffset;
	
	private Menu currentSubMenu;
	private OptionsMenu optionsMenu;
	private OptionsConfig optionsConfig;

	public MainMenu(Core core, Screen screen) throws PowtakException {
		super(core, screen);
		LevelConfig menuLevelConfig = LevelLoader.get().loadCustomLevel(Utils.levelPath(MENU_LEVEL_FILE_NAME));
		this.level = MenuLevel.from(screen, menuLevelConfig);
		this.optionsConfig = new OptionsConfig();
		this.menuSelector = new MenuSelector(screen);
		this.menuSelector.addEntry(this, 0, getOptionsCount() - 1);
	}
	
	public void focusMainMenu() {
		currentSubMenu = null;
	}
	
	@Override
	public int getOptionsCount() {
		return MainMenuOption.values().length;
	}
	
	@Override
	public void update() throws PowtakException {
		this.level.update();
		updateCurrentSubMenu();
	}
	
	private void updateCurrentSubMenu() throws PowtakException {
		if(currentSubMenu == null) {
			handleInput();
		} else {
			currentSubMenu.update();
		}
	}
	
	protected void handleInput() throws PowtakException {
		if(Keys.upKeyPressed()) {
			menuSelector.decreaseValue(this);
		}
		
		if(Keys.downKeyPressed()) {
			menuSelector.increaseValue(this);
		}
		
		setSelectedOption();
		
		if(Keys.isPressed(Keys.ENTER)) {
			applyUserSelection();
		}
	}
	
	private void setSelectedOption() {
		int menuSelectionValue = menuSelector.getValue(this);
		userSelectedOption = MainMenuOption.values()[menuSelectionValue];
	}
	
	private void applyUserSelection() throws PowtakException {
		switch (userSelectedOption) {
		case PLAY:
			reset();
			core.launchGame();
			break;
		case EDITOR:
			break;
		case OPTIONS:
			createOptionsMenu();
			break;
		case CREDITS:
			break;
		default:
			throw new UnknownEnumOptionException(MainMenuOption.class.getName(), userSelectedOption.name());
		}
	}
	
	private void createOptionsMenu() throws PowtakException {
		if(optionsMenu == null) {
			optionsMenu = new OptionsMenu(this, core, screen, menuSelector, optionsConfig);
		}
		currentSubMenu = optionsMenu;
	}
	
	protected void reset() {
		userSelectedOption = null;
		menuSelector.resetValue(this);
	}
	
	@Override
	public void render() {
		this.level.render();
		renderMenuLightning();
		renderCurrentSubMenu();
	}
	
	private void renderCurrentSubMenu() {
		renderPowtakInfo();
		if(currentSubMenu == null) {
			renderSubMenusInfos();
			renderSelectionIcon();
		} else {
			currentSubMenu.render();
		}
	}
	
	private void renderMenuLightning() {
		LightEngine lightEngine = LightEngine.get();
		lightEngine.enlightScreen(screen, BACKGROUND_LIGHTNING_POWER);
	}
	
	private void renderPowtakInfo() {
		String powtak = "POWTAK";
		int fontSize = POWTAK_INFO_FONT_SIZE;
		int rowOffset = -3;
		screen.renderCenteredText(Colors.DARK_RED, powtak, fontSize, rowOffset);
	}
	
	private void renderSubMenusInfos() {
		String play = "Play";
		String editor = "Editor";
		String options = "Options";
		String credits = "Credits";
		
		// setup menus
		Color wrapColor = TEXT_RENDERING_WRAP_COLOR;
		Color renderingColor = TEXT_RENDERING_COLOR;
		int fontSize = POWTAK_INFO_FONT_SIZE >> 1;
		int rowOffset = -1;
		screen.setupTextRendering(fontSize, wrapColor);
		
		// render play
		Point playPos = screen.getStringCenteredPosition(play, fontSize, rowOffset);
		int menusX = playPos.x;
		screen.renderWrapedText(play, renderingColor, menusX, playPos.y);
		int menusRenderingHeight = screen.getStringHeight(play);
		
		// render editor
		Point editorPos = screen.getStringCenteredPosition(editor, fontSize, rowOffset);
		screen.renderWrapedText(editor, renderingColor, menusX, editorPos.y + menusRenderingHeight);
		
		// render options
		Point optionsPos = screen.getStringCenteredPosition(options, fontSize, rowOffset);
		screen.renderWrapedText(options, renderingColor, menusX, optionsPos.y + (menusRenderingHeight * 2));
		
		// render credits
		Point creditsPos = screen.getStringCenteredPosition(credits, fontSize, rowOffset);
		screen.renderWrapedText(credits, renderingColor, menusX, creditsPos.y + (menusRenderingHeight * 3));

		// clean rendering
		screen.cleanTextRendering();
		setupIconRenderingStartPos(playPos, menusRenderingHeight);
	}
	
	private void setupIconRenderingStartPos(Point pos, int iconRenderingYOffset) {
		if(iconRenderingStartPos == null) {
			iconRenderingStartPos = pos;
			this.iconRenderingYOffset = iconRenderingYOffset;
		}
	}
	
	private void renderSelectionIcon() {
		Texture iconSelectionTexture = Texture.NORMAL_PLAYER_ICON;
		int ts = GameConfig.TILE_SIZE;
		int x = (int) (iconRenderingStartPos.x - (ts * 1.5f));
		int y = iconRenderingStartPos.y - (iconSelectionTexture.getHeight());
		y += menuSelector.getValue(this) * iconRenderingYOffset;
		screen.renderTex(iconSelectionTexture, x, y);
	}

	public OptionsConfig getOptionsConfig() {
		return optionsConfig;
	}
	

	private static enum MainMenuOption {
		PLAY, EDITOR, OPTIONS, CREDITS
	}

	
}
