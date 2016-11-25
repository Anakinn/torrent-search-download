package torrent.search;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Torrent1337x {
	private static final String URL = "https://1337x.to/search/";
	String torrentName;
	private Elements search(String search) throws IOException,
			InterruptedException {
		Connection.Response response = Jsoup
				.connect(URL + search.replaceAll(" ", "+") + "/1/")
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.2; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0")
				.execute();

		Document doc = response.parse();
		Elements links = doc.select("a[href]");
		response = Jsoup
				.connect("https://1337x.to" + realURL(links))
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.2; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0")
				.timeout(130 * 1000).execute();

		doc = response.parse();
		links = doc.select("a[href]");
		Element media = doc.select("h1").first();
		torrentName=media.toString();
		return links;
	}

	private String realURL(Elements links) {

		String[] urls = links.toString().split("\n");
		String desiredUrl = "";
		for (int i = 0; i < urls.length; i++) {
			if (urls[i].startsWith("<a href=\"/torrent/")) {
				desiredUrl = urls[i];
				break;
			}
		}
		desiredUrl = desiredUrl.substring(desiredUrl.indexOf("\"") + 1,
				desiredUrl.length());
		desiredUrl = desiredUrl.substring(0, desiredUrl.indexOf("\""));
		
		return desiredUrl;
	}

	// returning magnet link ready for download
	public String downloadtMagnetLink(String torrent) throws IOException,
			InterruptedException {

		String[] results = search(torrent).toString().split("\n");
		String finalResult = "";
		for (int i = 0; i < results.length; i++) {
			if (results[i].contains("magnet")) {
				finalResult = results[i];
				break;
			}

		}
		
		finalResult = finalResult.substring(finalResult.indexOf("=") + 1);
		finalResult = finalResult.substring(finalResult.indexOf("=") + 2);

		finalResult = finalResult.substring(0, finalResult.indexOf("\""));

		printTorrentName();
		return finalResult;
	}

	// printing torrent name
	private void printTorrentName() {

		System.out.println("The Torrent1337 torrent name : " + torrentName.substring(4, torrentName.length()-6));

	}
}