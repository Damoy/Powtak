package utils;

public class AABB {

	private int x;
	private int y;
	private int width;
	private int height;
	
	public AABB(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean collides(AABB aabb) {
		return collides(aabb.x, aabb.y, aabb.width, aabb.height);
	}
	
	public boolean collides(int x2, int y2, int w2, int h2) {
		return !((x > (x2 + w2)) ||
				((x + width) < x2) ||
				(y > (y2 + h2))
				|| ((y + height) < y2));
	}
	
}
