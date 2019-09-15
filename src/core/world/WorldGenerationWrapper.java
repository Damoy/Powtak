package core.world;

import java.util.Arrays;

import core.world.level.Level;
import core.world.level.LevelChunck;
import rendering.Screen;

public final class WorldGenerationWrapper {
	
	private static final String LEVEL_1_FILE_NAME = "level1.png";
	private static final String LEVEL_2_FILE_NAME = "level2.png";
	private static final String LEVEL_3_FILE_NAME = "level3.png";
	private static final String LEVEL_4_FILE_NAME = "level4.png";
	private static final String LEVEL_5_FILE_NAME = "level5.png";
	private static final String LEVEL_6_FILE_NAME = "level6.png";
	private static final String LEVEL_7_FILE_NAME = "level7.png";
	private static final String LEVEL_8_FILE_NAME = "level8.png";
	private static final String LEVEL_9_FILE_NAME = "level9.png";
	
	private WorldGenerationWrapper() {}
	
	public static World generate(Screen screen, String... levelFileNames) {
		return enablePortals(new World(new LevelChunck(Level.froms(screen, Arrays.asList(levelFileNames)))));
	}
	
	public static World generatePredefined(Screen screen) {
		return enablePortals(new World(new LevelChunck(
				Level.froms(screen, Arrays.asList(LEVEL_2_FILE_NAME, LEVEL_1_FILE_NAME, LEVEL_3_FILE_NAME)))));
	}
	
	private static World enablePortals(World world) {
		int lvlCount = world.getLevelCount();
		
		if(lvlCount > 1) {
			if(lvlCount == 2) {
				world.addNextLevelTpPoint(0, 1);
			} else {
				for(int i = 0; i < lvlCount - 1; ++i) {
					world.addNextLevelTpPoint(i, i + 1);
				}
			}
		}
		
		return world;
	}
}