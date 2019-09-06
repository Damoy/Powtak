package core.world.teleportation;

import core.entities.Entity;
import core.entities.player.Player;
import core.world.Tile;
import core.world.level.Level;
import rendering.Screen;
import rendering.Texture;

public class NextLevelTpPoint extends Entity{
	
	private NextLevelTpConfig config;

	public NextLevelTpPoint(Tile tile, int x, int y, NextLevelTpConfig config) {
		super(tile, x, y, Texture.TELEPORT_POINT);
		this.config = config;
	}

	@Override
	public void render(Screen s) {
		s.renderTex(texture, x, y);
	}
	
	public void activate(Player player) {
		if(config.getLevelDest() == null) throw new IllegalStateException("Level should exist at activation.");
		player.getLevel().getLevelChunck().grow();
		player.activateTeleportation();
		player.changeLevel(config.getLevelDest());
	}

	@Override
	public void update() {
		
	}
	
	public void setLevelDestination(Level level) {
		config.setLevelDest(level);
	}
	
	public int getDestX() {
		return config.getxDest();
	}
	
	public int getDestY() {
		return config.getyDest();
	}
	
	public Level getDestLevel() {
		return config.getLevelDest();
	}
	
	@Override
	public int getWidth() {
		return texture.getWidth();
	}
	
	@Override
	public int getHeight() {
		return texture.getHeight();
	}
	
}
