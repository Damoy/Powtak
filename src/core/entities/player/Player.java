package core.entities.player;

import java.util.ArrayList;
import java.util.List;

import core.Core;
import core.configs.GameConfig;
import core.entities.Direction;
import core.entities.Entity;
import core.entities.projectiles.PlayerStaticProjectile;
import core.entities.projectiles.Projectile;
import core.world.Map;
import core.world.Tile;
import core.world.level.Level;
import core.world.level.ingame.InGameLevel;
import input.Keys;
import rendering.Screen;
import rendering.Texture;
import rendering.animation.AnimationFrame;
import rendering.animation.DirectedAnimation;
import rendering.animation.DirectedAnimationOnTick;
import rendering.config.TextRenderingConfig;
import sound.SoundEngine;
import utils.Utils;
import utils.TickCounter;

public class Player extends Entity {

	private Core core;
	private SoundEngine soundEngine;
	
	private Direction direction;
	
	private boolean moving;
	private boolean blocked;
	private boolean teleporting;
	private boolean moveInputReceived;
	
	// save x, y
	private int sx;
	private int sy;
	
	// last dx, dy
	private int ldx;
	private int ldy;
	
	// start x, y
	private int startX;
	private int startY;
	
	// energy
	private int energyAmount;
	
	private InGameLevel level;
	private Map map;
	
	// animation
	private DirectedAnimation animation;
	
	// shooting
	private List<Projectile> projectiles;
	
	// teleportation counter
	private TickCounter tpCounter;
	
	public Player(Core core, InGameLevel level, Texture texture) {
		super(texture);
		this.core = core;
		this.soundEngine = core.getSoundEngine();
		attributesInit();
		initUsingLevel(level);
	}
	
	private void attributesInit() {
		this.animation = generateAnimation();
		this.projectiles = new ArrayList<>();
		this.moving = false;
		this.blocked = false;
		this.teleporting = false;
		this.moveInputReceived = false;
		this.tpCounter = null;
	}
	
	private void initUsingLevel(InGameLevel level) {
		this.level = level;
		PlayerConfig config = level.getPlayerConfig();
		this.map = level.getMap();
		this.startX = config.getX();
		this.startY = config.getY();
		this.energyAmount = config.getEnergy();
		this.x = startX;
		this.y = startY;
		this.sx = 0;
		this.sy = 0;
		this.ldx = 0;
		this.ldy = 0;
		this.setDirection(config.getDirection());
		this.level.setPlayer(this);
	}
	
	public void setLevel(InGameLevel level) {
		this.moving = false;
		this.blocked = false;
		this.moveInputReceived = false;
		initUsingLevel(level);
		Keys.deactivateAll();
	}
	
	public void reset() {
		this.removeProjectiles();
		this.moving = false;
		this.blocked = false;
		this.teleporting = false;
		this.moveInputReceived = false;
		this.tpCounter = null;
		this.energyAmount = level.getPlayerConfig().getEnergy();
		this.x = startX;
		this.y = startY;
		this.sx = 0;
		this.sy = 0;
		this.ldx = 0;
		this.ldy = 0;
		this.setDirection(level.getPlayerConfig().getDirection());
		Keys.deactivateAll();
	}
	
	private void removeProjectiles() {
		if(projectiles != null)
			projectiles.clear();
	}
	
	public void die() {
		soundEngine.playDeathSound();
		level.reset();
	}
	
	private DirectedAnimationOnTick generateAnimation() {
		Texture t = getTexture();
		int ticksBounds2f = GameConfig.TILE_SIZE;
		int ticksBounds3f = ticksBounds2f;
		
		DirectedAnimationOnTick animation = new DirectedAnimationOnTick();
		animation.addDirTimeEntry(Direction.UP, ticksBounds2f);
		animation.addDirTimeEntry(Direction.DOWN, ticksBounds2f);
		animation.addDirTimeEntry(Direction.LEFT, ticksBounds3f);
		animation.addDirTimeEntry(Direction.RIGHT, ticksBounds3f);
		
		List<AnimationFrame> downAnimations = new ArrayList<>();
		downAnimations.add(new AnimationFrame(t, 0, 0, 12, 13, 1.0f));
		downAnimations.add(new AnimationFrame(t, 17, 0, 12, 13, 1.0f));
		
		List<AnimationFrame> rightAnimations = new ArrayList<>();
		rightAnimations.add(new AnimationFrame(t, 34, 0, 8, 13, 1.0f));
		rightAnimations.add(new AnimationFrame(t, 51, 0, 10, 13, 1.0f));
		rightAnimations.add(new AnimationFrame(t, 67, 0, 10, 13, 1.0f));
		
		List<AnimationFrame> upAnimations = new ArrayList<>();
		upAnimations.add(new AnimationFrame(t, 81, 0, 12, 13, 1.0f));
		upAnimations.add(new AnimationFrame(t, 97, 0, 12, 13, 1.0f));
		
		List<AnimationFrame> leftAnimations = new ArrayList<>();
		leftAnimations.add(new AnimationFrame(t, 115, 0, 8, 13, 1.0f));
		leftAnimations.add(new AnimationFrame(t, 129, 0, 10, 13, 1.0f));
		leftAnimations.add(new AnimationFrame(t, 145, 0, 10, 13, 1.0f));
		
		animation.addAnimationChunck(Direction.UP, upAnimations);
		animation.addAnimationChunck(Direction.DOWN, downAnimations);
		animation.addAnimationChunck(Direction.LEFT, leftAnimations);
		animation.addAnimationChunck(Direction.RIGHT, rightAnimations);
		
		return animation;
	}
	
