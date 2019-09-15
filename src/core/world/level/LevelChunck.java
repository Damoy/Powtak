package core.world.level;

import java.util.List;

import core.entities.player.Player;
import rendering.Screen;

public class LevelChunck {

	private List<Level> levels;
	private Player player;
	private int ptr;
	
	public LevelChunck(List<Level> levels) {
		this.levels = levels;
		this.ptr = 0;
		this.levels.forEach(level -> level.setLevelChunck(this));
		this.player = new Player(levels.get(0), 0, 0);
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
		return levels.get(ptr);
	}
	
	public Level get(int index) {
		return levels.get(index);
	}
	
	public void grow() {
		++ptr;
	}
	
	public void decrease() {
		--ptr;
	}
	
	public int count() {
		return levels.size();
	}
}
