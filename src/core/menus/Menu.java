package core.menus;

import core.Core;
import rendering.Screen;
import sound.SoundEngine;
import utils.exceptions.PowtakException;

public abstract class Menu {

	protected Core core;
	protected SoundEngine soundEngine;
	protected Screen screen;
	
	public Menu(Core core, Screen screen) throws PowtakException {
		this.core = core;
		this.soundEngine = core.getSoundEngine();
		this.screen = screen;
	}
	
	public abstract void update() throws PowtakException;
	protected abstract void handleInput() throws PowtakException;
	public abstract void render() throws PowtakException;
	protected abstract void reset();
	public abstract int getOptionsCount();
	
}
