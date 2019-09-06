package core.entities.projectiles;

import core.entities.Direction;
import core.entities.Entity;
import core.world.Tile;
import core.world.level.Level;
import rendering.Screen;

public abstract class Projectile extends Entity{

	protected Level level;
	protected Direction direction;
	
	protected int dx;
	protected int dy;
	protected int xOffset;
	protected int yOffset;
	
	protected boolean dead;
	
	public Projectile(Level level, Direction direction, int x, int y) {
		super(null, x, y, null);
		this.level = level;
		this.direction = direction;
		this.dead = false;
		this.xOffset = 0;
		this.yOffset = 0;
		setDirection();
	}
	
	public abstract void render(Screen s);
	public abstract void update();
	
	private void setDirection() {
		dx = 0;
		dy = 0;
		
		switch(direction) {
			case UP:
				dy = -1;
				break;
			case DOWN:
				dy = 1;
				break;
			case LEFT:
				dx = -1;
				break;
			case RIGHT:
				dx = 1;
				break;
			default:
				throw new IllegalStateException();
		}
	}

	@Override
	public Tile getTile() {
		return level.getMap().getNormTileAt(x, y);
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void die() {
		dead = true;
	}
	
	public void resetPosOffsets() {
		xOffset = 0;
		yOffset = 0;
	}
	
	public void addXOffset(int dx) {
		xOffset = dx;
	}
	
	public void addYOffset(int dy) {
		yOffset = dy;
	}

}
