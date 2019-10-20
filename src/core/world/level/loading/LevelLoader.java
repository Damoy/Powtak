package core.world.level.loading;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import core.entities.Direction;
import core.entities.enemies.EnemyChunck;
import core.entities.enemies.StaticZombie;
import core.entities.items.Energy;
import core.entities.items.EnergyChunck;
import core.entities.player.PlayerConfig;
import core.entities.walls.DestructibleWall;
import core.entities.walls.StandardWall;
import core.entities.walls.Wall;
import core.entities.walls.WallChunck;
import core.world.GroundTile;
import core.world.Tile;
import core.world.items.DoorKeyEngine;
import core.world.teleportation.PortalSourcePoint;
import utils.Config;
import utils.Log;
import utils.Utils;
import utils.exceptions.IllegalLevelFileException;
import utils.exceptions.PowtakException;

public class LevelLoader {

	private final static LevelLoader INSTANCE = new LevelLoader();
	
	private LevelLoader() {}
	
	public static LevelLoader get() {
		return INSTANCE;
	}
	
	public LevelConfig loadCustomLevel(String levelFilePath) throws PowtakException {
		return loadCustomLevel(new File(levelFilePath));
	}
	
	public LevelConfig loadCustomLevel(File file) throws PowtakException {
		String levelFilePath = file.getAbsolutePath();
		String levelFileName = file.getName();
		Log.info("Loading \"" + levelFileName + "\" .");
		
		int rows = Config.NUM_ROWS;
		int cols = Config.NUM_COLS;

		// tiles
		Tile[][] tiles = new Tile[rows][cols];
		for(int row = 0; row < rows; ++row) {
			for(int col = 0; col < cols; ++col) {
				int x = col * Config.TILE_SIZE;
				int y = row * Config.TILE_SIZE;
				tiles[row][col] = new GroundTile(x, y);
			}
		}
		
		// level
		WallChunck wallChunk = new WallChunck();
		DoorKeyEngine doorKeyEngine = new DoorKeyEngine();
		PlayerConfig playerConfig = new PlayerConfig();
		EnemyChunck enemyChunck = new EnemyChunck();
		EnergyChunck energyChunck = new EnergyChunck();
		
		List<String> fileLines = Utils.getFileLines(levelFilePath);
		if(fileLines == null || fileLines.isEmpty()) {
			throw new IllegalLevelFileException(levelFilePath);
		}
		
		LevelConfig levelConfig = new LevelConfig();
		levelConfig.setId(levelFileName);
		levelConfig.setRows(rows);
		levelConfig.setCols(cols);
		levelConfig.setTiles(tiles);
		levelConfig.setEnergyChunck(energyChunck);
		levelConfig.setWallChunk(wallChunk);
		levelConfig.setDoorKeyEngine(doorKeyEngine);
		levelConfig.setEnemyChunck(enemyChunck);
		levelConfig.setPlayerConfig(playerConfig);
		
		loadCustomLevel(levelConfig, levelFileName, fileLines);
		return levelConfig;
	}
	
	private void loadCustomLevel(LevelConfig levelConfig, String levelFileName, List<String> levelFileLines) throws PowtakException {
		for(String levelFileLine : levelFileLines.stream().filter(lfl -> !lfl.isBlank()).collect(Collectors.toList())) {
			loadCustomLevelLine(levelConfig, levelFileName, levelFileLine);
		}
	}
	
	private enum LevelLineRequestType {
		SPAWN, WALL, WALLS, ZOMBIE,
		DOOR, KEY, ENERGY, PORTAL
	}
	
	private void loadCustomLevelLine(LevelConfig levelConfig, String levelFileName, String levelFileLine) throws PowtakException {
		String requestType = levelFileLine.split(":")[0];
		LevelLineRequestType levelLineRequestType = LevelLineRequestType.valueOf(requestType.toUpperCase());
		switch(levelLineRequestType) {
		case SPAWN:
			handleSpawnLoad(levelConfig, levelFileName, levelFileLine);
			break;
		case WALLS:
			handleWallsLoad(levelConfig, levelFileName, levelFileLine);
			break;
		case WALL:
			handleWallLoad(levelConfig, levelFileName, levelFileLine);
			break;
		case ZOMBIE:
			handleZombieLoad(levelConfig, levelFileName, levelFileLine);
			break;
		case PORTAL:
			handlePortalLoad(levelConfig, levelFileName, levelFileLine);
			break;
		case DOOR:
			handleDoorLoad(levelConfig, levelFileName, levelFileLine);
			break;
		case KEY:
			handleKeyLoad(levelConfig, levelFileName, levelFileLine);
			break;
		case ENERGY:
			handleEnergyLoad(levelConfig, levelFileName, levelFileLine);
			break;
		default:
			throw new IllegalLevelFileException(levelFileName);
		}
	}
	
