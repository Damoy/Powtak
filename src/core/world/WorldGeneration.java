package core.world;

import java.util.Arrays;

import core.world.level.Level;
import core.world.level.LevelChunck;
import rendering.Screen;

public final class WorldGeneration {
	
	private static final String LEVEL_1_FILE_NAME = "level1.png";
	private static final String LEVEL_2_FILE_NAME = "level2.png";
	private static final String LEVEL_3_FILE_NAME = "level3.png";
	private static final String LEVEL_4_FILE_NAME = "level4.png";
	private static final String LEVEL_5_FILE_NAME = "level5.png";
	private static final String LEVEL_6_FILE_NAME = "level6.png";
	private static final String LEVEL_7_FILE_NAME = "level7.png";
	private static final String LEVEL_8_FILE_NAME = "level8.png";
	private static final String LEVEL_9_FILE_NAME = "level9.png";
	
	private WorldGeneration() {}
	
	public static World generate(Screen screen, String... levelFileNames) {
		return enablePortals(new World(new LevelChunck(Level.froms(screen, Arrays.asList(levelFileNames)))));
	}
	
	public static World generatePredefined(Screen screen) {
		return enablePortals(new World(new LevelChunck(
				Level.froms(screen, Arrays.asList(
						LEVEL_4_FILE_NAME)))));
	}
	
	private static World enablePortals(World world, String... levelFileNames) {
		int lvlCount = world.getLevelCount();
		
		if (lvlCount > 1) {
			for (int i = 0; i < lvlCount - 1; ++i) {
				world.addNextLevelTpPoint(levelFileNames[i], levelFileNames[i + 1]);
			}
		}
		
		return world;
	}
}