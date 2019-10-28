package utils;

import java.lang.reflect.Array;

public class Selection<T> {

	private boolean loop;
	private int max;
	private int index;
	private T[] options;
	
	@SuppressWarnings("unchecked")
	public Selection(Class<T> clazz, int max, boolean loop) {
		this.max = max;
		this.index = 0;
		this.loop = loop;
		this.options = (T[]) Array.newInstance(clazz, max);
	}
	
	public void addEntry(int index, T option) {
		options[index] = option;
	}
	
	public T getCurrentOption() {
		return options[index];
	}
	
	public void increaseValue() {
		increaseValue(1);
	}
	
	public void decreaseValue() {
		increaseValue(-1);
	}
	
	public void increaseValue(int offset) {
		this.index += offset;
		
		if(loop) {
			index = index % max;
		} else {
			capIndex();
		}
	}
	
	private void capIndex() {
		if(index < 0)
			index = 0;
		if(index > max)
			index = max;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public T[] getOptions() {
		return options;
	}

	public void setOptions(T[] options) {
		this.options = options;
	}
	
}
