package core.world;

import java.util.ArrayList;
import java.util.List;

import core.world.level.Level;
import core.world.level.LevelChunck;
import core.world.teleportation.PortalSetup;
import rendering.Screen;
import utils.exceptions.PowtakException;

@SuppressWarnings("unused")
public final class WorldGeneration {
	
	private static final String LEVEL_1_FILE_NAME = "level1";
	private static final String LEVEL_2_FILE_NAME = "level2";
	private static final String LEVEL_3_FILE_NAME = "level3";
	private static final String LEVEL_4_FILE_NAME = "level4";
	private static final String LEVEL_5_FILE_NAME = "level5";
	
	private static final List<String> levelsFileNames = new ArrayList<>();
	
	static {
		levelsFileNames.add(LEVEL_1_FILE_NAME);
		levelsFileNames.add(LEVEL_4_FILE_NAME);
	}
	
	private WorldGeneration() {}
	
	public static World generateWorldFromPredefinedLevels(Screen screen) throws PowtakException {
		List<Level> levels = Level.from(screen, levelsFileNames);
		LevelChunck levelChunck = new LevelChunck(levels);
		PortalSetup.enableNextLevelPortals(levelChunck);
		return new World(levelChunck);
	}

}