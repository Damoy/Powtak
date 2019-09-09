package core.world.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.world.Tile;
import rendering.Screen;
import utils.Colors;

public class DoorKeyEngine {
	
	private Map<Integer, DoorKeyEntry> memory;

	public DoorKeyEngine() {
		memory = new HashMap<>();
	}
	
	public int getKeyIdentifier(int rgb) {
		return Colors.blue(rgb);
	}
	
	public int getDoorIdentifier(int rgb) {
		return Colors.green(rgb);
	}
	
	public boolean addEntry(Key key, Door... doors) {
		if(!memory.containsKey(key.getId())) {
			memory.put(key.getId(), new DoorKeyEntry(key, doors));
			return true;
		}
		
		return false;
	}
	
	public void update() {
		memory.forEach((id, entry) -> {
			entry.key.update();
			entry.doors.forEach(door -> door.update());
		});
	}
	
	public void render(Screen s) {
		memory.forEach((id, entry) -> {
			entry.key.render(s);
			entry.doors.forEach(door -> door.render(s));
		});
	}
	
	public boolean interactIfCollision(Tile tile) {
		if(tile.isKeyed()) {
			return interactIfCollision(tile, tile.getKey());
		}
		
		return tile.isDoored();
	}
	
	private boolean interactIfCollision(Tile tile, Key key) {
		if(memory.containsKey(key.getId())) {
			memory.get(key.getId()).doors.forEach(door -> door.interact());
			memory.remove(key.getId());
			key.interact();
			return true;
		}
		
		return false;
	}
	
	private static class DoorKeyEntry{
		public List<Door> doors;
		public Key key;
		
		public DoorKeyEntry(Key key, Door... doors) {
			this.key = key;
			this.doors = Arrays.asList(doors);
		}
	}
}
