package core.menus;

import core.Core;
import core.configs.OptionsConfig;
import rendering.Screen;
import utils.exceptions.PowtakException;

// TODO
public class OptionsMenu extends Menu {
	
	private MenuSelector menuSelector;
	private OptionsConfig optionsConfig;
	
	public OptionsMenu(Core core, Screen screen, MenuSelector menuSelector, OptionsConfig optionsConfig) throws PowtakException {
		super(core, screen);
		this.optionsConfig = optionsConfig;
		this.menuSelector = menuSelector;
	}

	@Override
	public void update() throws PowtakException {
	}

	@Override
	protected void handleInput() throws PowtakException {
		
	}

	@Override
	public void render() {
	}

	@Override
	protected void reset() {
	}

}
