package core.entities.walls;

import java.util.ArrayList;
import java.util.List;

import core.world.Tile;
import rendering.Screen;
import utils.AABB;
import utils.Config;

public class WallChunck {

	private List<Wall> walls;

	public WallChunck() {
		walls = new ArrayList<>();
	}
	
	public void add(Wall wall) {
		walls.add(wall);
	}
	
	public void remove(Wall wall) {
		walls.remove(wall);
	}
	
	public Wall addNorm(Tile tile, int x, int y, Class<? extends Wall> cl) {
		try {
			Wall wall = cl.newInstance();
			wall.setX(x);
			wall.setY(y);
			wall.setWallChunck(this);
			wall.setTile(tile);
			walls.add(wall);
			return wall;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}
	
	public Wall add(Tile tile, int row, int col, Class<? extends Wall> cl) {
		return addNorm(tile, col * Config.TILE_SIZE, row * Config.TILE_SIZE, cl);
	}
	
	public void render(Screen s) {
		walls.forEach(wall -> wall.render(s));
	}
	
	public void update() {
		walls.forEach(wall -> wall.update());
	}
	
	public Wall collision(AABB box) {
		Wall collision = null;
		
		for(Wall wall : walls) {
			if(wall.getBox().collides(box)) {
				collision = wall;
				break;
			}
		}
		
		return collision;
	}
	
}