	@Override
	public void update() {
		input();
		updateTpCounter();
		move();
		updateProjectiles();
	}
	
	private void updateTpCounter() {
		if(tpCounter != null) {
			tpCounter.increment();
			if(tpCounter.isStopped()) {
				teleporting = false;
			}
		}
	}
	
	private void input() {
		if(Keys.upKeyPressed()) {
			setDirection(Direction.UP);
			moveInputReceived = true;
		}
		
		if(Keys.downKeyPressed()) {
			setDirection(Direction.DOWN);
			moveInputReceived = true;
		}
		
		if(Keys.leftKeyPressed()) {
			setDirection(Direction.LEFT);
			moveInputReceived = true;
		}
		
		if(Keys.rightKeyPressed()) {
			setDirection(Direction.RIGHT);
			moveInputReceived = true;
		}
		
		if(Keys.isPressed(Keys.SPACE)) {
			energyAmount -= 2;
			if(checkEnergy()) {
				generateProjectile();
			}
			Keys.deactivate(Keys.SPACE);
		}
		
		if(Keys.isPressed(Keys.ENTER)) {
			level.reset();
		}
		
		if(Keys.isPressed(Keys.ESCAPE)) {
			core.focusMainMenuFromInGame();
		}
	}
	
	private void generateProjectile() {
		soundEngine.playHitSound();
		projectiles.add(new PlayerStaticProjectile(getLevel(), animation.getCurrentDirection(),
				getProjX(), getProjY()));
	}
	
	private int getProjX() {
		switch(animation.getCurrentDirection()) {
			case UP:
			case DOWN:
			case NULL:
				return x + (animation.getCurrentFrame().getWidth() >> 1) + 1;
			case LEFT:
				return x + 4;
			case RIGHT:
				return x + (animation.getCurrentFrame().getWidth());
		}
		
		throw new IllegalStateException();
	}
	
	private int getProjY() {
		switch(animation.getCurrentDirection()) {
			case UP:
				return y;
			case DOWN:
			case NULL:
				return y + (animation.getCurrentFrame().getHeight()) - 4;
			case LEFT:
			case RIGHT:
				return y + (animation.getCurrentFrame().getHeight() >> 1);
		}
		
		throw new IllegalStateException();
	}
	
	private void setDirection(Direction dir) {
		if(!moving) {
			direction = dir;
			animation.setDirection(direction);
		}
	}
	
	private void move() {
		if(teleporting) return;
		
		if(!moving) {
			int dx = 0;
			int dy = 0;
			int offset = GameConfig.PLAYER_SPEED;
			
			sx = x;
			sy = y;
			
			switch (direction) {
				case UP:
					dy = -offset;
					ldy = dy;
					break;
				case DOWN:
					dy = offset;
					ldy = dy;
					break;
				case LEFT:
					dx = -offset;
					ldx = dx;
					break;
				case RIGHT:
					dx = offset;
					ldx = dx;
					break;
				default:
					return;
			}
			
			blocked = !(move2(dx, 0) && move2(0, dy));
			if(!blocked) {
				--energyAmount;
				if(!checkEnergy()) {
					return;
				}
			}
		}
		else {
			// not moving
			blocked = !(move2(ldx, 0) && move2(0, ldy));
		}
		
		int ts = GameConfig.TILE_SIZE;
		int xpts = sx + ts;
		int ypts = sy + ts;
		int xmts = sx - ts;
		int ymts = sy - ts;
		
		if(x >= xpts || y >= ypts || x <= xmts || y <= ymts) {
			if(!teleporting) {
				if(x >= xpts) setX(xpts);
				if(x <= xmts) setX(xmts);
				if(y >= ypts) setY(ypts);
				if(y <= ymts) setY(ymts);
			} else {
				teleporting = false;
			}

			resetMovement();
		} else {
			if(!blocked) moving = true;
			else resetMovement();
		}
	}
	
	private boolean checkEnergy() {
		if(energyAmount <= 0) {
			die();
			return false;
		}
		return true;
	}
	
