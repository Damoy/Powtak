package core.world.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.world.Tile;
import rendering.Screen;
import utils.Colors;
import utils.Config;

public class DoorKeyEngine {
	
	private Map<Integer, DoorKeyEntry> memory;
	private boolean[] entryExistence;

	private DoorKeyEngine() {
		memory = new HashMap<>();
		entryExistence = new boolean[Config.DOOR_KEY_ENGINE_SIZE];
	}
	
	public static DoorKeyEngine get() {
		return new DoorKeyEngine();
	}
	
	public int getKeyIdentifier(int rgb) {
		return Colors.blue(rgb);
	}
	
	public int getDoorIdentifier(int rgb) {
		return Colors.green(rgb);
	}
	
	public boolean addEntry(int id, Key key, Door... doors) {
		if(!entryExistence[id]) {
			memory.put(id, new DoorKeyEntry(key, doors));
			entryExistence[id] = true;
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
		if(entryExistence[key.getId()]) {
			entryExistence[key.getId()] = false;
			memory.get(key.getId()).doors.forEach(door -> door.forget());
			memory.remove(key.getId());
			key.forget();
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
