package core.menus;

import core.Core;
import rendering.Screen;
import utils.exceptions.PowtakException;

public abstract class Menu {

	protected Core core;
	protected Screen screen;
	
	public Menu(Core core, Screen screen) throws PowtakException {
		this.screen = screen;
		this.core = core;
	}
	
	public abstract void update() throws PowtakException;
	protected abstract void handleInput() throws PowtakException;
	public abstract void render() throws PowtakException;
	protected abstract void reset();
	public abstract int getOptionsCount();
	
}
