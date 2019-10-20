package core.world.light;

import java.awt.Color;

import rendering.Screen;

public class LightSpot{

	private Color color;
	private Screen screen;
	private int x;
	private int y;
	private int radius;
	
	public LightSpot(Screen screen, int x, int y, int radius, Color color) {
		this.color = color;
		this.screen = screen;
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public void enlight(float brightness) {
		screen.enlight(x, y, radius, radius, color.getRGB(), brightness);
	}
	
	public void darken(float brightness) {
		screen.darken(x, y, radius, radius, color.getRGB(), brightness);
	}

}
