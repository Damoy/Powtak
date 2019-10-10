package utils;

import java.awt.Color;

public class Colors {

	private Colors() {}
	
	public static int red(int rgb) {
		// return (rgb & 0xFF0000) >> 16;
		return (rgb >> 16) & 0xff;
	}
	
	public static int green(int rgb) {
		// return (rgb & 0xFF00) >> 8;
		return (rgb >> 8) & 0xff;
	}
	
	public static int blue(int rgb) {
		return (rgb & 0xFF);
	}
	
//	public static ColorBundle extract(Color color) {
//		return new ColorBundle(color.getRed(), color.getGreen(), color.getBlue());
//	}
	
	public static ColorBundle extract(int rgb) {
		int r = red(rgb);
		int g = green(rgb);
		int b = blue(rgb);
		
		return new ColorBundle(r, g, b);
	}
	
	public static Color of(ColorBundle colorBundle) {
		return new Color(colorBundle.r(), colorBundle.g(), colorBundle.b());
	}
	
	public static int increase(int rgb, Color color, float brightness) {
		return extract(rgb).increase(color, brightness).rgb();
	}
	
	// key when:
	// 	- red: 255  
	// 	- green: 255 
	// 	- blue: < 100
	public static boolean isKey(int rgb) {
		ColorBundle cBundle = extract(rgb);
		int r = cBundle.r();
		int g = cBundle.g();
		int b = cBundle.b();
		
		// 100 keys available
		return r == 255 && g == 255 && b >= 0 && b < 100;
	}
	
	// door when:
	// 	- red: 0
	// 	- green < 100
	// 	- blue: 255
	public static boolean isDoor(int rgb) {
		ColorBundle cBundle = extract(rgb);
		int r = cBundle.r();
		int g = cBundle.g();
		int b = cBundle.b();
		
		// 100 doors available
		return r == 0 && g >= 0 && g < 100 && b == 255;
	}
	
	// energy when:
	// 	- red: 255
	// 	- green < 100
	// 	- blue: 255
	public static boolean isEnergy(int rgb) {
		ColorBundle cBundle = extract(rgb);
		int r = cBundle.r();
		int g = cBundle.g();
		int b = cBundle.b();
		
		// 0 to 100 power amount
		return r == 255 && g >= 0 && g < 100 && b == 255;
	}
	
	// zombie when:
	// 	- red: 255
	// 	- green: 0
	// 	- blue < 4 ; 0 up ; 1 right ; 2 down ; 3 left
	public static boolean isZombie(int rgb) {
		ColorBundle cBundle = extract(rgb);
		int r = cBundle.r();
		int g = cBundle.g();
		int b = cBundle.b();
		return r == 255 && g == 0 && b >= 0 && b <= 3;
	}
	
	// spawn when:
	// 	- red: 0
	// 	- green >= 155
	// 	- blue: 0
	public static boolean isSpawn(int rgb) {
		ColorBundle cBundle = extract(rgb);
		int r = cBundle.r();
		int g = cBundle.g();
		int b = cBundle.b();
		// value of green significates value of initial energy
		return r >= 0 && r <= 3 && b == 0 && g >= 155 && g <= 255;
	}
	
	public static class ColorBundle{
		
		private int r;
		private int g;
		private int b;
		
		public ColorBundle(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}
		
		public ColorBundle increase(Color color, float brightness) {
			r += (int) (color.getRed() * brightness);
			g += (int) (color.getGreen() * brightness);
			b += (int) (color.getBlue() * brightness);
			computeBounds();
			return this;
		}
		
		public ColorBundle increase(int dr, int dg, int db, float brightness) {
			r += (int) (dr * brightness);
			g += (int) (dg * brightness);
			b += (int) (db * brightness);
			computeBounds();
			return this;
		}
		
		public ColorBundle decrease(int dr, int dg, int db, float brightness) {
			r -= (int) (dr * brightness);
			g -= (int) (dg * brightness);
			b -= (int) (db * brightness);
			computeBounds();
			return this;
		}
		
		public ColorBundle computeBounds() {
			if(r < 0) r = 0;
			if(g < 0) g = 0;
			if(b < 0) b = 0;
			if(r > 255) r = 255;
			if(g > 255) g = 255;
			if(b > 255) b = 255;
			
			return this;
		}
		
		public int rgb() {
			computeBounds();
			return new Color(r, g, b).getRGB();
		}

		public int r() {
			return r;
		}

		public int g() {
			return g;
		}

		public int b() {
			return b;
		}
		
	}
}
