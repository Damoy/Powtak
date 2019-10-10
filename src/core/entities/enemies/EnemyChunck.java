package core.entities.enemies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.world.level.Level;
import rendering.Screen;
import utils.AABB;

public class EnemyChunck {
	
	private List<Enemy> enemies;
	
	public EnemyChunck() {
		enemies = new ArrayList<>();
	}
	
	public EnemyChunck(List<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	public EnemyChunck(Enemy... enemies) {
		this.enemies = Arrays.asList(enemies);
	}
	
	public void render(Screen s) {
		enemies.forEach(enemy -> enemy.render(s));
	}
	
	public void update() {
		enemies.forEach(enemy -> enemy.update());
	}
	
	public Enemy collision(AABB box) {
		Enemy collision = null;
		
		for(Enemy enemy : enemies) {
			if(enemy.getBox().collides(box)) {
				collision = enemy;
				break;
			}
		}
		
		return collision;
	}
	
	public Enemy get(int row, int col) {
		for(Enemy enemy : enemies) {
			if(enemy.getRow() == row && enemy.getCol() == col)
				return enemy;
		}
		
		return null;
	}
	
	public void setEnemiesLevel(Level level) {
		enemies.forEach(enemy -> enemy.setLevel(level, false));
	}
	
	public void add(Enemy enemy) {
		enemies.add(enemy);
	}
	
	public void remove(Enemy enemy) {
		enemies.remove(enemy);
	}
	
}
