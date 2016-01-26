package week07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProperiesParser {
	/*
	@author: Angel Angelov
	*/	
	// private List<String> lines;
	private Map<String, String> data;
	private String file;

	public ProperiesParser (String file) {
		this.file = file;
		//lines = new ArrayList<String>();
		data = new HashMap<>();
	}
	
	public List<String> getLines() throws IOException {
		List<String> res = new ArrayList<>();
		BufferedReader f = new BufferedReader(new FileReader(file));
		String line = null;
		try {
		while (true) {
			line = f.readLine();
			if (line == null) {
				break;
			}
			res.add(line);
		}
		} finally {
			f.close();
		}
		return res;
	}
	
	public String[] parseLine(String line) {
		String [] res;
		if (line.startsWith("#")) {
			return null;
		}
		res = line.split("=", 2);
		if (res.length < 2) {
			return null;
		}
		res[0] = res[0].replaceAll("\\s+","" );
		res[1] = res[1].replaceAll("\\s+","" );
		res[1] = res[1].replaceAll("#.+$", "");
		// System.out.println(res[0] + " " + res[1]);
		return res;
	}
	
	public Map<String, String> parse() throws IOException {
		String [] parsedLine; 
		for (String temp: getLines()) {
			parsedLine = parseLine(temp);
			if (parsedLine != null ) {
				data.put(parsedLine[0], parsedLine[1]);
			}
		}
		return data;
	}
	
	public static void main(String [] Args) {
		ProperiesParser parser = new ProperiesParser("/home/angel/mytmp/property.txt");
		try {
			System.out.println(parser.parse());
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
