package core.world.teleportation;

import java.util.List;

import core.world.Tile;
import core.world.level.Level;
import core.world.level.LevelChunck;
import utils.exceptions.PowtakException;

public final class PortalSetup {

	private PortalSetup() {}
	
	public static void enableNextLevelPortals(LevelChunck levelChunck) throws PowtakException {
		int lvlCount = levelChunck.count();
		if (lvlCount > 1) {
			List<Level> levels = levelChunck.getLevels();
			for(int i = 0; i < lvlCount - 1; ++i) {
				enableNextLevelPortal(levels.get(i), levels.get(i + 1));
			}
		}
	}
	
	public static void enableNextLevelPortal(Level sourceLevel, Level destinationLevel) throws PowtakException {
		int x = sourceLevel.getNextLevelPortalSourcePoint().getX();
		int y = sourceLevel.getNextLevelPortalSourcePoint().getY();
		Tile tile = sourceLevel.getMap().getNormTileAt(x, y);
		
		Portal nextLevelPortal = new Portal(tile, x, y,
				destinationLevel, destinationLevel.getPlayerConfig().getX(), destinationLevel.getPlayerConfig().getY());
		sourceLevel.setNextLevelPortal(nextLevelPortal);
	}
	
}
