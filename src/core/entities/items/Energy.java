package core.entities.items;

import core.entities.player.Player;
import core.world.Tile;
import rendering.Screen;
import rendering.Texture;
import rendering.animation.Animation;
import rendering.animation.BasicAnimationOnTick;
import utils.Colors;

public class Energy extends Item{

	private int value;
	private Animation animation;
	private EnergyChunck chunck;
	
	public Energy(Tile tile, int x, int y, int value, EnergyChunck chunck) {
		super(tile, x, y, Texture.ENERGY_SPRITESHEET);
		this.animation = generateAnimation();
		this.value = value;
		this.chunck = chunck;
		this.tile.setEnergy(this);
	}
	
	public static int retrievePower(int rgb) {
		return Colors.green(rgb);
	}
	
	private Animation generateAnimation() {
		Texture s = Texture.ENERGY_SPRITESHEET;
		return BasicAnimationOnTick.get(s, 12, 1.0f,
				0, 0, 7, 10,
				8, 0, 7, 10,
				16, 0, 7, 10,
				24, 0, 7, 10);
	}

	@Override
	public void render(Screen s) {
		animation.render(s, x + 4, y + 3);
	}

	@Override
	public void update() {
		animation.update();
	}
	
	@Override
	public void interact(Player player) {
		player.addPower(value);
		chunck.remove(this);
		tile.setEnergy(null);
	}
	
	@Override
	public int getWidth() {
		return animation.getCurrentFrame().getWidth();
	}

	@Override
	public int getHeight() {
		return animation.getCurrentFrame().getHeight();
	}
	
	public int getValue() {
		return value;
	}

}
