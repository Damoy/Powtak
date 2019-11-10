package core.menus;

import java.util.HashMap;
import java.util.Map;

import core.configs.CharacterSkin;
import rendering.Screen;
import rendering.Texture;
import utils.Pair;
import utils.exceptions.PowtakException;

public class MenuSelector {

	private Screen screen;
	private Texture texture;
	private Map<Menu, Pair<Integer, Integer>> menusSelections;
	
	public MenuSelector(Screen screen) {
		this.screen = screen;
		this.menusSelections = new HashMap<>();
	}
	
	public Texture updateTexture(CharacterSkin characterSkin) throws PowtakException {
		this.texture = CharacterSkin.getCharacterReducedIcon(characterSkin);
		return this.texture;
	}
	
	public void addEntry(Menu menu, int value, int capValue) {
		menusSelections.put(menu, new Pair<>(value, capValue));
	}

	public void update() {
		
	}
	
	public void render() {
		
	}
	
	public void resetTexture() {
		texture = null;
	}
	
	public void resetValue(Menu menu) {
		menusSelections.get(menu).setFirst(0);
	}
	
	public boolean increaseValue(Menu menu) {
		return increaseValue(menu, 1);
	}
	
	public boolean decreaseValue(Menu menu) {
		return increaseValue(menu, -1);
	}
	
	public boolean increaseValue(Menu menu, int offset) {
		Pair<Integer, Integer> menuSelection = menusSelections.get(menu);
		int value = menuSelection.getFirst();
		value += offset;
		int saveValue = value;
		value = capValue(value, menuSelection.getSecond());
		menuSelection.setFirst(value);
		return value == saveValue;
	}
	
	private int capValue(int value, int capValue) {
		if(value < 0)
			value = 0;
		if(value > capValue)
			value = capValue;
		return value;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public int getValue(Menu menu) {
		return menusSelections.get(menu).getFirst();
	}
	
	public void setValue(Menu menu, int value) {
		menusSelections.get(menu).setFirst(value);
	}

	public Screen getScreen() {
		return screen;
	}

}
