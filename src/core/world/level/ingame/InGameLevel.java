package core.world.level.ingame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.entities.Entity;
import core.entities.enemies.EnemyChunck;
import core.entities.items.EnergyChunck;
import core.entities.player.Player;
import core.entities.player.PlayerConfig;
import core.entities.walls.WallChunck;
import core.world.Map;
import core.world.items.DoorKeyEngine;
import core.world.level.Level;
import core.world.level.loading.LevelConfig;
import core.world.level.loading.LevelLoader;
import core.world.teleportation.PortalSourcePoint;
import rendering.Screen;
import rendering.particles.ParticleEngine;
import utils.Log;
import utils.exceptions.PowtakException;

public class InGameLevel extends Level {
	
	private Player player;
	private PlayerConfig playerConfig;

	protected InGameLevel(String id, Screen screen, Map map, WallChunck wallChunk, DoorKeyEngine doorKeyEngine,
			PlayerConfig playerConfig, PortalSourcePoint nextLevelPortalSourcePoint,
			EnemyChunck enemyChunck, EnergyChunck energyChunck, ParticleEngine particleEngine) {
		super(id, screen, map, wallChunk, doorKeyEngine, nextLevelPortalSourcePoint, enemyChunck, energyChunck,
				particleEngine);
		this.playerConfig = playerConfig;
		this.player = null;
	}
	
	public static List<Level> from(Screen screen, List<File> levelFiles){
		LevelLoader levelLoader = LevelLoader.get();
		List<Level> levels = new ArrayList<>();
		for(File levelFileName : levelFiles) {
			try {
				levels.add(InGameLevel.from(screen, levelLoader.loadCustomLevel(levelFileName)));
			} catch(PowtakException e) {
				Log.error(e.getMessage());
			}
		}
		return levels;
	}

	public static InGameLevel from(Screen screen, LevelConfig config) {
		String id = config.getId();
		Map map = new Map(config.getRows(), config.getCols(), config.getTiles());
		WallChunck wallChunk = config.getWallChunk();
		DoorKeyEngine doorKeyEngine = config.getDoorKeyEngine();
		PlayerConfig playerConfig = config.getPlayerConfig();
		PortalSourcePoint nextLevelPortalSourcePoint = config.getNextLevelPortalSourcePoint();
		EnemyChunck enemyChunck = config.getEnemyChunck();
		EnergyChunck energyChunck = config.getEnergyChunck();
		ParticleEngine particleEngine = null;
		
		return new InGameLevel(id, screen, map, wallChunk, doorKeyEngine,
				playerConfig, nextLevelPortalSourcePoint, enemyChunck,
				energyChunck, particleEngine);
	}
	
	@Override
	public void update() throws PowtakException {
		super.update();
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	public boolean playerCollidesWith(Entity entity) {
		return player.collides(entity);
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
	
}
