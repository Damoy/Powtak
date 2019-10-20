package core.world.level;

import core.entities.enemies.Enemy;
import core.entities.enemies.EnemyChunck;
import core.entities.items.EnergyChunck;
import core.entities.walls.Wall;
import core.entities.walls.WallChunck;
import core.world.Map;
import core.world.Tile;
import core.world.items.DoorKeyEngine;
import core.world.teleportation.Portal;
import core.world.teleportation.PortalSourcePoint;
import rendering.Screen;
import rendering.particles.ParticleEngine;
import utils.AABB;
import utils.Log;
import utils.exceptions.PowtakException;

public abstract class Level {

	// is level file path
	protected String id;
	
	protected LevelChunck levelChunck;
	protected Screen s;
	protected Map map;

	protected ParticleEngine particleEngine;
	protected DoorKeyEngine doorKeyEngine;
	
	protected WallChunck wallChunk;
	protected EnemyChunck enemyChunck;
	protected EnergyChunck energyChunck;
	
	protected PortalSourcePoint nextLevelPortalSourcePoint;
	protected Portal nextLevelPortal;
	
	protected boolean shouldReset;
	
	protected Level(String id, Screen screen, Map map, WallChunck wallChunk, DoorKeyEngine doorKeyEngine,
			PortalSourcePoint nextLevelPortalSourcePoint, EnemyChunck enemyChunck,
			EnergyChunck energyChunck, ParticleEngine particleEngine) {
		this.id = id;
		this.s = screen;
		this.map = map;
		this.wallChunk = wallChunk;
		this.particleEngine = particleEngine;
		this.doorKeyEngine = doorKeyEngine;
		this.nextLevelPortalSourcePoint = nextLevelPortalSourcePoint;
		this.enemyChunck = enemyChunck;
		this.energyChunck = energyChunck;
		this.nextLevelPortal = null;
		this.levelChunck = null;
		this.shouldReset = false;
		this.setEnemiesLevel();
	}
	
	public void reset() {
		shouldReset = true;
	}
	
	public void update() throws PowtakException {
		map.update();
		updateWalls();
		updateDoorsKeys();
		updateEnemies();
		updateEnergies();
		updateNextLevelPortalSourcePoint();
		updateParticles();
		checkLevelReset();
	}
	
	private void updateNextLevelPortalSourcePoint() {
		if(nextLevelPortal != null)
			nextLevelPortal.update();
	}
	
	private void updateWalls() {
		if(wallChunk != null) {
			wallChunk.update();
		}
	}
	
	private void updateDoorsKeys() {
		if(doorKeyEngine != null) {
			doorKeyEngine.update();
		}
	}
	
	private void updateEnemies() {
		if(enemyChunck != null) {
			enemyChunck.update();
		}
	}
	
	private void updateEnergies() {
		if(energyChunck != null) {
			energyChunck.update();
		}
	}
	
	private void updateParticles() {
		if(particleEngine != null) {
			particleEngine.update();
		}
	}
	
	public void render() {
		map.render(s);
		renderWalls(s);
		renderDoorsKeys(s);
		renderNextLevelPortalSourcePoint(s);
		renderEnemies(s);
		renderEnergies(s);
		renderParticles(s);
	}
	
	private void renderNextLevelPortalSourcePoint(Screen s) {
		if(nextLevelPortal != null)
			nextLevelPortal.render(s);
	}
	
	private void renderWalls(Screen s) {
		if(wallChunk != null) {
			wallChunk.render(s);
		}
	}
	
	private void renderDoorsKeys(Screen s) {
		if(doorKeyEngine != null) {
			doorKeyEngine.render(s);
		}
	}
	
	private void renderEnemies(Screen s) {
		if(enemyChunck != null) {
			enemyChunck.render(s);
		}
	}
	
	private void renderEnergies(Screen s) {
		if(energyChunck != null) {
			energyChunck.render(s);
		}
	}
	
	private void renderParticles(Screen s) {
		if(particleEngine != null) {
			particleEngine.render(s);
		}
	}
	
	private void checkLevelReset() throws PowtakException {
		if(shouldReset) {
			shouldReset = false;
			levelChunck.reload(s, id, nextLevelPortalSourcePoint, nextLevelPortal);
		}
	}
	
	private void setEnemiesLevel() {
		if(enemyChunck != null) {
			enemyChunck.setEnemiesLevel(this);
		}
	}
	
	public Enemy getEnemyOn(int row, int col) {
		return enemyChunck.get(row, col);
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

	public void setNextLevelPortal(Portal portal) {
		if(portal != null) {
			nextLevelPortal = portal;
			map.setNextLevelPortal(portal);
			Log.info("Teleport dest x: " + portal.getDestinationX() + ", y: " + portal.getDestinationY());
		}
	}

	public LevelChunck getLevelChunck() {
		return levelChunck;
	}

	public void setLevelChunck(LevelChunck levelChunck) {
		this.levelChunck = levelChunck;
	}

	public String getId() {
		return id;
	}

	public PortalSourcePoint getNextLevelPortalSourcePoint() {
		return nextLevelPortalSourcePoint;
	}

	public void setNextLevelPortalSourcePoint(PortalSourcePoint nextLevelPortalSourcePoint) {
		this.nextLevelPortalSourcePoint = nextLevelPortalSourcePoint;
	}

	public Portal getNextLevelPortal() {
		return nextLevelPortal;
	}

}
