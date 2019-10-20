package core.world.light;

import java.awt.Color;
import java.awt.image.BufferedImage;

import rendering.Screen;
import utils.Colors;

public class LightEngine {

	private final static LightEngine INSTANCE = new LightEngine();
	
	private LightEngine() {}
	
	public static LightEngine get() {
		return INSTANCE;
	}

	public void enlightScreen(Screen screen, int power) {
		BufferedImage sdata = screen.getData();
		int w = sdata.getWidth();
		int h = sdata.getHeight();
		int blackRGB = Color.BLACK.getRGB();
		
		for(int x = 0; x < w; ++x) {
			for(int y = 0; y < h; ++y) {
				int rgb = sdata.getRGB(x, y);
				sdata.setRGB(x, y, Colors.increase(rgb, blackRGB, power).getRGB());
			}
		}
	}
	
}
