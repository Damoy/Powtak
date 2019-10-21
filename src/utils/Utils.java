package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import core.configs.GameConfig;
import utils.exceptions.NullObjectException;
import utils.exceptions.PowtakException;

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
	
	public static List<File> loadFilesIgnoreDirs(String directoryPath){
		List<File> files = loadFiles(directoryPath);
		return files.stream().filter(f -> !f.isDirectory()).collect(Collectors.toList());
	}
	
	
	public static List<File> loadFiles(String directoryPath){
		File file = new File(directoryPath);
		if(!file.isDirectory()) {
			Log.error("\"" + directoryPath + "\" files could not be loaded.");
			return null;
		} else {
			return Arrays.asList(file.listFiles());
		}
	}
	
	public static String levelPath(String levelFileName) {
		return GameConfig.CUSTOM_LEVELS_FILE_PATH + levelFileName;
	}
	
	public static String texturePath(String textureFileName, String folderName) {
		String texturePath = GameConfig.TEXTURES_FILE_PATH;
		if(folderName != null) {
			texturePath += folderName + "/";
		}
		return texturePath + textureFileName;
	}
	
	public static String fontPath(String fontFileName) {
		return GameConfig.FONTS_FILE_PATH + fontFileName;
	}
	
	public static List<String> getFileLines(String fileName) {
		try {
			return Files.lines(Paths.get(fileName)).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void validateNotNull(Object o, String errorMsg) throws PowtakException {
		if(o == null)
			throw new NullObjectException(errorMsg);
	}
}
