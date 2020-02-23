package core.menus;

import java.awt.Color;
import java.awt.Point;

import core.Core;
import core.configs.GameConfig;
import core.configs.OptionsConfig;
import core.world.level.editor.LevelEditor;
import input.Keys;
import rendering.Screen;
import rendering.Texture;
import utils.Colors;
import utils.exceptions.PowtakException;
import utils.exceptions.UnknownEnumOptionException;

public class EditorMenu extends Menu {
	
	private MainMenu mainMenu;
	private MenuSelector menuSelector;
	private OptionsConfig optionsConfig;
	private LevelEditor levelEditor;
	
	private Point iconRenderingStartPos;
	private int iconRenderingYOffset;
	private EditorMenuOption userSelectedOption;

	public EditorMenu(MainMenu mainMenu, Core core, Screen screen,
			MenuSelector menuSelector, OptionsConfig optionsConfig) throws PowtakException {
		super(core, screen);
		this.mainMenu = mainMenu;
		this.menuSelector = menuSelector;
		this.optionsConfig = optionsConfig;
		this.menuSelector.addEntry(this, 0, getOptionsCount() - 1);
	}
	
	@Override
	public void update() throws PowtakException {
		handleInput();
		handleEditorUpdate();
	}
	
	private void createLevelEditor() {
		if(levelEditor == null) {
			this.levelEditor = new LevelEditor(core, screen);
		}
	}

	@Override
	protected void handleInput() throws PowtakException {
		if(levelEditor == null) {
			handleMenuInput();
		}
	}
	
	private void handleEditorUpdate() {
		if(levelEditor != null) {
			levelEditor.update();
		}
	}
	
	private void handleMenuInput() throws PowtakException {
		if(Keys.upKeyPressed()) {
			boolean valueDecreased = menuSelector.decreaseValue(this);
			if(valueDecreased) {
				soundEngine.playMenuOptionSound();
			}
		}
		
		if(Keys.downKeyPressed()) {
			boolean valueIncreased = menuSelector.increaseValue(this);
			if(valueIncreased) {
				soundEngine.playMenuOptionSound();
			}
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
		userSelectedOption = EditorMenuOption.values()[menuSelectionValue];
	}
	
	private void applyUserSelection() throws PowtakException {
		switch (userSelectedOption) {
		case NEW_LEVEL:
			soundEngine.playMenuSelectionSound();
			createLevelEditor();
			break;
		case EDIT_LEVEL:
			soundEngine.playMenuSelectionSound();
			createLevelEditor();
			break;
		default:
			throw new UnknownEnumOptionException(EditorMenuOption.class.getName(), userSelectedOption.name());
		}
	}
	
	private void deleteLevelEditor() {
		this.levelEditor = null;
	}

	@Override
	public void render() throws PowtakException {
		if(levelEditor != null) {
			levelEditor.render();
		} else {
			renderOptions();
			renderSelectionIcon();
		}
	}
	
	private void renderOptions() {
		Color wrapColor = MainMenu.TEXT_RENDERING_WRAP_COLOR;
		Color renderingColor = Colors.LIGHT_GREEN;
		int fontSize = MainMenu.POWTAK_INFO_FONT_SIZE >> 1;
		int rowOffset = -1;
		String newLevelInfo = "New level";
		String editLevelInfo = "Edit level";
		
		Point newLevelInfoRenderingPos = renderNewLevel(wrapColor, renderingColor, newLevelInfo, fontSize, rowOffset);
		rowOffset += 2;
		renderEditLevel(wrapColor, renderingColor, editLevelInfo, fontSize, rowOffset, newLevelInfoRenderingPos.x);
	}
	
	private Point renderNewLevel(Color wrapColor, Color renderingColor, String newLevelInfo,
			int fontSize, int rowOffset) {
		// setup screen rendering
		screen.setupTextRendering(fontSize, wrapColor);
		// get rendering position of "Mute" info
		Point newLevelInfoRenderingPos = screen.getStringCenteredPosition(newLevelInfo, fontSize, rowOffset);
		// wrapped rendering
		screen.renderWrapedText(newLevelInfo, renderingColor, newLevelInfoRenderingPos.x, newLevelInfoRenderingPos.y);
		// clean rendering
		screen.cleanTextRendering();
		
		setupIconRenderingStartPos(newLevelInfoRenderingPos, GameConfig.TILE_SIZE << 1);
		return newLevelInfoRenderingPos;
	}
	
	private void renderEditLevel(Color wrapColor, Color renderingColor, String editLevelInfo,
			int fontSize, int rowOffset, int previousOptionStartX) {
		// setup screen rendering
		screen.setupTextRendering(fontSize, wrapColor);
		// get rendering position of "Mute" info
		Point editLevelInfoRenderingPos = screen.getStringCenteredPosition(editLevelInfo, fontSize, rowOffset);
		// wrapped rendering
		screen.renderWrapedText(editLevelInfo, renderingColor, previousOptionStartX, editLevelInfoRenderingPos.y);
		// clean rendering
		screen.cleanTextRendering();
		
		setupIconRenderingStartPos(editLevelInfoRenderingPos, GameConfig.TILE_SIZE << 1);
	}
	
	private void setupIconRenderingStartPos(Point pos, int iconRenderingYOffset) {
		if(iconRenderingStartPos == null) {
			iconRenderingStartPos = pos;
			this.iconRenderingYOffset = iconRenderingYOffset;
		}
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

	@Override
	public int getOptionsCount() {
		return 2;
	}
	
	private static enum EditorMenuOption {
		NEW_LEVEL, EDIT_LEVEL
	}

}
