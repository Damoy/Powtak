package core.world.level.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.entities.enemies.EnemyChunck;
import core.entities.items.EnergyChunck;
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

public class MenuLevel extends Level {

	protected MenuLevel(String id, Screen screen, Map map, WallChunck wallChunk, DoorKeyEngine doorKeyEngine,
			PortalSourcePoint nextLevelPortalSourcePoint, EnemyChunck enemyChunck,
			EnergyChunck energyChunck, ParticleEngine particleEngine) {
		super(id, screen, map, wallChunk, doorKeyEngine, nextLevelPortalSourcePoint, enemyChunck, energyChunck,
				particleEngine);
	}
	
	public static Level from(Screen screen, String levelFileName){
		LevelLoader levelLoader = LevelLoader.get();
		Level level = null;
		
		try {
			level = from(screen, levelLoader.loadCustomLevel(levelFileName));
		} catch(PowtakException e) {
			Log.error(e.getMessage());
		}
		
		return level;
	}
	
	public static List<Level> from(Screen screen, List<File> levelFiles){
		LevelLoader levelLoader = LevelLoader.get();
		List<Level> levels = new ArrayList<>();
		for(File levelFileName : levelFiles) {
			try {
				levels.add(from(screen, levelLoader.loadCustomLevel(levelFileName)));
			} catch(PowtakException e) {
				Log.error(e.getMessage());
			}
		}
		return levels;
	}

	public static Level from(Screen screen, LevelConfig config) {
		String id = config.getId();
		Map map = new Map(config.getRows(), config.getCols(), config.getTiles());
		WallChunck wallChunk = config.getWallChunk();
		DoorKeyEngine doorKeyEngine = config.getDoorKeyEngine();
		PortalSourcePoint nextLevelPortalSourcePoint = config.getNextLevelPortalSourcePoint();
		EnemyChunck enemyChunck = config.getEnemyChunck();
		EnergyChunck energyChunck = config.getEnergyChunck();
		ParticleEngine particleEngine = null;
		
		return new MenuLevel(id, screen, map, wallChunk, doorKeyEngine,
				nextLevelPortalSourcePoint, enemyChunck,
				energyChunck, particleEngine);
	}

}
