package core.entities.enemies;

import java.util.ArrayList;
import java.util.List;

import core.entities.Direction;
import core.entities.projectiles.Projectile;
import core.entities.projectiles.ZombieProjectile;
import core.world.Tile;
import rendering.Screen;
import rendering.Texture;
import rendering.animation.Animation;
import rendering.animation.BasicAnimationOnCall;
import rendering.animation.ExplosionAnimation;
import utils.Config;
import utils.Utils;
import utils.TickCounter;

public class StaticZombie extends Zombie{

	private List<Projectile> projectiles;
	private Animation animation;
	private float scale;
	private int shootSpeed;
	private int ticksCap;
	private TickCounter shootCounter;
	
	public StaticZombie(Tile tile, EnemyChunck enemyChunck, int x, int y, float scale, Direction initialDirection) {
		super(null, tile, enemyChunck, x, y, Texture.STATIC_ZOMBIE_SPRITESHEET, initialDirection);
		this.scale = scale;
		this.shootSpeed = Utils.irand(Config.UPS >> 2, Config.UPS);
		this.ticksCap = shootSpeed;
		this.projectiles = new ArrayList<>();
		this.shootCounter = new TickCounter(shootSpeed);
		this.animation = generateAnimation();
	}
	
	private Animation generateAnimation() {
		switch(direction) {
			case DOWN:
				return BasicAnimationOnCall.get(getTexture(), ticksCap, scale, 
						0, 0, 12, 13,
						13, 0, 12, 13);
			case UP:
				return BasicAnimationOnCall.get(getTexture(), ticksCap, scale, 
						26, 0, 12, 13,
						39, 0, 12, 13);
			case RIGHT:
				return BasicAnimationOnCall.get(getTexture(), ticksCap, scale, 
						52, 0, 8, 13,
						61, 0, 8, 13);
			case LEFT:
				return BasicAnimationOnCall.get(getTexture(), ticksCap, scale, 
						70, 0, 8, 13,
						79, 0, 8, 13);
		default:
			throw new IllegalStateException();
		}
	}
	
	private int getRenderingXOffset() {
		int ts = Config.TILE_SIZE;
		int w = getWidth();
		return (ts - w) >> 1;
	}
	
	private int getRenderingYOffset() {
		int ts = Config.TILE_SIZE;
		int h = getHeight();
		return (ts - h) >> 1;
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
	public void render(Screen s) {
		animation.render(s, x + getRenderingXOffset(), y + getRenderingYOffset());
		renderProjectiles(s);
	}
	
	private void renderProjectiles(Screen s) {
		projectiles.forEach(projectile -> projectile.render(s));
	}

	@Override
	public void update() {
		checkLevelExists();
		animation.update();
		updateProjectiles();
		removeDeadProjectiles();
		shoot();
	}
	
	private void checkLevelExists() {
		if(level == null) throw new IllegalStateException("Level should not be null !");
	}
	
	private void shoot() {
		shootCounter.increment();
		if(shootCounter.isStopped()) {
			generateProjectile();
			shootCounter.reset();
		}
	}
	
	private void updateProjectiles() {
		int xoff = getRenderingXOffset();
		int yoff = getRenderingYOffset();
		
		projectiles.forEach(projectile -> projectile.addXOffset(xoff));
		projectiles.forEach(projectile -> projectile.addYOffset(yoff));
		projectiles.forEach(projectile -> projectile.update());
	}
	
	private void removeDeadProjectiles() {
		projectiles.removeIf(projectile -> projectile.isDead());
	}
	
	private void generateProjectile() {
		projectiles.add(new ZombieProjectile(getLevel(), direction, new ExplosionAnimation(8, 1.0f), x, y));
	}

}
