package torrent.tracker;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ThePirateBay {

	private static final String URL = "https://thepiratebay.org/search/";
	private static final String SORT = "/0/88/0"; //sorts by most seeds

	public ThePirateBay() {

	}

	// method to connect and search the pirate bay and get the link with most seeds
	private Elements search(String search) throws IOException {
		Connection.Response response = Jsoup
				.connect(URL + search + SORT)
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.2; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0")
				.execute();
		Document doc = response.parse();
		Elements links = doc.select("a[href]");
		
		return links;
	}

	// returning magnet link ready for download
	public String downloadtMagnetLink(String torrent) throws IOException {
		String[] results = search(torrent).toString().split("\n");
		String finalResult = "";
		for (int i = 0; i < results.length; i++) {
			if (results[i].startsWith("<a href=\"magnet:")) {
				finalResult = results[i];
				break;
			}

		}
		finalResult = finalResult.substring(finalResult.indexOf("\"") + 1);
		finalResult = finalResult.substring(0, finalResult.indexOf("\""));
		printTorrentName(results);
		return finalResult;
	}

	// printing torrent name
	private void printTorrentName(String[] results) {
		String torrentName = "";
		for (int i = 0; i < results.length; i++) {
			if (results[i].startsWith("<a href=\"/torrent/")) {
				torrentName = results[i];
				break;
			}

		}
		torrentName = torrentName.substring(torrentName.indexOf("\"") + 1);
		torrentName = torrentName.substring(0, torrentName.indexOf("\""));
		torrentName = torrentName.substring(torrentName.lastIndexOf("/") + 1,
				torrentName.length());
		System.out.println("The Pirate Bay torrent name is : " + torrentName);

	}
}
