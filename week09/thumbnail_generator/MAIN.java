package week09.thumbnail_generator;

import java.io.IOException;


public class MAIN {

	public static void main(String[] args) {

		int startParseIndex = 0;

		String option = null;
		if (args[startParseIndex].startsWith("-")) {
			option = args[startParseIndex].substring(1,
					args[startParseIndex].length());
			startParseIndex = 1;
		}
		String dir = args[startParseIndex];
		Integer with = Integer.parseInt(args[startParseIndex + 1]);
		Integer high = Integer.parseInt(args[startParseIndex + 2]);
		boolean recursivly = false;
		if (option != null) {
			if (option.equals("r")) {
			recursivly = true;
			} else {
				System.out.println("Illegel Argumet Option: " + option);
				return;
			}
		}
		
		Thread t = new Thread(new CheckDIrs(dir, recursivly, with, high));
		t.start();
	}

}
