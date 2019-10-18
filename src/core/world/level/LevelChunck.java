package core.world.level;

import java.util.List;

import core.entities.player.Player;
import core.world.teleportation.Portal;
import core.world.teleportation.PortalSourcePoint;
import rendering.Screen;
import utils.exceptions.PowtakException;

public class LevelChunck {

	private List<Level> levels;
	private int currentLevelIndex;
	private Player player;
	
	public LevelChunck(List<Level> inLevels) {
		this.currentLevelIndex = 0;
		this.levels = inLevels;
		this.levels.forEach(lvl -> lvl.setLevelChunck(this));
		this.player = new Player(this.levels.get(0));
	}
	
	public void reload(Screen s, String levelId, PortalSourcePoint nextLevelPortalSourcePoint, Portal nextLevelPortal) throws PowtakException {
		Level reloadedLevel = Level.from(s, LevelLoader.get().loadCustomLevel(levelId));
		reloadedLevel.setLevelChunck(this);
		reloadedLevel.setNextLevelPortalSourcePoint(nextLevelPortalSourcePoint);
		reloadedLevel.setNextLevelPortal(nextLevelPortal);
		levels.set(getLevelIndexUsingId(levelId), reloadedLevel);
		player.setLevel(reloadedLevel);
	}
	
	private int getLevelIndexUsingId(String levelId) throws PowtakException {
		for(int i = 0; i < levels.size(); ++i) {
			if(levels.get(i).getId().equals(levelId)) {
				return i;
			}
		}
		throw new PowtakException("Unknown level identified by \"" + levelId + "\"" + levelId) {
			private static final long serialVersionUID = -7622403420527172374L;};
	}
	
	public void render(Screen s) {
		getCurrent().render();
		player.render(s);
	}
	
	public void update() throws PowtakException {
		getCurrent().update();
		player.update();
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
	
	public List<Level> getLevels(){
		return levels;
	}
}
