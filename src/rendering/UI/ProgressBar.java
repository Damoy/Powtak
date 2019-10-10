package rendering.UI;

import javax.swing.JProgressBar;

import core.Core;

public class ProgressBar {

	private JProgressBar bar;
	private int x;
	private int y;
	
	public ProgressBar(Core core, int x, int y) {
		bar = new JProgressBar(0, 100);
		core.add(bar);
		this.x = x;
		this.y = y;
		
		//LayoutManager layoutManager = core.getLayout();
		//core.setLayout(null);
		//positionOnScreen();
		//core.setLayout(layoutManager);
	}
	
	public void positionOnScreen() {
		bar.setLocation(x, y);
	}
	
	public void update(int v) {
		bar.setValue(v);
	}
	
}
