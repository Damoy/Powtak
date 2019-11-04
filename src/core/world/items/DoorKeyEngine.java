package core.world.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import core.world.Tile;
import rendering.Screen;

public class DoorKeyEngine {
	
	private Map<Integer, DoorKeyEntry> memory;
	private Set<Integer> doorIdSet;
	private List<Door> waitingList;

	public DoorKeyEngine() {
		memory = new HashMap<>();
		doorIdSet = new HashSet<>();
		waitingList = new ArrayList<>();
	}
	
	public Door addDoor(Tile tile, int id) {
		Door door = null;
		
		if(memory.containsKey(id)) {
			DoorKeyEntry doorKeyEntry = memory.get(id);
			door = new Door(tile, id, tile.getX(), tile.getY());
			doorKeyEntry.addDoor(door);
		} else {
			door = new Door(tile, id, tile.getX(), tile.getY());
			waitingList.add(door);
		}
		
		tile.setDoor(door);
		doorIdSet.add(id);
		return door;
	}
	
	public Key addKey(Tile tile, int id) {
		if(memory.containsKey(id)) {
			throw new IllegalStateException("Duplicated Keys");
		}
		
		Key key = new Key(tile, id, tile.getX(), tile.getY());
		memory.put(id, new DoorKeyEntry(key, waitingList.stream().filter(door -> door.getId() == id).collect(Collectors.toList())));
		tile.setKey(key);
		return key;
	}
	
	public void setReady(){
		Set<Integer> ids = memory.keySet();
		for(Door door : waitingList) {
			if(ids.contains(door.getId())) {
				memory.get(door.getId()).addDoor(door);
			} else {
				door.getTile().setDoor(null);
			}
		}
		
		waitingList.clear();
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
		public Key key;
		public List<Door> doors;
		
		public DoorKeyEntry(Key key, List<Door> doors) {
			this.key = key;
			this.doors = doors;
		}
		
		public void addDoor(Door door) {
			doors.add(door);
		}
	}
	
}
