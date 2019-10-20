package utils;

import java.awt.Color;

public class Colors {

	private Colors() {}
	
	public static int alpha(int rgb) {
		return (rgb >> 24) & 0xff;
	}
	
	public static int red(int rgb) {
		return (rgb >> 16) & 0xff;
	}
	
	public static int green(int rgb) {
		return (rgb >> 8) & 0xff;
	}
	
	public static int blue(int rgb) {
		return (rgb & 0xFF);
	}
	
	public static Color from(int rgb) {
		int r = red(rgb);
		int g = green(rgb);
		int b = blue(rgb);
		int a = alpha(rgb);
		return new Color(r, g, b, a);
	}
	
	public static Color increase(Color sourceColor, Color increaseColor, int power) {
		int sr = sourceColor.getRed();
		int sg = sourceColor.getGreen();
		int sb = sourceColor.getBlue();
		int sa = sourceColor.getAlpha();
		
		int ir = increaseColor.getRed();
		int ig = increaseColor.getGreen();
		int ib = increaseColor.getBlue();
		int ia = increaseColor.getAlpha();
		
		return increase(sr, sg, sb, sa, ir, ig, ib, ia, power);
	}
	
	public static Color increase(int sourceColorRGB, Color increaseColor, int power) {
		int sr = red(sourceColorRGB);
		int sg = green(sourceColorRGB);
		int sb = blue(sourceColorRGB);
		int sa = alpha(sourceColorRGB);
		
		int ir = increaseColor.getRed();
		int ig = increaseColor.getGreen();
		int ib = increaseColor.getBlue();
		int ia = increaseColor.getAlpha();
		
		return increase(sr, sg, sb, sa, ir, ig, ib, ia, power);
	}
	
	public static Color increase(Color sourceColor, int increaseColorRGB, int power) {
		int sr = sourceColor.getRed();
		int sg = sourceColor.getGreen();
		int sb = sourceColor.getBlue();
		int sa = sourceColor.getAlpha();
		
		int ir = red(increaseColorRGB);
		int ig = green(increaseColorRGB);
		int ib = blue(increaseColorRGB);
		int ia = alpha(increaseColorRGB);
		
		return increase(sr, sg, sb, sa, ir, ig, ib, ia, power);
	}
	
	public static Color increase(int sourceColorRGB, int increaseColorRGB, int power) {
		int sr = red(sourceColorRGB);
		int sg = green(sourceColorRGB);
		int sb = blue(sourceColorRGB);
		int sa = alpha(sourceColorRGB);
		
		int ir = red(increaseColorRGB);
		int ig = green(increaseColorRGB);
		int ib = blue(increaseColorRGB);
		int ia = alpha(increaseColorRGB);
		
		return increase(sr, sg, sb, sa, ir, ig, ib, ia, power);
	}
	
	private static Color increase(int sr, int sg, int sb, int sa,
			int ir, int ig, int ib, int ia, int power) {
		sr += ((255 - ir) / 255.0f) * power;
		sg += ((255 - ig) / 255.0f) * power;
		sb += ((255 - ib) / 255.0f) * power;
		sa += ((255 - ia) / 255.0f) * power;
		
		if(sr < 0) sr = 0;
		if(sg < 0) sg = 0;
		if(sb < 0) sb = 0;
		if(sa < 0) sa = 0;
		
		if(sr > 255) sr = 255;
		if(sg > 255) sg = 255;
		if(sb > 255) sb = 255;
		if(sa > 255) sa = 255;
		
		return new Color(sr, sg, sb, sa);
	}
	
}