	private Pattern rowPattern = Pattern.compile("row=\\d+");
	private Pattern colPattern = Pattern.compile("col=\\d+");
	private Pattern rowStartPattern = Pattern.compile("rowStart=\\d+");
	private Pattern colStartPattern = Pattern.compile("colStart=\\d+");
	private Pattern rowEndPattern = Pattern.compile("rowEnd=\\d+");
	private Pattern colEndPattern = Pattern.compile("colEnd=\\d+");
	private Pattern energyPattern = Pattern.compile("energy=\\d+");
	private Pattern directionPattern = Pattern.compile("direction=[a-zA-Z]+");
	private Pattern exceptIntegerPattern = Pattern.compile("except=(\\d|,)+");
	private Pattern exceptStringPattern = Pattern.compile("except=([a-zA-Z]|,)+");
	private Pattern destructiblePattern = Pattern.compile("destructible=(true|false)");
	private Pattern idPattern = Pattern.compile("id=\\d+");
	private Pattern powerPattern = Pattern.compile("power=\\d+");
	
	private void handleSpawnLoad(LevelConfig levelConfig, String levelFileName, String levelFileLine) throws PowtakException {
		Log.info("Level loading: spawn.");
		PlayerConfig playerConfig = levelConfig.getPlayerConfig();
		Matcher rowMatcher = rowPattern.matcher(levelFileLine);
		Matcher colMatcher = colPattern.matcher(levelFileLine);
		Matcher energyMatcher = energyPattern.matcher(levelFileLine);
		Matcher directionMatcher = directionPattern.matcher(levelFileLine);
		
		Direction direction;
		int row;
		int col;
		int energy;
		
		try {
			directionMatcher.find();
			rowMatcher.find();
			colMatcher.find();
			energyMatcher.find();
			
			direction = Direction.valueOf(directionMatcher.group().split("=")[1].toUpperCase());
			row = Integer.parseInt(rowMatcher.group().split("=")[1]);
			col = Integer.parseInt(colMatcher.group().split("=")[1]);
			energy = Integer.parseInt(energyMatcher.group().split("=")[1]);
		} catch(Exception e) {
			e.printStackTrace();
			throw new IllegalLevelFileException(levelFileName);
		}
		
		playerConfig.setDirection(direction);
		playerConfig.setEnergy(energy);
		playerConfig.setX(col * Config.TILE_SIZE);
		playerConfig.setY(row * Config.TILE_SIZE);
	}
	
	private void handleWallsLoad(LevelConfig levelConfig, String levelFileName, String levelFileLine) throws PowtakException {
		Log.info("Level loading: walls.");
		WallChunck wallChunck = levelConfig.getWallChunk();
		Matcher rowMatcher = rowPattern.matcher(levelFileLine);
		Matcher colMatcher = colPattern.matcher(levelFileLine);
		boolean rowMatching = rowMatcher.find();
		boolean colMatching = colMatcher.find();
		
		if(rowMatching) {
			handleWallsRowLoad(levelConfig, wallChunck, rowMatcher, levelFileName, levelFileLine);
		} else if(colMatching){
			handleWallsColLoad(levelConfig, wallChunck, colMatcher, levelFileName, levelFileLine);
		} else {
			throw new IllegalLevelFileException(levelFileName);
		}
	}
	
