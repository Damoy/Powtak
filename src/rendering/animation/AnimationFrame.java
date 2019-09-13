package rendering.animation;

import java.util.ArrayList;
import java.util.List;

import rendering.Screen;
import rendering.Texture;

public class AnimationFrame {

	private Texture frameSheet;
	private Texture frameData;
	private int startX;
	private int startY;
	private int width;
	private int height;
	private float scale;
	
	public AnimationFrame(Texture frameSheet, int startX, int startY, int width, int height, float scale) {
		this.frameSheet = frameSheet;
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.height = height;
		this.frameData = Texture.of(width, height, frameSheet.get().getSubimage(startX, startY, width, height), null);
		this.scale = scale;
		if(scale != 0) {
			this.frameData = this.frameData.scale(scale, scale);
		}
	}
	
	public static List<AnimationFrame> getFromData(Texture spritesheet, float scale, int... framesData){
		if(framesData.length % 4 != 0) throw new IllegalStateException();
		List<AnimationFrame> frames = new ArrayList<>();
		
		for(int i = 0; i < framesData.length; i += 4) {
			frames.add(new AnimationFrame(spritesheet,
					framesData[i], framesData[i + 1], framesData[i + 2], framesData[i + 3], scale));
		}
		
		return frames;
	}
	
	public void render(Screen s, int x, int y) {
		s.renderTex(frameData, x, y);
	}

	public Texture getFrameSheet() {
		return frameSheet;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getScale() {
		return scale;
	}
	
}
