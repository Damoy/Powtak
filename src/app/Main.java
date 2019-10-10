package app;

import core.Core;
import utils.exceptions.PowtakException;

public class Main {
	
	public static void main(String[] args) throws PowtakException {
		Core.generate().start();
	}
}
