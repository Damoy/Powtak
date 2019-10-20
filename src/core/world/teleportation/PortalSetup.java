package core.world.teleportation;

import java.util.List;

import core.world.Tile;
import core.world.level.Level;
import core.world.level.ingame.InGameLevel;
import core.world.level.ingame.InGameLevelChunck;
import utils.Log;
import utils.exceptions.PowtakException;

public final class PortalSetup {

	private PortalSetup() {}
	
	public static void enableNextLevelPortals(InGameLevelChunck levelChunck) throws PowtakException {
		int lvlCount = levelChunck.count();
		if (lvlCount > 1) {
			List<Level> levels = levelChunck.getLevels();
			InGameLevel sourceLevel = null;
			InGameLevel destinationLevel = null;
			for(int i = 0; i < lvlCount - 1; ++i) {
				sourceLevel = (InGameLevel) levels.get(i);
				destinationLevel = (InGameLevel) levels.get(i + 1);
				enableNextLevelPortal(sourceLevel, destinationLevel);
			}
		} else {
			Log.warn("No next level portals.");
		}
	}
	
	public static void enableNextLevelPortal(InGameLevel sourceLevel, InGameLevel destinationLevel) throws PowtakException {
		int x = sourceLevel.getNextLevelPortalSourcePoint().getX();
		int y = sourceLevel.getNextLevelPortalSourcePoint().getY();
		Tile tile = sourceLevel.getMap().getNormTileAt(x, y);
		
		Portal nextLevelPortal = new NextLevelPortal(tile, x, y,
				destinationLevel, destinationLevel.getPlayerConfig().getX(), destinationLevel.getPlayerConfig().getY());
		sourceLevel.setNextLevelPortal(nextLevelPortal);
	}
	
}
