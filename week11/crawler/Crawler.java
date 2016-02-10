package week11.crawler;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {

	private String uri;
	private String needle;
	private String content;
	private Set<String> crawledLinks;
	private List<String> matchedLinks;
	private int maxDepth;

	public Crawler(String uri, String needle) throws Exception {
		this.uri = uri;
		this.needle = needle;
		this.content = HttpResponeHandler.getContent(uri);
		this.crawledLinks = new HashSet<>();
		this.matchedLinks = new ArrayList<>();
		this.maxDepth = 10;
	}

	public String getContent() throws Exception {
		return content;
	}

	private List<String> getCurrLinks(String cont) {
		ArrayList<String> resultList = new ArrayList<>();
		String regex = "<a.*?href=\"((?!javascript).*?)\".*?>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			resultList.add(matcher.group(1));
		}
		return resultList;
	}
	
	public List<String> getLinks() {
		return getCurrLinks(content);
	}

	private void crawlRecur(String currLink, int depth) throws Exception{
		if ((maxDepth ==  depth) || crawledLinks.contains(currLink) || (!currLink.contains(uri))) {
			return;
		}
		// System.out.println(currLink);
		String content = HttpResponeHandler.getContent(currLink);
		if (content.contains(needle)) {
			System.out.println(currLink + " " + needle + " is found");
			matchedLinks.add(currLink);
		}
		crawledLinks.add(currLink);
		List<String> links = getCurrLinks(content);
		for (String link : links) {
			String newLink = (new URL(new URL(currLink), link)).toString();
			if (!newLink.contains("..")) {
				crawlRecur(newLink.toString(), depth + 1);
			}
		}
	}
	
	public List<String> crawled() throws Exception{
		crawlRecur(uri, 0);
		return matchedLinks;
	}

	public static void main(String[] args) throws Exception {

		Crawler c = new Crawler("http://sportal.bg", "Казийски");
		List<String> links = c.crawled();
		System.out.println();
		for (String link : links) {
			System.out.println(link);
		}
	}
}
