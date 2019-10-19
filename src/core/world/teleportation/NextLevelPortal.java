package core.world.teleportation;

import core.entities.player.Player;
import core.world.Tile;
import core.world.level.Level;
import utils.Utils;
import utils.exceptions.PowtakException;

public class NextLevelPortal extends Portal {

	private Level destinationLevel;
	
	public NextLevelPortal(Tile tile, int x, int y, Level destinationLevel, int destinationX, int destinationY)
			throws PowtakException {
		super(tile, x, y, destinationX, destinationY);
		Utils.validateNotNull(destinationLevel, "Destination level");
		this.destinationLevel = destinationLevel;
	}

	@Override
	public void activate(Player player) {
		player.getLevel().getLevelChunck().grow();
		player.activateTeleportation();
		player.setLevel(destinationLevel);
	}

	public Level getDestinationLevel() {
		return destinationLevel;
	}

	public void setDestinationLevel(Level destinationLevel) {
		this.destinationLevel = destinationLevel;
	}
	
}
