package core.world.level;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import core.entities.Entity;
import core.entities.enemies.Enemy;
import core.entities.enemies.EnemyChunck;
import core.entities.items.EnergyChunck;
import core.entities.player.Player;
import core.entities.player.PlayerConfig;
import core.entities.walls.Wall;
import core.entities.walls.WallChunck;
import core.world.Map;
import core.world.Tile;
import core.world.items.DoorKeyEngine;
import core.world.light.LightChunck;
import core.world.light.LightSpot;
import core.world.teleportation.NextLevelTpPoint;
import core.world.teleportation.NextLevelTpPointSource;
import rendering.Screen;
import rendering.animation.ExplosionAnimation;
import rendering.particles.Particle;
import rendering.particles.ParticleEngine;
import rendering.particles.ParticleMovement;
import utils.AABB;
import utils.Config;

public class Level {

	private LevelChunck levelChunck;
	private Screen s;
	private Map map;
	private Player player;
	private WallChunck wallChunk;
	private LightChunck lightChunck;
	private ParticleEngine particleEngine;
	private DoorKeyEngine doorKeyEngine;
	private PlayerConfig playerConfig;
	private NextLevelTpPointSource nextLevelTpPointSource;
	private NextLevelTpPoint nextLevelTpPoint;
	private EnemyChunck enemyChunck;
	private EnergyChunck energyChunck;
	
	private Level(Screen screen, Map map, WallChunck wallChunk, DoorKeyEngine doorKeyEngine,
			PlayerConfig playerConfig, NextLevelTpPointSource nextLevelTpPointSource, EnemyChunck enemyChunck, EnergyChunck energyChunck) {
		this.levelChunck = null;
		this.s = screen;
		this.map = map;
		this.wallChunk = wallChunk;
		this.lightChunck = buildLightChunck();
		this.particleEngine = buildParticleEngine();
		this.doorKeyEngine = doorKeyEngine;
		this.playerConfig = playerConfig;
		this.nextLevelTpPointSource = nextLevelTpPointSource;
		this.nextLevelTpPoint = null;
		this.enemyChunck = enemyChunck;
		this.energyChunck = energyChunck;
		this.player = null;
	}
	
	private LightChunck buildLightChunck() {
		LightChunck lightChunck = new LightChunck();
		lightChunck.add(new LightSpot(s, 0, 96, Config.TILE_SIZE, Color.CYAN));
		lightChunck.add(new LightSpot(s, 32, 96, Config.TILE_SIZE, Color.MAGENTA));
		lightChunck.add(new LightSpot(s, 64, 96, Config.TILE_SIZE, Color.GREEN));
		lightChunck.add(new LightSpot(s, 96, 96, Config.TILE_SIZE, Color.WHITE));
		return lightChunck;
	}
	
	private ParticleEngine buildParticleEngine() {
		ParticleEngine particleEngine = ParticleEngine.empty();
//		List<Particle> particles = new ArrayList<>();
//		particles.add(new Particle(this, particleEngine, 0, 100, 100, new ParticleMovement(100, 100, 25, 25), new ExplosionAnimation(4, 1.0f)));
//		particles.add(new Particle(this, particleEngine, 0, 100, 100, new ParticleMovement(100, 100, 25, -25), new ExplosionAnimation(4, 1.0f)));
//		particles.add(new Particle(this, particleEngine, 0, 100, 100, new ParticleMovement(100, 100, -25, 25), new ExplosionAnimation(4, 1.0f)));
//		particles.add(new Particle(this, particleEngine, 0, 100, 100, new ParticleMovement(100, 100, -25, -25), new ExplosionAnimation(4, 1.0f)));
//		particleEngine.addComponent(particles);
		return particleEngine;
	}
	
	public void reset() {
		// TODO
	}
	
	public static Level from(Screen screen, LevelConfig config) {
		return new Level(screen, new Map(config.getRows(), config.getCols(), config.getTiles()),
				config.getWallChunk(), config.getDoorKeyEngine(), config.getPlayerConfig(),
				config.getNextLevelTpPointSource(), config.getEnemyChunck(), config.getEnergyChunck());
	}
	
	public static List<Level> froms(Screen screen, List<String> levelFileNames){
		List<Level> levels = new ArrayList<>();
		levelFileNames.forEach(lfn -> levels.add(Level.from(screen, LevelLoader.get().load(lfn))));
		return levels;
	}
	
	public void render() {
		map.render(s);
		wallChunk.render(s);
		doorKeyEngine.render(s);
		renderNextLevelTpPoint(s);
		enemyChunck.render(s);
		energyChunck.render(s);
		// particleEngine.render(s);
		// lightChunck.enlight(0.5f);
	}
	
	private void renderNextLevelTpPoint(Screen s) {
		if(nextLevelTpPoint != null)
			nextLevelTpPoint.render(s);
	}
	
	public void update() {
		setEnemyLevel();
		map.update();
		wallChunk.update();
		doorKeyEngine.update();
		enemyChunck.update();
		energyChunck.update();
		updateNextLevelTpPoint();
		particleEngine.update();
	}
	
	private void setEnemyLevel() {
		enemyChunck.setEnemiesLevel(this);
	}
	
	public boolean playerCollidesWith(Entity entity) {
		return player.collides(entity);
	}
	
	public Enemy getEnemyOn(int row, int col) {
		return enemyChunck.get(row, col);
	}
	
	private void updateNextLevelTpPoint() {
		if(nextLevelTpPoint != null)
			nextLevelTpPoint.update();
	}
	
	public boolean interactIfCollisionDoorKey(Tile tile) {
		return doorKeyEngine.interactIfCollision(tile);
	}
	
	public Enemy enemyCollision(AABB box) {
		return enemyChunck.collision(box);
	}
	
	public Wall wallCollision(AABB box) {
		return wallChunk.collision(box);
	}
	
	public Map getMap() {
		return map;
	}

	public WallChunck getWallChunk() {
		return wallChunk;
	}

	public PlayerConfig getPlayerConfig() {
		return playerConfig;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setNextLevelTpPoint(NextLevelTpPoint nextLevelTpPoint) {
		this.nextLevelTpPoint = nextLevelTpPoint;
		map.setNextLevelTpPoint(nextLevelTpPoint);
		utils.Logger.logn("Teleport dest x: " + nextLevelTpPoint.getDestX() + ", y: " + nextLevelTpPoint.getDestY());
	}

	public NextLevelTpPointSource getNextLevelTpPointSource() {
		return nextLevelTpPointSource;
	}

	public LevelChunck getLevelChunck() {
		return levelChunck;
	}

	public void setLevelChunck(LevelChunck levelChunck) {
		this.levelChunck = levelChunck;
	}
}
