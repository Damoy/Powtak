package core.world;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.Core;
import core.configs.GameConfig;
import core.world.level.Level;
import core.world.level.ingame.InGameLevel;
import core.world.level.ingame.InGameLevelChunck;
import core.world.teleportation.PortalSetup;
import rendering.Screen;
import utils.Utils;
import utils.exceptions.PowtakException;

public final class WorldGeneration {
	
	private WorldGeneration() {}
	
	public static World generateWorldFromResourcesLevels(Core core, Screen screen) throws PowtakException {
		List<File> levelFiles = Utils.loadFilesIgnoreDirs(GameConfig.CUSTOM_LEVELS_FILE_PATH);
		return generateWorldFromLevelFileNames(core, screen, levelFiles);
	}
	
	public static World generateWorldFromPredefinedLevels(Core core, Screen screen) throws PowtakException {
		List<File> levelFiles = new ArrayList<>();
		levelFiles.add(new File(Utils.levelPath("level3.lvl")));
		levelFiles.add(new File(Utils.levelPath("level4.lvl")));
		return generateWorldFromLevelFileNames(core, screen, levelFiles);
	}
	
	private static World generateWorldFromLevelFileNames(Core core, Screen screen, List<File> levelFiles) throws PowtakException {
		List<Level> levels = InGameLevel.from(screen, levelFiles);
		InGameLevelChunck levelChunck = new InGameLevelChunck(core, levels);
		PortalSetup.enableNextLevelPortals(levelChunck);
		return new World(levelChunck);
	}

}