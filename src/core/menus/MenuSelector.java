package core.menus;

import java.util.HashMap;
import java.util.Map;

import rendering.Screen;
import rendering.Texture;
import utils.Pair;

public class MenuSelector {

	private Screen screen;
	private Texture texture;
	private Map<Menu, Pair<Integer, Integer>> menusSelections;
	
	public MenuSelector(Screen screen) {
		this.screen = screen;
		this.menusSelections = new HashMap<>();
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
	
	public void increaseValue(Menu menu) {
		increaseValue(menu, 1);
	}
	
	public void decreaseValue(Menu menu) {
		increaseValue(menu, -1);
	}
	
	public void increaseValue(Menu menu, int offset) {
		Pair<Integer, Integer> menuSelection = menusSelections.get(menu);
		int value = menuSelection.getFirst();
		value += offset;
		value = capValue(value, menuSelection.getSecond());
		menuSelection.setFirst(value);
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
