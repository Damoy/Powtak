package core.world.light;

import java.awt.image.BufferedImage;

import rendering.Screen;
import utils.Colors;
import utils.Colors.ColorBundle;

public class LightEngine {

	private final static LightEngine INSTANCE = new LightEngine();
	
	private LightEngine() {}
	
	public static LightEngine get() {
		return INSTANCE;
	}
	
	public void enlight(Screen screen, int sx, int sy, int w, int h, int color, float brightness, LightHint lhint) {
		if(lhint == LightHint.ELLIPSE) {
			eenlight(screen, sx, sy, w, h, color, brightness);
		} else {
			renlight(screen, sx, sy, w, h, color, brightness);
		}
	}
	
	private void eenlight(Screen screen, int sx, int sy, int w, int h, int color, float brightness) {
		renlight(screen, sx, sy, w, h, color, brightness);
	}
	
	private void renlight(Screen screen, int sx, int sy, int w, int h, int color, float brightness) {
		BufferedImage sdata = screen.getData();
		
		for(int x = sx; x < sx + w; ++x) {
			for(int y = sy; y < sy + h; ++y) {
				sdata.setRGB(x, y, enlight(sdata.getRGB(x, y), color, brightness));
			}
		}
	}
	
	private int enlight(int rgb, int color, float brightness) {
		ColorBundle rgbBundle = Colors.extract(rgb);
		ColorBundle colorBundle = Colors.extract(color);
		int dr = colorBundle.r();
		int dg = colorBundle.g();
		int db = colorBundle.b();
		
		return Colors.of(rgbBundle.increase(dr, dg, db, brightness)).getRGB();
	}
	
	public void darken(Screen screen, int sx, int sy, int w, int h, int color, float brightness, LightHint lhint) {
		BufferedImage sdata = screen.getData();
		
		for(int x = sx; x < sx + w; ++x) {
			for(int y = sy; y < sy + h; ++y) {
				sdata.setRGB(x, y, darken(sdata.getRGB(x, y), color, brightness));
			}
		}
	}
	
	private int darken(int rgb, int color, float brightness) {
		ColorBundle rgbBundle = Colors.extract(rgb);
		ColorBundle colorBundle = Colors.extract(color);
		int dr = colorBundle.r();
		int dg = colorBundle.g();
		int db = colorBundle.b();
		
		return Colors.of(rgbBundle.decrease(dr, dg, db, brightness)).getRGB();
	}
	
}
