package core.world.level.editor;

import core.Core;
import core.world.level.menu.MenuLevel;
import rendering.Screen;
import utils.Utils;
import utils.exceptions.PowtakException;

// TODO GRAPHICAL LEVEL EDITOR
public class LevelEditor {
	
	private Core core;
	private Screen screen;
	private LevelEditorMouseListener mouseListener;
	private MenuLevel level;

	public LevelEditor(Core core, Screen screen) {
		this.core = core;
		this.screen = screen;
		this.mouseListener = new LevelEditorMouseListener(this);
		this.level = (MenuLevel) MenuLevel.from(screen, Utils.levelPath("editor/prototype.lvl"));
	}
	
	public void update() {
		try {
			level.update();
		} catch (PowtakException e) {
			e.printStackTrace();
		}
	}
	
	public void render() {
		level.render();
	}
	
	private void renderCursor() {
		
	}
	
	private void renderCommandsOption() {
		
	}
	
	private void renderCommandsMenu() {
		
	}
	
}
