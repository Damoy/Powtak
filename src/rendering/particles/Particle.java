package rendering.particles;

import core.entities.Entity;
import core.world.Tile;
import core.world.level.Level;
import rendering.Screen;
import rendering.animation.BasicAnimation;

public class Particle extends Entity{

	private ParticleEngine engine;
	private ParticleMovement movement;
	private BasicAnimation animation;
	private Level level;
	private int componentId;
	
	public Particle(Level level, ParticleEngine engine, int componentId, int x, int y, ParticleMovement movement, BasicAnimation animation) {
		super(null, x, y, null);
		this.componentId = componentId;
		this.movement = movement;
		this.engine = engine;
		this.animation = animation;
		this.level = level;
	}

	@Override
	public void render(Screen s) {
		animation.render(s, x, y);
	}

	@Override
	public void update() {
		movement.update(this);
		animation.update();
		if(movement.isStopped()) {
			engine.remove(componentId, this);
		}
	}
	
	@Override
	public int getWidth() {
		return animation.getCurrentFrame().getWidth();
	}

	@Override
	public int getHeight() {
		return animation.getCurrentFrame().getHeight();
	}
	
	@Override
	public Tile getTile() {
		return level.getMap().getNormTileAt(x, y);
	}
	
}
