package core.world.level;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
import core.world.items.Door;
import core.world.items.DoorKeyEngine;
import core.world.items.Key;
import core.world.teleportation.NextLevelTpPointSource;
import utils.Colors;
import utils.Config;
import utils.Log;
import utils.Utils;

public class LevelLoader {

	private final static LevelLoader INSTANCE = new LevelLoader();
	
	private LevelLoader() {}
	
	public static LevelLoader get() {
		return INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
	public LevelConfig load(String levelFileName) {
		int rows = Config.NUM_ROWS;
		int cols = Config.NUM_COLS;
		BufferedImage tex = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_ARGB);
		
		// tiles
		Tile[][] tiles = new Tile[rows][cols];
		
		// walls
		WallChunck wallChunk = new WallChunck();
		
		// key engine
		DoorKeyEngine doorKeyEngine = new DoorKeyEngine();
		Key[] keys = new Key[Config.DOOR_KEY_ENGINE_SIZE];
		initNullArray(keys);
		
		@SuppressWarnings("rawtypes")
		List[] doors = new List[Config.DOOR_KEY_ENGINE_SIZE];
		initNullArray(doors);
		
		// player configuration
		PlayerConfig playerConfig = new PlayerConfig();
		
		// teleportation to new level
		NextLevelTpPointSource nextLevelTpPointSource = null;
		
		// enemies
		EnemyChunck enemyChunck = new EnemyChunck();
		
		// energies
		EnergyChunck energyChunck = new EnergyChunck();
		
		try {
			tex = ImageIO.read(new FileInputStream(Utils.levelPath(levelFileName)));

			for(int col = 0; col < cols; ++col) {
				for(int row = 0; row < rows; ++row) {
					int rgb = tex.getRGB(col, row);
					int x = col * Config.TILE_SIZE;
					int y = row * Config.TILE_SIZE;
					
					if(rgb == Config.GROUND_TILE_COLOR.getRGB()) {
						tiles[row][col] = new GroundTile(x, y);
					}
					
					if(rgb == Config.STANDARD_WALL_COLOR.getRGB()) {
						tiles[row][col] = new GroundTile(x, y);
						Wall wall = wallChunk.add(tiles[row][col], row, col, StandardWall.class);
						tiles[row][col].setWall(wall);
					}
					
					if(rgb == Config.DESTRUCTIBLE_WALL_COLOR.getRGB()) {
						tiles[row][col] = new GroundTile(x, y);
						Wall wall = wallChunk.add(tiles[row][col], row, col, DestructibleWall.class);
						tiles[row][col].setWall(wall);
					}
					
					if(Colors.isSpawn(rgb)) {
						tiles[row][col] = new GroundTile(x, y);
						playerConfig.setX(x);
						playerConfig.setY(y);
						playerConfig.setEnergy(Colors.green(rgb) - 155);
						Direction playerDirection = Direction.NULL;
						switch(Colors.red(rgb)) {
						case 0:
							playerDirection = Direction.DOWN;
							break;
						case 1:
							playerDirection = Direction.LEFT;
							break;
						case 2:
							playerDirection = Direction.UP;
							break;
						case 3:
							playerDirection = Direction.RIGHT;
							break;
						default:
							throw new IllegalStateException();
						}
						
						playerConfig.setDirection(playerDirection);
					}
					
					if(rgb == Config.NEXT_LEVEL_TELEPORT_POINT_COLOR.getRGB()) {
						tiles[row][col] = new GroundTile(x, y);
						nextLevelTpPointSource = new NextLevelTpPointSource(x, y);
					}
					
					if(Colors.isZombie(rgb)) {
						tiles[row][col] = new GroundTile(x, y);
						Direction zomDir;
						
						switch(Colors.blue(rgb)) {
							case 0:
								zomDir = Direction.DOWN;
								break;
							case 1:
								zomDir = Direction.LEFT;
								break;
							case 2:
								zomDir = Direction.UP;
								break;
							case 3:
								zomDir = Direction.RIGHT;
								break;
							default:
								throw new IllegalStateException();
						}
						
						enemyChunck.add(new StaticZombie(tiles[row][col], enemyChunck, x, y, 1.0f, zomDir));
						Log.info("Zombie");
					}
					
					if(Colors.isDoor(rgb)) {
						tiles[row][col] = new GroundTile(x, y);
						int doorIdentifier = doorKeyEngine.getDoorIdentifier(rgb);
						
						@SuppressWarnings("rawtypes")
						List identifiedDoors = doors[doorIdentifier];
						
						if(identifiedDoors == null)
							identifiedDoors = new ArrayList<>();

						doors[doorIdentifier] = identifiedDoors;
						Door door = new Door(tiles[row][col], doorIdentifier, x, y);
						identifiedDoors.add(door);
						tiles[row][col].setDoor(door);
						Log.info("Door");
					}
					
					if(Colors.isKey(rgb)) {
						tiles[row][col] = new GroundTile(x, y);
						int keyIdentifier = doorKeyEngine.getKeyIdentifier(rgb);
						
						if(keys[keyIdentifier] != null)
							throw new IllegalStateException();
						
						keys[keyIdentifier] = new Key(tiles[row][col], keyIdentifier, x, y);
						tiles[row][col].setKey(keys[keyIdentifier] );
						Log.info("Key");
					}
					
					if(Colors.isEnergy(rgb)) {
						tiles[row][col] = new GroundTile(x, y);
						int energyAmount = Energy.retrievePower(rgb);
						energyChunck.add(new Energy(tiles[row][col], x, y, energyAmount, energyChunck));
						Log.info("Energy");
					}
				}
			}
			
			for(int i = 0; i < Config.DOOR_KEY_ENGINE_SIZE; ++i) {
				if(doors[i] == null) continue;
				
				Door[] doorArr = new Door[doors[i].size()];
				
				for(int j = 0; j < doorArr.length; ++j) {
					doorArr[j] = (Door) doors[i].get(j);
				}
				
				doorKeyEngine.addEntry(keys[i], doorArr);
			}
			
			return new LevelConfig(rows, cols, tiles, wallChunk, doorKeyEngine, playerConfig, nextLevelTpPointSource, enemyChunck, energyChunck);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}
	
	private <T> void initNullArray(@SuppressWarnings("unchecked") T... elements){
		for(int i = 0; i < elements.length; ++i)
			elements[i] = null;
	}
	
}
