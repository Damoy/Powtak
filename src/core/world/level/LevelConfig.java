package core.world.level;

import core.entities.enemies.EnemyChunck;
import core.entities.items.EnergyChunck;
import core.entities.player.PlayerConfig;
import core.entities.walls.WallChunck;
import core.world.Tile;
import core.world.items.DoorKeyEngine;
import core.world.teleportation.NextLevelTpPointSource;

public class LevelConfig {

	private String id;
	private int rows;
	private int cols;
	private Tile[][] tiles;
	private WallChunck wallChunk;
	private DoorKeyEngine doorKeyEngine;
	private PlayerConfig playerConfig;
	private NextLevelTpPointSource nextLevelTpPointSource;
	private EnemyChunck enemyChunck;
	private EnergyChunck energyChunck;
	
	public LevelConfig() {
		
	}
	
	public LevelConfig(String id, int rows, int cols, Tile[][] tiles, WallChunck wallChunk, DoorKeyEngine doorKeyEngine,
			PlayerConfig playerConfig, NextLevelTpPointSource nextLevelTpPointSource, EnemyChunck enemyChunck, EnergyChunck energyChunck) {
		this.id = id;
		this.rows = rows;
		this.cols = cols;
		this.tiles = tiles;
		this.wallChunk = wallChunk;
		this.doorKeyEngine = doorKeyEngine;
		this.playerConfig = playerConfig;
		this.nextLevelTpPointSource = nextLevelTpPointSource;
		this.enemyChunck = enemyChunck;
		this.energyChunck = energyChunck;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRows() {
		return rows;
	}

	public LevelConfig setRows(int rows) {
		this.rows = rows;
		return this;
	}

	public int getCols() {
		return cols;
	}

	public LevelConfig setCols(int cols) {
		this.cols = cols;
		return this;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public LevelConfig setTiles(Tile[][] tiles) {
		this.tiles = tiles;
		return this;
	}

	public WallChunck getWallChunk() {
		return wallChunk;
	}

	public void setWallChunk(WallChunck wallChunk) {
		this.wallChunk = wallChunk;
	}

	public DoorKeyEngine getDoorKeyEngine() {
		return doorKeyEngine;
	}

	public PlayerConfig getPlayerConfig() {
		return playerConfig;
	}

	public NextLevelTpPointSource getNextLevelTpPointSource() {
		return nextLevelTpPointSource;
	}

	public EnemyChunck getEnemyChunck() {
		return enemyChunck;
	}

	public EnergyChunck getEnergyChunck() {
		return energyChunck;
	}

	public void setEnergyChunck(EnergyChunck energyChunck) {
		this.energyChunck = energyChunck;
	}
	
}
