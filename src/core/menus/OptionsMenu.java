package core.menus;

import java.awt.Color;
import java.awt.Point;

import core.Core;
import core.configs.CharacterSkin;
import core.configs.GameConfig;
import core.configs.OptionsConfig;
import input.Keys;
import rendering.Screen;
import rendering.Texture;
import utils.Colors;
import utils.Selection;
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
	
	private Selection<Color> characterSelection;
	
	public OptionsMenu(MainMenu mainMenu, Core core, Screen screen, MenuSelector menuSelector, OptionsConfig optionsConfig) throws PowtakException {
		super(core, screen);
		this.mainMenu = mainMenu;
		this.optionsConfig = optionsConfig;
		this.menuSelector = menuSelector;
		this.menuSelector.addEntry(this, 0, getOptionsCount() - 1);
		this.characterSelection = new Selection<>(Color.class, CharacterSkin.values().length, true);
		this.characterSelection.addEntry(0, CharacterSkin.getCharacterColor(CharacterSkin.values()[0]));
		this.characterSelection.addEntry(1, CharacterSkin.getCharacterColor(CharacterSkin.values()[1]));
		this.characterSelection.addEntry(2, CharacterSkin.getCharacterColor(CharacterSkin.values()[2]));
		this.characterSelection.addEntry(3, CharacterSkin.getCharacterColor(CharacterSkin.values()[3]));
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
			reset();
			mainMenu.focusMainMenuFromSubMenu();
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
			updateSoundState();
			break;
		case CHARACTER_SELECTION:
			updateCharacterSelection();
			break;
		default:
			throw new UnknownEnumOptionException(OptionsMenuOption.class.getName(), userSelectedOption.name());
		}
	}
	
	private void updateSoundState() {
		optionsConfig.setSoundActivated(!optionsConfig.isSoundActivated());
	}
	
	private void updateCharacterSelection() {
		characterSelection.increaseValue();
		optionsConfig.setCharacterSkin(CharacterSkin.values()[characterSelection.getIndex()]);
	}

	@Override
	public void render() throws PowtakException {
		renderSoundInfo();
		renderCharacterSelection();
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
		
		setupIconRenderingStartPos(soundInfoRenderingPos, GameConfig.TILE_SIZE << 1);
	}
	
	private void setupIconRenderingStartPos(Point pos, int iconRenderingYOffset) {
		if(iconRenderingStartPos == null) {
			iconRenderingStartPos = pos;
			this.iconRenderingYOffset = iconRenderingYOffset;
		}
	}
	
	private void renderCharacterSelection() throws PowtakException {
		// render icon and arrow or just color name and update icon
		String characterSelectionInfo = "Character";
		Color wrapColor = MainMenu.TEXT_RENDERING_WRAP_COLOR;
		int fontSize = MainMenu.POWTAK_INFO_FONT_SIZE >> 1;
		int rowOffset = 1;
		Color renderingColor = characterSelection.getCurrentOption();
		
		screen.setupTextRendering(fontSize, wrapColor);
		Point characterInfosPos = screen.getStringCenteredPosition(characterSelectionInfo, fontSize, rowOffset);
		screen.renderWrapedText(characterSelectionInfo, renderingColor, this.iconRenderingStartPos.x, characterInfosPos.y);
		screen.cleanTextRendering();
	}
	
	private void renderSelectionIcon() throws PowtakException {
		Texture iconSelectionTexture = menuSelector.updateTexture(optionsConfig.getCharacterSkin());
		int ts = GameConfig.TILE_SIZE;
		int x = (int) (iconRenderingStartPos.x - (ts * 1.5f));
		int y = iconRenderingStartPos.y - (iconSelectionTexture.getHeight());
		y += menuSelector.getValue(this) * iconRenderingYOffset;
		screen.renderTex(iconSelectionTexture, x, y);
	}

	@Override
	protected void reset() {
		menuSelector.setValue(this, 0);
	}
	
	private static enum OptionsMenuOption {
		SOUND_ACTIVATION, CHARACTER_SELECTION
	}

}
