package rendering;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import core.Core;
import core.world.light.LightEngine;
import core.world.light.LightHint;
import rendering.config.TextRenderingConfig;
import utils.Config;
import utils.ScreenPositionCalculator;
import utils.TickCounter;

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
	
	public void enlight(int sx, int sy, int w, int h, int color, float brightness, LightHint lhint) {
		LightEngine.get().enlight(this, sx, sy, w, h, color, brightness, lhint);
	}
	
	public void darken(int sx, int sy, int w, int h, int color, float brightness, LightHint lhint) {
		LightEngine.get().darken(this, sx, sy, w, h, color, brightness, lhint);
	}
	
	public void darken(float brightness) {
		darken(0, 0, Config.WIDTH, Config.HEIGHT, Color.WHITE.getRGB(), brightness, LightHint.RECTANGLE);
	}
	
	public void render() {
		//darken(DarkenValue);
		Graphics g2 = core.getGraphics();
		g2.drawImage(data, 0, 0, Config.S_WIDTH, Config.S_HEIGHT, null);
		g2.dispose();
	}
	
	float DarkenValue = 0.1f;
	float ddv = 0.1f;
	TickCounter darkenCounter = new TickCounter(Config.UPS >> 1);
	
	public void update() {
		darkenCounter.increment();
		if(darkenCounter.isStopped()) {
			DarkenValue += ddv;
			darkenCounter.reset();
			if(DarkenValue <= 0.0f || DarkenValue >= 0.8f) {
				ddv = -ddv;
			}
		}
	}
	
	public BufferedImage getData() {
		return data;
	}
	
	public Graphics2D g() {
		return gdata;
	}
	
}
