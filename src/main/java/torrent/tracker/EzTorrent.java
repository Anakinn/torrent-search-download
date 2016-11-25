package torrent.tracker;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class EzTorrent {

	private static final String URL = "https://eztv.ag/search/";

	private Elements search(String search) throws IOException {
		Connection.Response response = Jsoup
				.connect(URL + search.replaceAll(" ", "-"))
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
			if (results[i].startsWith("<a href=\"/ep")) {
				torrentName = results[i];
				break;
			}

		}
		torrentName = torrentName.substring(torrentName.indexOf("=") + 1,
				torrentName.length());
		torrentName = torrentName.substring(torrentName.indexOf("=") + 2,
				torrentName.length());
		torrentName = torrentName.substring(0, torrentName.indexOf("\""));

		System.out.println("The Ez torrent name is : " + torrentName);

	}
}