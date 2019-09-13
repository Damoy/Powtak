package core.world.teleportation;

import core.entities.Entity;
import core.entities.player.Player;
import core.world.Tile;
import core.world.level.Level;
import rendering.Screen;
import rendering.Texture;
import rendering.animation.Animation;
import rendering.animation.BasicAnimationOnTick;
import utils.Config;

public class NextLevelTpPoint extends Entity{
	
	private NextLevelTpConfig config;
	private Animation animation;
	
	public NextLevelTpPoint(Tile tile, int x, int y, NextLevelTpConfig config) {
		super(tile, x, y, Texture.TELEPORT_POINT_SPRITESHEET);
		this.config = config;
		this.x += 1;
		this.y += 1;
		this.animation = buildAnimation();
	}
	
	private Animation buildAnimation() {
		return BasicAnimationOnTick.get(getTexture(), Config.UPS, 1, 
				2, 2, 13, 13,
				18, 2, 13, 13,
				34, 2, 13, 13,
				50, 2, 13, 14);
	}

	@Override
	public void render(Screen s) {
		// s.renderTex(texture, x, y);
		animation.render(s, x, y);
	}
	
	public void activate(Player player) {
		if(config.getLevelDest() == null) throw new IllegalStateException("Level should exist at activation.");
		player.getLevel().getLevelChunck().grow();
		player.activateTeleportation();
		player.changeLevel(config.getLevelDest());
	}

	@Override
	public void update() {
		animation.update();
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
		// return texture.getWidth();
		return animation.getCurrentFrame().getWidth();
	}
	
	@Override
	public int getHeight() {
		// return texture.getHeight();
		return animation.getCurrentFrame().getHeight();
	}
	
}
