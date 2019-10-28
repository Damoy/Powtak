package core.world.level;

import java.util.List;

import core.Core;
import core.world.teleportation.Portal;
import core.world.teleportation.PortalSourcePoint;
import rendering.Screen;
import utils.exceptions.PowtakException;

public abstract class LevelChunck {

	protected Core core;
	protected List<Level> levels;
	protected int currentLevelIndex;
	
	public LevelChunck(Core core, List<Level> inLevels) {
		this.core = core;
		this.currentLevelIndex = 0;
		this.levels = inLevels;
		this.levels.forEach(lvl -> lvl.setLevelChunck(this));
	}
	
	public abstract void reload(Screen s, String levelId, PortalSourcePoint nextLevelPortalSourcePoint, Portal nextLevelPortal) throws PowtakException;
	
	protected int getLevelIndexUsingId(String levelId) throws PowtakException {
		for(int i = 0; i < levels.size(); ++i) {
			if(levels.get(i).getId().equals(levelId)) {
				return i;
			}
		}
		throw new PowtakException("Unknown level identified by \"" + levelId + "\"" + levelId) {
			private static final long serialVersionUID = -7622403420527172374L;};
	}
	
	public void update() throws PowtakException {
		getCurrent().update();
	}
	
	public void render(Screen s) {
		getCurrent().render();
	}
	
	public Level getCurrent() {
		return levels.get(currentLevelIndex);
	}
	
	public Level get(String levelId) throws PowtakException {
		return levels.get(getLevelIndexUsingId(levelId));
	}
	
	public void grow() {
		++currentLevelIndex;
	}
	
	public void decrease() {
		--currentLevelIndex;
	}
	
	public int count() {
		return levels.size();
	}

	public List<Level> getLevels() {
		return levels;
	}

	public int getCurrentLevelIndex() {
		return currentLevelIndex;
	}
	
}
