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
import utils.Utils;
import utils.exceptions.PowtakException;

public class Portal extends Entity{
	
	private Level destinationLevel;
	private int destinationX;
	private int destinationY;
	private Animation animation;
	
	public Portal(Tile tile, int x, int y,  Level destinationLevel, int destinationX, int destinationY) throws PowtakException {
		super(tile, x, y, Texture.TELEPORT_POINT_SPRITESHEET);
		Utils.validateNotNull(destinationLevel, "Destination level");
		this.destinationLevel = destinationLevel;
		this.destinationX = destinationX;
		this.destinationY = destinationY;
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
		animation.render(s, x, y);
	}
	
	public void activate(Player player) {
		player.getLevel().getLevelChunck().grow();
		player.activateTeleportation();
		player.setLevel(destinationLevel);
	}

	@Override
	public void update() {
		animation.update();
	}
	
	@Override
	public int getWidth() {
		return animation.getCurrentFrame().getWidth();
	}
	
	@Override
	public int getHeight() {
		return animation.getCurrentFrame().getHeight();
	}

	public Level getDestinationLevel() {
		return destinationLevel;
	}

	public void setDestinationLevel(Level destinationLevel) {
		this.destinationLevel = destinationLevel;
	}

	public int getDestinationX() {
		return destinationX;
	}

	public void setDestinationX(int destinationX) {
		this.destinationX = destinationX;
	}

	public int getDestinationY() {
		return destinationY;
	}

	public void setDestinationY(int destinationY) {
		this.destinationY = destinationY;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
}
