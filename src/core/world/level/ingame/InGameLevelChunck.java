package core.world.level.ingame;

import java.util.List;

import core.Core;
import core.entities.player.Player;
import core.world.level.Level;
import core.world.level.LevelChunck;
import core.world.level.loading.LevelLoader;
import core.world.teleportation.Portal;
import core.world.teleportation.PortalSourcePoint;
import rendering.Screen;
import utils.Utils;
import utils.exceptions.PowtakException;

public class InGameLevelChunck extends LevelChunck {

	private Player player;
	
	public InGameLevelChunck(Core core, List<Level> inLevels) throws PowtakException {
		super(core, inLevels);
		this.player = new Player(core, (InGameLevel) this.levels.get(0), core.getCurrentCharacterSkin());
	}
	
	public void update() throws PowtakException {
		super.update();
		player.update();
	}
	
	@Override
	public void render(Screen s) {
		super.render(s);
		player.render(s);
	}

	@Override
	public void reload(Screen s, String levelId, PortalSourcePoint nextLevelPortalSourcePoint, Portal nextLevelPortal)
			throws PowtakException {
		LevelLoader levelLoader = LevelLoader.get();
		String levelFilePath = Utils.levelPath(levelId);
		InGameLevel reloadedLevel = InGameLevel.from(s, levelLoader.loadCustomLevel(levelFilePath));
		reloadedLevel.setLevelChunck(this);
		reloadedLevel.setNextLevelPortalSourcePoint(nextLevelPortalSourcePoint);
		reloadedLevel.setNextLevelPortal(nextLevelPortal);
		levels.set(getLevelIndexUsingId(levelId), reloadedLevel);
		player.setLevel(reloadedLevel);
	}

	public Player getPlayer() {
		return player;
	}
	
}
