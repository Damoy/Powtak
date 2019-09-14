package utils;

import java.util.Random;

public final class Utils {
	
	private final static double log10of2 = Math.log10(2);
	private static Random seed = new Random();
	
	private Utils() {}
	
	public static double log2(double of) {
		return Math.log10(of) / log10of2;
	}
	
	public static double log(double nb, int base){
		return Math.log10(nb) / Math.log10(base);
	}
	
	public static void randomize(){
		seed = new Random();
	}
	
	public static int irand(int min, int max){
		return seed.nextInt((max - min) + 1) + min;
	}
	
	public static int getDigitsLen(int x) {
		return (int) (Math.log10(x) + 1);
	}
	
	public static String levelPath(String levelFileName) {
		return "./resources/levels/" + levelFileName;
	}
	
	public static String texturePath(String textureFileName) {
		return "./resources/textures/" + textureFileName;
	}
	
	public static String fontPath(String fontFileName) {
		return "./resources/fonts/" + fontFileName;
	}
}
