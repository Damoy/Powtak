package core.world;

import core.world.level.LevelChunck;
import rendering.Screen;
import utils.exceptions.PowtakException;

public class World {

	private LevelChunck levelChunck;
	
	public World(LevelChunck levelChunck) {
		this.levelChunck = levelChunck;
	}
	
	public void render(Screen s) {
		this.levelChunck.render(s);
	}
	
	public void update() throws PowtakException {
		this.levelChunck.update();
	}
	
	public int getLevelCount() {
		return levelChunck.count();
	}
	
}
