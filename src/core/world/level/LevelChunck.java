package core.world.level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.entities.player.Player;
import rendering.Screen;

public class LevelChunck {

	private Map<String, Level> levels;
	private Player player;
	private String idCurrent;
	private int idCurrentPtr;
	
	public LevelChunck(List<Level> levels) {
		this.levels = new HashMap<>();
		this.idCurrentPtr = 0;
		this.idCurrent = levels.get(idCurrentPtr).getId();
		
		levels.forEach(level -> {
			this.levels.put(level.getId(), level);
			level.setLevelChunck(this);	
		});
		
		this.player = new Player(levels.get(0));
	}
	
	public void reload(Screen s, String levelId) {
		Level reloadedLevel = Level.from(s, LevelLoader.get().load(levelId));
		reloadedLevel.setLevelChunck(this);
		levels.put(levelId, reloadedLevel);
		player.setLevel(reloadedLevel);
	}
	
	public void render(Screen s) {
		getCurrent().render();
		player.render(s);
	}
	
	public void update() {
		getCurrent().update();
		player.update();
	}
	
	public Level getCurrent() {
		return levels.get(idCurrent);
	}
	
	public Level get(String id) {
		return levels.get(id);
	}
	
	public void grow() {
		++idCurrentPtr;
		idCurrent = (String) this.levels.keySet().toArray()[idCurrentPtr];
	}
	
	public void decrease() {
		--idCurrentPtr;
		idCurrent = (String) this.levels.keySet().toArray()[idCurrentPtr];
	}
	
	public int count() {
		return levels.size();
	}
}
