package core.menus;

import java.awt.Color;
import java.awt.Point;

import core.Core;
import core.configs.GameConfig;
import core.configs.OptionsConfig;
import input.Keys;
import rendering.Screen;
import rendering.Texture;
import utils.Colors;
import utils.Log;
import utils.exceptions.PowtakException;
import utils.exceptions.UnknownEnumOptionException;

public class OptionsMenu extends Menu {
	
	private final static Color ACTIVATED_OPTION_COLOR = Colors.LIGHT_GREEN;
	private final static Color DEACTIVATED_OPTION_COLOR = Colors.LIGHT_RED;
	
	private MainMenu mainMenu;
	private MenuSelector menuSelector;
	private OptionsConfig optionsConfig;
	private OptionsMenuOption userSelectedOption;
	
	private Point iconRenderingStartPos;
	private int iconRenderingYOffset;
	
	public OptionsMenu(MainMenu mainMenu, Core core, Screen screen, MenuSelector menuSelector, OptionsConfig optionsConfig) throws PowtakException {
		super(core, screen);
		this.mainMenu = mainMenu;
		this.optionsConfig = optionsConfig;
		this.menuSelector = menuSelector;
		this.menuSelector.addEntry(this, 0, getOptionsCount() - 1);
	}
	
	@Override
	public int getOptionsCount() {
		return 2;
	}

	@Override
	public void update() throws PowtakException {
		handleInput();
	}

	@Override
	protected void handleInput() throws PowtakException {
		if(Keys.upKeyPressed()) {
			menuSelector.decreaseValue(this);
		}
		
		if(Keys.downKeyPressed()) {
			menuSelector.increaseValue(this);
		}
		
		if(Keys.isPressed(Keys.ESCAPE)) {
			mainMenu.focusMainMenu();
		}
		
		setSelectedOption();
		
		if(Keys.isPressed(Keys.ENTER)) {
			applyUserSelection();
		}
	}
	
	private void setSelectedOption() {
		int menuSelectionValue = menuSelector.getValue(this);
		userSelectedOption = OptionsMenuOption.values()[menuSelectionValue];
	}
	
	private void applyUserSelection() throws PowtakException {
		switch (userSelectedOption) {
		case SOUND_ACTIVATION:
			optionsConfig.setSoundActivated(!optionsConfig.isSoundActivated());
			Log.warn(optionsConfig.isSoundActivated());
			break;
		case CHARACTER_SELECTION:
			break;
		default:
			throw new UnknownEnumOptionException(OptionsMenuOption.class.getName(), userSelectedOption.name());
		}
	}

	@Override
	public void render() {
		renderSoundInfo();
		renderSelectionIcon();
	}
	
	private void renderSoundInfo() {
		// sound info rendering is updated according to if sound is activated
		boolean soundActivated = optionsConfig.isSoundActivated();
		Color wrapColor = MainMenu.TEXT_RENDERING_WRAP_COLOR;
		Color renderingColor = soundActivated ? DEACTIVATED_OPTION_COLOR : ACTIVATED_OPTION_COLOR;
		int fontSize = MainMenu.POWTAK_INFO_FONT_SIZE >> 1;
		int rowOffset = -1;
		String muteInfo = "Mute";
		String unmuteInfo = "Unmute";
		String soundInfo = soundActivated ? muteInfo : unmuteInfo;
		
		// setup screen rendering
		screen.setupTextRendering(fontSize, wrapColor);
		// get rendering position of "Mute" info
		Point soundInfoRenderingPos = screen.getStringCenteredPosition(muteInfo, fontSize, rowOffset);
		// wrapped rendering
		screen.renderWrapedText(soundInfo, renderingColor, soundInfoRenderingPos.x, soundInfoRenderingPos.y);
		// clean rendering
		screen.cleanTextRendering();
		
		setupIconRenderingStartPos(soundInfoRenderingPos, 0);
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

	@Override
	protected void reset() {
	}
	
	private static enum OptionsMenuOption {
		SOUND_ACTIVATION, CHARACTER_SELECTION
	}

}
