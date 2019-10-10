package core.world.level;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.entities.Direction;
import core.entities.enemies.EnemyChunck;
import core.entities.enemies.StaticZombie;
import core.entities.items.Energy;
import core.entities.items.EnergyChunck;
import core.entities.player.PlayerConfig;
import core.entities.walls.StandardWall;
import core.entities.walls.WallChunck;
import core.world.GroundTile;
import core.world.Tile;
import core.world.items.DoorKeyEngine;
import core.world.teleportation.NextLevelTpPointSource;
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
	
	public LevelConfig loadCustomLevel(String levelFileName) throws PowtakException {
		String levelFilePath = "./resources/levels/custom/" + levelFileName + ".lvl";
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
		for(String levelFileLine : levelFileLines) {
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
		colStartMatcher.find();
		colEndMatcher.find();
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
			wallChunck.add(tile, row, col, StandardWall.class);
		}
	}
	
	private void handleWallsColLoad(LevelConfig levelConfig, WallChunck wallChunck,
			Matcher colMatcher, String levelFileName, String levelFileLine) throws PowtakException {
		Matcher rowStartMatcher = rowStartPattern.matcher(levelFileLine);
		Matcher rowEndMatcher = rowEndPattern.matcher(levelFileLine);
		rowStartMatcher.find();
		rowEndMatcher.find();
		int col;
		int rowStart;
		int rowEnd;
		
		try {
			col = Integer.parseInt(colMatcher.group().split("=")[1]);
			rowStart = Integer.parseInt(rowStartMatcher.group().split("=")[1]);
			rowEnd = Integer.parseInt(rowEndMatcher.group().split("=")[1]);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalLevelFileException(levelFileName);
		}
		
		for(int row = rowStart; row <= rowEnd; ++row) {
			Tile tile = levelConfig.getTiles()[row][col];
			wallChunck.add(tile, row, col, StandardWall.class);
		}
	}
	
	private void handleWallLoad(LevelConfig levelConfig, String levelFileName, String levelFileLine) throws PowtakException {
		Log.info("Level loading: wall.");
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
		
		Tile tile = levelConfig.getTiles()[row][col];
		levelConfig.getWallChunk().add(tile, row, col, StandardWall.class);
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
		levelConfig.setNextLevelTpPointSource(new NextLevelTpPointSource(x, y));
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
	
//	public LevelConfig load(String levelFileName) {
//		int rows = Config.NUM_ROWS;
//		int cols = Config.NUM_COLS;
//		BufferedImage tex = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_ARGB);
//		
//		// tiles
//		Tile[][] tiles = new Tile[rows][cols];
//		
//		// walls
//		WallChunck wallChunk = new WallChunck();
//		
//		// key engine
//		DoorKeyEngine doorKeyEngine = new DoorKeyEngine();
//		
//		// player configuration
//		PlayerConfig playerConfig = new PlayerConfig();
//		
//		// teleportation to new level
//		NextLevelTpPointSource nextLevelTpPointSource = null;
//		
//		// enemies
//		EnemyChunck enemyChunck = new EnemyChunck();
//		
//		// energies
//		EnergyChunck energyChunck = new EnergyChunck();
//		
//		try {
//			tex = ImageIO.read(new FileInputStream(Utils.levelPath(levelFileName)));
//
//			for(int col = 0; col < cols; ++col) {
//				for(int row = 0; row < rows; ++row) {
//					int rgb = tex.getRGB(col, row);
//					int x = col * Config.TILE_SIZE;
//					int y = row * Config.TILE_SIZE;
//					
//					if(rgb == Config.GROUND_TILE_COLOR.getRGB()) {
//						tiles[row][col] = new GroundTile(x, y);
//					}
//					
//					if(rgb == Config.STANDARD_WALL_COLOR.getRGB()) {
//						tiles[row][col] = new GroundTile(x, y);
//						Wall wall = wallChunk.add(tiles[row][col], row, col, StandardWall.class);
//						tiles[row][col].setWall(wall);
//					}
//					
//					if(rgb == Config.DESTRUCTIBLE_WALL_COLOR.getRGB()) {
//						tiles[row][col] = new GroundTile(x, y);
//						Wall wall = wallChunk.add(tiles[row][col], row, col, DestructibleWall.class);
//						tiles[row][col].setWall(wall);
//					}
//					
//					if(rgb == Config.NEXT_LEVEL_TELEPORT_POINT_COLOR.getRGB()) {
//						tiles[row][col] = new GroundTile(x, y);
//						nextLevelTpPointSource = new NextLevelTpPointSource(x, y);
//						Log.info("Portal");
//					}
//					
//					if(Colors.isSpawn(rgb)) {
//						tiles[row][col] = new GroundTile(x, y);
//						playerConfig.setX(x);
//						playerConfig.setY(y);
//						playerConfig.setEnergy(Colors.green(rgb) - 155);
//						Direction playerDirection = Direction.NULL;
//						switch(Colors.red(rgb)) {
//						case 0:
//							playerDirection = Direction.DOWN;
//							break;
//						case 1:
//							playerDirection = Direction.LEFT;
//							break;
//						case 2:
//							playerDirection = Direction.UP;
//							break;
//						case 3:
//							playerDirection = Direction.RIGHT;
//							break;
//						default:
//							throw new IllegalStateException();
//						}
//						
//						playerConfig.setDirection(playerDirection);
//					}
//					
//					if(Colors.isZombie(rgb)) {
//						tiles[row][col] = new GroundTile(x, y);
//						Direction zomDir;
//						
//						switch(Colors.blue(rgb)) {
//							case 0:
//								zomDir = Direction.DOWN;
//								break;
//							case 1:
//								zomDir = Direction.LEFT;
//								break;
//							case 2:
//								zomDir = Direction.UP;
//								break;
//							case 3:
//								zomDir = Direction.RIGHT;
//								break;
//							default:
//								throw new IllegalStateException();
//						}
//						
//						enemyChunck.add(new StaticZombie(tiles[row][col], enemyChunck, x, y, 1.0f, zomDir));
//						Log.info("Zombie");
//					}
//					
//					if(Colors.isDoor(rgb)) {
//						tiles[row][col] = new GroundTile(x, y);
//						doorKeyEngine.addDoor(tiles[row][col], Colors.green(rgb));
//						Log.info("Door");
//					}
//					
//					if(Colors.isKey(rgb)) {
//						tiles[row][col] = new GroundTile(x, y);
//						doorKeyEngine.addKey(tiles[row][col], Colors.blue(rgb));
//						Log.info("Key");
//					}
//					
//					if(Colors.isEnergy(rgb)) {
//						tiles[row][col] = new GroundTile(x, y);
//						int energyAmount = Energy.retrievePower(rgb);
//						energyChunck.add(new Energy(tiles[row][col], x, y, energyAmount, energyChunck));
//						Log.info("Energy");
//					}
//				}
//			}
//			
//			doorKeyEngine.setReady();
//			return new LevelConfig(levelFileName, rows, cols, tiles, wallChunk, doorKeyEngine, playerConfig, nextLevelTpPointSource, enemyChunck, energyChunck);
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new IllegalStateException();
//		}
//	}
	
}
