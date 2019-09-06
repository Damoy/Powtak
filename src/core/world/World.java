package core.world;

import core.world.level.Level;
import core.world.level.LevelChunck;
import core.world.teleportation.NextLevelTpPoint;
import core.world.teleportation.NextLevelTpConfig;
import rendering.Screen;

public class World {

	private LevelChunck levelChunck;
	
	public World(LevelChunck levelChunck) {
		this.levelChunck = levelChunck;
	}
	
	public void render(Screen s) {
		this.levelChunck.render(s);
	}
	
	public void update() {
		this.levelChunck.update();
	}
	
	public void addNextLevelTpPoint(int lvlSrcIndex, int lvlDestIndex) {
		Level levelSrc = levelChunck.get(lvlSrcIndex);
		Level levelDest = levelChunck.get(lvlDestIndex);
		
		if(levelSrc == null || levelDest == null) throw new IllegalStateException();
		
		int x = levelSrc.getNextLevelTpPointSource().getX();
		int y = levelSrc.getNextLevelTpPointSource().getY();
		Tile tile = levelSrc.getMap().getNormTileAt(x, y);
		
		NextLevelTpPoint nextLevelTpPoint = new NextLevelTpPoint(tile, x, y, NextLevelTpConfig.of(levelDest));
		levelSrc.setNextLevelTpPoint(nextLevelTpPoint);
	}
	
	public int getLevelCount() {
		return levelChunck.count();
	}
	
}