	private boolean move2(int dx, int dy) {
		if(dx != 0 && dy != 0) throw new IllegalStateException();
		
		int xr = GameConfig.TILE_SIZE - 1;
		int yr = xr;
		int sv = (int) Utils.log2(GameConfig.TILE_SIZE);
		
		int rowTo0 = y >> sv;
		int colTo0 = x >> sv;
		int rowTo1 = (y + yr) >> sv;
		int colTo1 = (x + xr) >> sv;
		
		int row0 = (y + dy) >> sv;
		int col0 = (x + dx) >> sv;
		int row1 = (y + dy + yr) >> sv;
		int col1 = (x + dx + xr) >> sv;
		
		if(row0 < 0 || col0 < 0 || row1 >= GameConfig.NUM_ROWS || col1 >= GameConfig.NUM_COLS) return false;
		
		for(int row = row0; row <= row1; ++row) {
			for(int col = col0; col <= col1; ++col) {
				if(col >= colTo0 && col <= colTo1 && row >= rowTo0 && row <= rowTo1) continue;
				if(row < 0 || row >= GameConfig.NUM_ROWS || col < 0 || col >= GameConfig.NUM_COLS) continue;
				
				Tile tile = map.getTileAt(row, col);
				
				// wall tile collision
				if(tile.isWalled()) {
					launchWallInteraction();
					return false;
				}
				
				if(level.getEnemyOn(row, col) != null) {
					launchEnemyInteraction();
					return false;
				}
				
				// door key collision
				if(level.interactIfCollisionDoorKey(tile)) {
					launchDoorKeyInteraction();
					return false;
				}
				
				// next level teleport collision
				if(tile.isNextLevelPortaled()) {
					return launchPortalInteraction(tile);
				}
				
				if(tile.isPoweredUp()) {
					launchEnergyInteraction(tile);
				}
			}
		}
		
		if(moveInputReceived) {
			addX(dx);
			addY(dy);
			animation.update();
			return true;
		}
		
		return false;
	}
	
	private void launchWallInteraction() {
		//soundEngine.playWallHitSound();
	}
	
	private void launchEnemyInteraction() {
		//soundEngine.playWallHitSound();
	}
	
	private void launchDoorKeyInteraction() {
		soundEngine.playDoorOpeningSound();
	}
	
	private boolean launchPortalInteraction(Tile tile) {
		soundEngine.playPortalSound();
		tile.getNextLevelPortal().activate(this);
		return false;
	}
	
	private void launchEnergyInteraction(Tile tile) {
		soundEngine.playPowerupSound();
		tile.getEnergy().interact(this);
	}
	
	@Override
	public void render(Screen s) {
		renderProjectiles(s);
		renderAnimation(s);
		renderEnergy(s);
	}
	
	private void renderProjectiles(Screen s) {
		projectiles.forEach(projectile -> projectile.render(s));
	}
	
	private void renderEnergy(Screen s) {
		s.renderUIText(energyAmount, TextRenderingConfig.PLAYER_ENERGY_FONT_SIZE, TextRenderingConfig.PLAYER_ENERGY_FONT_COLOR);
	}
	
	private void renderAnimation(Screen s) {
		animation.render(s, x + getFrameXoffset(animation.getCurrentFrame()),
							y + getFrameYoffset(animation.getCurrentFrame()));
	}
	
	private int getFrameXoffset(AnimationFrame frame) {
		return (GameConfig.TILE_SIZE - frame.getWidth()) >> 1;
	}
	
	private int getFrameYoffset(AnimationFrame frame) {
		return (GameConfig.TILE_SIZE - frame.getHeight()) >> 1;
	}
	
	private void updateProjectiles() {
		projectiles.forEach(projectile -> projectile.update());
		removeDeadProjectiles();
	}
	
	private void removeDeadProjectiles() {
		projectiles.removeIf(projectile -> projectile.isDead());
	}
	
	private void resetMovement() {
		moving = false;
		ldx = 0;
		ldy = 0;
		direction = Direction.NULL;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void activateTeleportation() {
		teleporting = true;
		Keys.deactivateAll();
		Keys.blockKeysWithDelay(GameConfig.UPS >> 2);
		resetMovement();
	}
	
	public void addPower(int value) {
		energyAmount += value;
	}

	@Override
	public int getWidth() {
		return animation.getCurrentFrame().getWidth();
	}

	@Override
	public int getHeight() {
		return animation.getCurrentFrame().getHeight();
	}
	
	@Override
	public Tile getTile() {
		return level.getMap().getNormTileAt(x, y);
	}
	
	@Override
	public void setX(int x) {
		this.x = x;
	}
	
	@Override
	public void setY(int y) {
		this.y = y;
	}

	public int getEnergyAmount() {
		return energyAmount;
	}

	public void setEnergyAmount(int energyAmount) {
		this.energyAmount = energyAmount;
	}

}
