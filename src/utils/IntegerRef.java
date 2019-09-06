package utils;

public class IntegerRef {

	private int data;
	
	public IntegerRef(int init) {
		this.data = init;
	}
	
	public int inc(int v) {
		data += v;
		return data;
	}
	
	public int dec(int v) {
		return inc(-v);
	}
	
	public int get() {
		return data;
	}
	
	public void set(int v) {
		data = v;
	}
}
