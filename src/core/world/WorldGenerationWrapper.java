package core.world;

import java.util.Arrays;

import core.world.level.Level;
import core.world.level.LevelChunck;
import rendering.Screen;

public final class WorldGenerationWrapper {
	
	private static final String LEVEL_1_FILE_NAME = "level1.png";
	private static final String LEVEL_2_FILE_NAME = "level2.png";
	private static final String LEVEL_3_FILE_NAME = "level3.png";
	
	private WorldGenerationWrapper() {}
	
	public static World generate(Screen screen, String... levelFileNames) {
		return new World(LevelChunck.of(Level.froms(screen, Arrays.asList(levelFileNames))));
	}
	
	public static World generatePredefined(Screen screen) {
		return enableBasicWorldTeleportation(new World(LevelChunck.of(
				Level.froms(screen, Arrays.asList(LEVEL_1_FILE_NAME, LEVEL_2_FILE_NAME, LEVEL_3_FILE_NAME)))));
				// Level.froms(screen,Factory.iList(LEVEL_2_FILE_NAME, LEVEL_1_FILE_NAME, LEVEL_3_FILE_NAME)))));
	}
	
	private static World enableBasicWorldTeleportation(World world) {
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
