package core.world;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.world.level.Level;
import core.world.level.LevelChunck;
import core.world.teleportation.PortalSetup;
import rendering.Screen;
import utils.Config;
import utils.Utils;
import utils.exceptions.PowtakException;

public final class WorldGeneration {
	
	private WorldGeneration() {}
	
	public static World generateWorldFromResourcesLevels(Screen screen) throws PowtakException {
		List<File> levelFiles = Utils.loadFilesIgnoreDirs(Config.CUSTOM_LEVELS_FILE_PATH);
		return generateWorldFromLevelFileNames(screen, levelFiles);
	}
	
	public static World generateWorldFromPredefinedLevels(Screen screen) throws PowtakException {
		List<File> levelFiles = new ArrayList<>();
		levelFiles.add(new File(Config.CUSTOM_LEVELS_FILE_PATH + "level3.lvl"));
//		levelFiles.add(new File(Config.CUSTOM_LEVELS_FILE_PATH + "level2.lvl"));
		levelFiles.add(new File(Config.CUSTOM_LEVELS_FILE_PATH + "level4.lvl"));
		return generateWorldFromLevelFileNames(screen, levelFiles);
	}
	
	private static World generateWorldFromLevelFileNames(Screen screen, List<File> levelFiles) throws PowtakException {
		List<Level> levels = Level.from(screen, levelFiles);
		LevelChunck levelChunck = new LevelChunck(levels);
		PortalSetup.enableNextLevelPortals(levelChunck);
		return new World(levelChunck);
	}

}