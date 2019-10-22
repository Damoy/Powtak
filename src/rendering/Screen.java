package rendering;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import core.Core;
import core.configs.GameConfig;
import rendering.config.TextRenderingConfig;
import utils.ScreenComputation;

public class Screen {

	private Core core;
	private BufferedImage data;
	private Graphics2D gdata;
	
	private Font fontSaved;
	private Color colorSaved;
	
	private Screen(Core core, BufferedImage data, Graphics2D gdata) {
		this.core = core;
		this.data = data;
		this.gdata = gdata;
	}
	
	// -- Rendering
	public void clear(Color color) {
		Color oldColor = g().getColor();
		g().setColor(color);
		g().fillRect(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
		g().setColor(oldColor);
	}
	
	public Font setupTextRendering(float fontSize, Color fontColor) {
		Graphics2D g = g();
		fontSaved = g.getFont();
		colorSaved = g.getColor();
		Font derivedFont = TextRenderingConfig.GAME_RENDERING_FONT.deriveFont(fontSize);
		g.setFont(derivedFont);
		g.setColor(fontColor);
		return derivedFont;
	}
	
	public void cleanTextRendering() {
		Graphics2D g = g();
		g.setFont(fontSaved);
		g.setColor(colorSaved);
	}
	
	public void renderTex(Texture texture, int x, int y) {
		g().drawImage(texture.get(), x, y, null);
	}
	
	public void renderText(String s, int x, int y) {
		Graphics2D g = g();
		g.drawString(s, x, y);
	}
	
	public void renderWrapedText(String s, Color fillColor, int x, int y) {
		Graphics2D g = g();
		g.drawString(s, x, y);
		Color color = g.getColor();
		g.setColor(fillColor);
		g.drawString(s, x + 1, y + 1);
		g.setColor(color);
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
	
	public Point renderCenteredTextAndClean(Color fontColor, String s, float fontSize, int rowOffset) {
		Point centeredPoint = renderCenteredText(fontColor, s, fontSize, rowOffset);
		cleanTextRendering();
		return centeredPoint;
	}
	
	public Point renderCenteredText(Color fontColor, String s, float fontSize, int rowOffset) {
		setupTextRendering(fontSize, fontColor);
		Point stringPos = getStringCenteredPosition(s, fontSize, rowOffset);
		int x = stringPos.x;
		int y = stringPos.y;
		renderText(s, x, y);
		return new Point(x, y);
	}
	
	public Point renderCenteredWrapedTextAndClean(Color wrapColor, Color fontColor, String s, float fontSize, int rowOffset) {
		Point renderPosition = renderCenteredWrapedText(wrapColor, fontColor, s, fontSize, rowOffset);
		cleanTextRendering();
		return renderPosition;
	}
	
	public Point renderCenteredWrapedText(Color wrapColor, Color fontColor, String s, float fontSize, int rowOffset) {
		setupTextRendering(fontSize, wrapColor);
		Point stringPos = getStringCenteredPosition(s, fontSize, rowOffset);
		int x = stringPos.x;
		int y = stringPos.y;
		renderWrapedText(s, fontColor, x, y);
		return new Point(x, y);
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
	
	public void renderUIText(int content, float fontSize, Color fontColor) {
		Point textPos = ScreenComputation.computePositionAtUIStart(content, fontSize, 0);
		renderText(content, textPos.x, textPos.y, fontSize, fontColor);
	}
	
	public void renderText(int v, int x, int y, float fontSize, Color fontColor) {
		renderText(Integer.toString(v), x, y, fontSize, fontColor);
	}
	
	public static Screen of(Core core, BufferedImage data) {
		return new Screen(core, data, (Graphics2D) data.getGraphics());
	}
	
	public void render() {
		Graphics g2 = core.getGraphics();
		g2.drawImage(data, 0, 0, GameConfig.S_WIDTH, GameConfig.S_HEIGHT, null);
		g2.dispose();
	}
	
	// -- Computation
	public Point getStringCenteredPosition(String s, float fontSize, int rowOffset) {
		Point centerPoint = ScreenComputation.getCenter();
		int width = getStringWidth(s);
		int x = (int) (centerPoint.x - (width >> 1));
		int y = centerPoint.y + (rowOffset * GameConfig.TILE_SIZE);
		int height = (int) getStringBounds(g(), s, x, y).getHeight();
		y += (height >> 1);
		return new Point(x, y);
	}
	
	public int getStringHeight(String str) {
		return g().getFontMetrics().getHeight();
	}
	
	public int getStringWidth(String str) {
		return SwingUtilities.computeStringWidth(g().getFontMetrics(), str);
	}
	
	public Rectangle getStringBounds(Graphics2D g2, String str, float x, float y) {
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
		return gv.getPixelBounds(null, x, y);
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
