package rendering.particles;

public class ParticleMovement {
	
	private int startX;
	private int startY;
	private int dx;
	private int dy;
	private int xIncs;
	private int yIncs;
	
	// frame dx, dy
	private int fdx;
	private int fdy;
	
	private boolean xStopped;
	private boolean yStopped;
	
	public ParticleMovement(int startX, int startY, int dx, int dy) {
		this.startX = startX;
		this.startY = startY; 
		this.dx = dx;
		this.dy = dy;
		this.xStopped = false;
		this.yStopped = false;
		this.xIncs = 0;
		this.yIncs = 0;
		this.fdx = (dx > 0) ? 1 : (dx == 0) ? 0 : -1;
		this.fdy = (dy > 0) ? 1 : (dy == 0) ? 0 : -1;
	}
	
	public void update(Particle particle) {
		if(isStopped()) return;
		
		int x = particle.getX();
		int y = particle.getY();
		
		if(!xStopped) {
			x += fdx;
			++xIncs;
			xStopped = xIncs >= Math.abs(dx);
			particle.setX(x);
		}
		
		if(!yStopped) {
			y += fdy;
			++yIncs;
			yStopped = yIncs >= Math.abs(dy);
			particle.setY(y);
		}
	}
	
	public void reset(Particle particle) {
		this.startX = particle.getX();
		this.startY = particle.getY();
		this.xStopped = false;
		this.yStopped = false;
		this.xIncs = 0;
		this.yIncs = 0;
	}
	
	public boolean isStopped() {
		return xStopped && yStopped;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}
	
}
