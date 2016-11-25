package torrent.search;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import uriSchemeHandler.CouldNotOpenUriSchemeHandler;
import uriSchemeHandler.URISchemeHandler;

public class App {
	public static void enableSSLSocket() throws KeyManagementException,
			NoSuchAlgorithmException {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});

		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, new X509TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		} }, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(context
				.getSocketFactory());
	}

	public static void main(String[] args) throws IOException,
			CouldNotOpenUriSchemeHandler, URISyntaxException,
			KeyManagementException, NoSuchAlgorithmException,
			InterruptedException {

		/*
		 * Enabling SSL connection
		 */
		enableSSLSocket();
		
		EzTorrent tor = new EzTorrent();
		ThePirateBay tor2 = new ThePirateBay();
		Torrent1337x tor3 = new Torrent1337x();
		String search = "Ubuntu";
		

			// downloadMagnet(tor.downloadtMagnetLink(serach));
			downloadMagnet(tor2.downloadtMagnetLink(search));
			downloadMagnet(tor3.downloadtMagnetLink(search));
		
	}

	// method for opening file in torrent client
	public static void downloadMagnet(String link)
			throws CouldNotOpenUriSchemeHandler, URISyntaxException {
		URI magnetLinkUri = new URI(link);
		URISchemeHandler uriSchemeHandler = new URISchemeHandler();
		uriSchemeHandler.open(magnetLinkUri);
	}

}
