package core.world;

import core.entities.player.Player;
import core.world.level.LevelChunck;
import core.world.level.ingame.InGameLevelChunck;
import rendering.Screen;
import utils.Optional;
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
	
	public Optional<Player> getPlayer(){
		Optional<Player> player = new Optional<Player>();
		if(levelChunck instanceof InGameLevelChunck) {
			InGameLevelChunck inGameLevelChunck = (InGameLevelChunck) levelChunck;
			player.setElement(inGameLevelChunck.getPlayer());
		}
		return player;
	}
	
}