	private void handleWallsRowLoad(LevelConfig levelConfig, WallChunck wallChunck,
			Matcher rowMatcher, String levelFileName, String levelFileLine) throws PowtakException {
		Matcher colStartMatcher = colStartPattern.matcher(levelFileLine);
		Matcher colEndMatcher = colEndPattern.matcher(levelFileLine);
		Matcher exceptMatcher = exceptIntegerPattern.matcher(levelFileLine);
		Matcher destructibleMatcher = destructiblePattern.matcher(levelFileLine);
		
		colStartMatcher.find();
		colEndMatcher.find();
		boolean destructible = destructibleMatcher.find();
		
		int row;
		int colStart;
		int colEnd;
		List<Integer> exceptions = null;
		
		try {
			row = Integer.parseInt(rowMatcher.group().split("=")[1]);
			colStart = Integer.parseInt(colStartMatcher.group().split("=")[1]);
			colEnd = Integer.parseInt(colEndMatcher.group().split("=")[1]);
			
			if(exceptMatcher.find()) {
				exceptions = new ArrayList<>();
				String[] tokens = exceptMatcher.group().split("=")[1].split(",");
				for(String token : tokens) {
					exceptions.add(Integer.parseInt(token));
				}
			}
			
			if(destructible) {
				destructible = Boolean.parseBoolean(destructibleMatcher.group().split("=")[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalLevelFileException(levelFileName);
		}
		
		for(int col = colStart; col <= colEnd; ++col) {
			if(exceptions != null && exceptions.contains(col)) {
				Log.info("Ignoring at row=" + row + ", col=" + col + ".");
				continue;
			}
			
			Tile tile = levelConfig.getTiles()[row][col];
			if(!tile.isWalled()) {
				Class<? extends Wall> wallClazz = destructible ? DestructibleWall.class : StandardWall.class;
				wallChunck.add(tile, row, col, wallClazz);
			} else {
				Log.warn("Wall already found at row=" + row + ", col=" + col + ".");
			}
		}
	}
	
	private void handleWallsColLoad(LevelConfig levelConfig, WallChunck wallChunck,
			Matcher colMatcher, String levelFileName, String levelFileLine) throws PowtakException {
		Matcher rowStartMatcher = rowStartPattern.matcher(levelFileLine);
		Matcher rowEndMatcher = rowEndPattern.matcher(levelFileLine);
		Matcher destructibleMatcher = destructiblePattern.matcher(levelFileLine);
		rowStartMatcher.find();
		rowEndMatcher.find();
		boolean destructible = destructibleMatcher.find();
		
		int col;
		int rowStart;
		int rowEnd;
		
		try {
			col = Integer.parseInt(colMatcher.group().split("=")[1]);
			rowStart = Integer.parseInt(rowStartMatcher.group().split("=")[1]);
			rowEnd = Integer.parseInt(rowEndMatcher.group().split("=")[1]);
			
			if(destructible) {
				destructible = Boolean.parseBoolean(destructibleMatcher.group().split("=")[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalLevelFileException(levelFileName);
		}
		
		for(int row = rowStart; row <= rowEnd; ++row) {
			Tile tile = levelConfig.getTiles()[row][col];
			Class<? extends Wall> wallClazz = destructible ? DestructibleWall.class : StandardWall.class;
			wallChunck.add(tile, row, col, wallClazz);
		}
	}
	
	private void handleWallLoad(LevelConfig levelConfig, String levelFileName, String levelFileLine) throws PowtakException {
		Log.info("Level loading: wall.");
		Matcher rowMatcher = rowPattern.matcher(levelFileLine);
		Matcher colMatcher = colPattern.matcher(levelFileLine);
		Matcher destructibleMatcher = destructiblePattern.matcher(levelFileLine);
		rowMatcher.find();
		colMatcher.find();
		boolean destructible = destructibleMatcher.find();
		
		int row;
		int col;
		
		try {
			row = Integer.parseInt(rowMatcher.group().split("=")[1]);
			col = Integer.parseInt(colMatcher.group().split("=")[1]);
			if(destructible) {
				destructible = Boolean.parseBoolean(destructibleMatcher.group().split("=")[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalLevelFileException(levelFileName);
		}
		
		Tile tile = levelConfig.getTiles()[row][col];
		Class<? extends Wall> wallClazz = destructible ? DestructibleWall.class : StandardWall.class;
		levelConfig.getWallChunk().add(tile, row, col, wallClazz);
	}
	
	private void handleZombieLoad(LevelConfig levelConfig, String levelFileName, String levelFileLine) throws PowtakException {
		Log.info("Level loading: zombie.");
		Matcher rowMatcher = rowPattern.matcher(levelFileLine);
		Matcher colMatcher = colPattern.matcher(levelFileLine);
		Matcher directionMatcher = directionPattern.matcher(levelFileLine);
		
		rowMatcher.find();
		colMatcher.find();
		directionMatcher.find();
		
		int row;
		int col;
		Direction direction;
		
		try {
			row = Integer.parseInt(rowMatcher.group().split("=")[1]);
			col = Integer.parseInt(colMatcher.group().split("=")[1]);
			direction = Direction.valueOf(directionMatcher.group().split("=")[1].toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalLevelFileException(levelFileName);
		}
		
		Tile tile = levelConfig.getTiles()[row][col];
		EnemyChunck enemyChunck = levelConfig.getEnemyChunck();
		int x = col * Config.TILE_SIZE;
		int y = row * Config.TILE_SIZE; 
		enemyChunck.add(new StaticZombie(tile, enemyChunck, x, y, 1.0f, direction));
	}
	
	private void handlePortalLoad(LevelConfig levelConfig, String levelFileName, String levelFileLine) throws PowtakException {
		Log.info("Level loading: portal.");
		Matcher rowMatcher = rowPattern.matcher(levelFileLine);
		Matcher colMatcher = colPattern.matcher(levelFileLine);
		rowMatcher.find();
		colMatcher.find();
		int row;
		int col;
		
		try {
			row = Integer.parseInt(rowMatcher.group().split("=")[1]);
			col = Integer.parseInt(colMatcher.group().split("=")[1]);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalLevelFileException(levelFileName);
		}
		
		int x = col * Config.TILE_SIZE;
		int y = row * Config.TILE_SIZE; 
		levelConfig.setNextLevelPortalSourcePoint(new PortalSourcePoint(x, y));
	}
	
	private void handleDoorLoad(LevelConfig levelConfig, String levelFileName, String levelFileLine) throws PowtakException {
		Log.info("Level loading: door.");
		Matcher rowMatcher = rowPattern.matcher(levelFileLine);
		Matcher colMatcher = colPattern.matcher(levelFileLine);
		Matcher idMatcher = idPattern.matcher(levelFileLine);
		rowMatcher.find();
		colMatcher.find();
		idMatcher.find();
		
		int row;
		int col;
		int id;
		
		try {
			row = Integer.parseInt(rowMatcher.group().split("=")[1]);
			col = Integer.parseInt(colMatcher.group().split("=")[1]);
			id = Integer.parseInt(idMatcher.group().split("=")[1]);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalLevelFileException(levelFileName);
		}
		
		Tile tile = levelConfig.getTiles()[row][col];
		levelConfig.getDoorKeyEngine().addDoor(tile, id);
	}
	
	private void handleKeyLoad(LevelConfig levelConfig, String levelFileName, String levelFileLine) throws PowtakException {
		Log.info("Level loading: key.");
		Matcher rowMatcher = rowPattern.matcher(levelFileLine);
		Matcher colMatcher = colPattern.matcher(levelFileLine);
		Matcher idMatcher = idPattern.matcher(levelFileLine);
		rowMatcher.find();
		colMatcher.find();
		idMatcher.find();
		
		int row;
		int col;
		int id;
		
		try {
			row = Integer.parseInt(rowMatcher.group().split("=")[1]);
			col = Integer.parseInt(colMatcher.group().split("=")[1]);
			id = Integer.parseInt(idMatcher.group().split("=")[1]);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalLevelFileException(levelFileName);
		}
		
		Tile tile = levelConfig.getTiles()[row][col];
		levelConfig.getDoorKeyEngine().addKey(tile, id);
	}
	
	private void handleEnergyLoad(LevelConfig levelConfig, String levelFileName, String levelFileLine) throws PowtakException {
		Log.info("Level loading: energy.");
		Matcher rowMatcher = rowPattern.matcher(levelFileLine);
		Matcher colMatcher = colPattern.matcher(levelFileLine);
		Matcher powerMatcher = powerPattern.matcher(levelFileLine);
		rowMatcher.find();
		colMatcher.find();
		powerMatcher.find();
		
		int row;
		int col;
		int power;
		
		try {
			row = Integer.parseInt(rowMatcher.group().split("=")[1]);
			col = Integer.parseInt(colMatcher.group().split("=")[1]);
			power = Integer.parseInt(powerMatcher.group().split("=")[1]);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalLevelFileException(levelFileName);
		}
		
		EnergyChunck energyChunck = levelConfig.getEnergyChunck();
		Tile tile = levelConfig.getTiles()[row][col];
		int x = col * Config.TILE_SIZE;
		int y = row * Config.TILE_SIZE; 
		levelConfig.getEnergyChunck().add(new Energy(tile, x, y, power, energyChunck));
	}
	
}
