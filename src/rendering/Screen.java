package rendering;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import core.Core;
import rendering.config.TextRenderingConfig;
import utils.Config;
import utils.ScreenPositionCalculator;

public class Screen {

	private Core core;
	private BufferedImage data;
	private Graphics2D gdata;
	
	private Screen(Core core, BufferedImage data, Graphics2D gdata) {
		this.core = core;
		this.data = data;
		this.gdata = gdata;
	}
	
	public void clear(Color color) {
		Color oldColor = g().getColor();
		g().setColor(color);
		g().fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
		g().setColor(oldColor);
	}
	
	public void renderTex(Texture texture, int x, int y) {
		g().drawImage(texture.get(), x, y, null);
	}
	
	public void renderText(String s, int x, int y, float fontSize, Color fontColor) {
		Font oldFont = g().getFont();
		Color oldFontColor = g().getColor();
		
		g().setFont(TextRenderingConfig.GAME_RENDERING_FONT.deriveFont(fontSize));
		g().setColor(fontColor);
		g().drawString(s, x, y);
		
		g().setFont(oldFont);
		g().setColor(oldFontColor);
	}
	
	public void renderTextWithFont(String s, Font font, int x, int y, float fontSize, Color fontColor) {
		Font oldFont = g().getFont();
		Color oldFontColor = g().getColor();
		
		g().setFont(font.deriveFont(fontSize));
		g().setColor(fontColor);
		g().drawString(s, x, y);
		
		g().setFont(oldFont);
		g().setColor(oldFontColor);
	}
	
	public void renderText(int v, int x, int y, float fontSize, Color fontColor) {
		renderText(Integer.toString(v), x, y, fontSize, fontColor);
	}
	
	public void renderUIText(int content, float fontSize, Color fontColor) {
		Point textPos = ScreenPositionCalculator.computePositionAtUIStart(content, fontSize, 0);
		renderText(content, textPos.x, textPos.y, fontSize, fontColor);
	}
	
	public static Screen of(Core core, BufferedImage data) {
		return new Screen(core, data, (Graphics2D) data.getGraphics());
	}
	
	public void render() {
		Graphics g2 = core.getGraphics();
		g2.drawImage(data, 0, 0, Config.S_WIDTH, Config.S_HEIGHT, null);
		g2.dispose();
	}
	
	public void update() {
	}
	
	public BufferedImage getData() {
		return data;
	}
	
	public Graphics2D g() {
		return gdata;
	}
	
}
