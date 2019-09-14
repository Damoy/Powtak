package rendering.config;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import utils.Utils;

public class TextRenderingConfig {

	public static Font GAME_RENDERING_FONT;
	public static final Font RAW_RENDERING_FONT = new Font("Tahoma", Font.PLAIN, 24);
	public static final Color PLAYER_ENERGY_FONT_COLOR = new Color(40, 220, 40);
	public static final float PLAYER_ENERGY_FONT_SIZE = 12.0f;
	
	static {
		try {
			GAME_RENDERING_FONT = Font.createFont(Font.TRUETYPE_FONT, new File(Utils.fontPath("ARCADECLASSIC.ttf")));
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(GAME_RENDERING_FONT);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not load font.");
		}
	}
	
}
