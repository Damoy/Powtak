package core.entities.walls;

import java.awt.Color;

import core.world.Tile;
import core.world.light.LightHint;
import rendering.Screen;
import rendering.Texture;

public class EnlightnedWall extends Wall{

	private int brightness;
	private int rgb;
	
	public EnlightnedWall(Tile tile, WallChunck wallChunck, int x, int y, Texture texture, Color color, int brightness) {
		super(tile, wallChunck, x, y, texture);
		this.rgb = color.getRGB();
		this.brightness = brightness;
	}

	@Override
	public void render(Screen s) {
		s.enlight(x, y, texture.getWidth(), texture.getHeight(), rgb, brightness, LightHint.RECTANGLE);
		s.renderTex(texture, x, y);
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDestructible() {
		return false;
	}

}
