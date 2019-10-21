package rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.configs.GameConfig;
import utils.Utils;

public class Texture {
	
	// projectiles
	public final static Texture GRAY_PROJECTILE = getSubTexture(Utils.texturePath("grayProj.png", "projectiles"), 0, 0, 2, 2);
	public final static Texture RED_PROJECTILE = getSubTexture(Utils.texturePath("redProj.png", "projectiles"), 0, 0, 2, 2);
	
	// tiles
	public final static Texture GROUND_TILE = getTexture(Utils.texturePath("groundTile.png", "tiles"));
	
	// walls
	public final static Texture DESTRUCTIBLE_WALL = getTexture(Utils.texturePath("dwall.png", "walls"));
	public final static Texture STANDARD_WALL = getTexture(Utils.texturePath("swall.png", "walls"));
	
	// doors
	public final static Texture DOOR = getTexture(Utils.texturePath("door.png", "doors"));
	
	// keys
	public final static Texture KEY = getTexture(Utils.texturePath("key.png", "keys"));
	
	// Icons
	public final static Texture NORMAL_PLAYER_ICON = getSubTexture(Utils.texturePath("normalPlayerIcon.png", "icons"), 0, 0, 12, 10);
	
	// Sprite-sheets
	public final static Texture PLAYER_SPRITESHEET = getSubTexture(Utils.texturePath("player_spritesheet.png", "player"), 0, 0, 155, 13);
	public final static Texture PARTICULE_SPRITESHEET = getSubTexture(Utils.texturePath("particuleAnimTest.png", "particles"), 0, 0, 9, 3);
	public final static Texture ENERGY_SPRITESHEET = getSubTexture(Utils.texturePath("energy_spritesheet.png", "energy"), 0, 0, 31, 10);
	public final static Texture ZOMBIE_SPRITESHEET = getSubTexture(Utils.texturePath("zombie_spritesheet.png", "enemies"), 0, 0, 87, 13);
	public final static Texture TELEPORT_POINT_SPRITESHEET = getSubTexture(Utils.texturePath("portal_spritesheet.png", "portals"), 0, 0, 64, 16);

	private int width;
	private int height;
	private BufferedImage data;
	private String filePath;
	
	private Texture(int width, int height, BufferedImage data, String filePath) {
		this.width = width;
		this.height = height;
		this.data = data;
		this.filePath = filePath;
	}

	public static Texture of(int width, int height, BufferedImage data, String filePath) {
		return new Texture(width, height, data, filePath);
	}
	
	public static Texture getTexture(String filePath) {
		return getSubTexture(filePath, 0, 0, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
	}
	
	public static Texture getSubTexture(String filePath, int xp, int yp, int width, int height) {
		BufferedImage tex = new BufferedImage(width, height, GameConfig.TEXTURE_TYPE);
		try {
			tex = ImageIO.read(new FileInputStream(filePath));
			tex = tex.getSubimage(xp, yp, width, height);

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int argb = tex.getRGB(x, y);
					// removes the MAGENTA color from the texture
					if (argb == Color.MAGENTA.getRGB()) {
						tex.setRGB(x, y, 0);
					}
				}
			}
			
			return Texture.of(width, height, tex, filePath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}
	
	public Texture scale(float wscale, float hscale) {
		if(wscale == 1.0f && hscale == wscale) return this;
		
		int nw = (int) (width * wscale);
		int nh = (int) (height * hscale);
		
		Image tmp = data.getScaledInstance(nw, nh, Image.SCALE_SMOOTH);
		BufferedImage scaled = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = scaled.createGraphics();
		
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		
		data = scaled;
		return this;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getFilePath() {
		return filePath;
	}

	public BufferedImage get() {
		return data;
	}

}
