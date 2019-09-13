package utils;

import java.util.ArrayList;
import java.util.List;

public final class Factory {

	private Factory() {}
	
	@SafeVarargs
	public static <T> List<T> listOf(Class<T> type, T... content){
		List<T> list = new ArrayList<>();
		for(T element : content)
			list.add(element);
		return list;
	}
	
}
