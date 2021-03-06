package core.world.teleportation;

import core.configs.GameConfig;
import core.entities.Entity;
import core.entities.player.Player;
import core.world.Tile;
import rendering.Screen;
import rendering.Texture;
import rendering.animation.Animation;
import rendering.animation.BasicAnimationOnTick;
import utils.exceptions.PowtakException;

public abstract class Portal extends Entity {
	
	protected Animation animation;
	protected int destinationX;
	protected int destinationY;
	
	public Portal(Tile tile, int x, int y, int destinationX, int destinationY) throws PowtakException {
		super(tile, x, y, Texture.TELEPORT_POINT_SPRITESHEET);
		this.destinationX = destinationX;
		this.destinationY = destinationY;
		this.x += 1;
		this.y += 1;
		this.animation = buildAnimation();
	}
	
	private Animation buildAnimation() {
		return BasicAnimationOnTick.get(getTexture(), GameConfig.UPS, 1, 
				2, 2, 13, 13,
				18, 2, 13, 13,
				34, 2, 13, 13,
				50, 2, 13, 14);
	}

	@Override
	public void render(Screen s) {
		animation.render(s, x, y);
	}
	
	public abstract void activate(Player player);
	
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
