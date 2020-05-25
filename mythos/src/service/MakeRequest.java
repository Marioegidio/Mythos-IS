package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Servlet che si occupa di effettuare le richieste alla piattaforma online.
 */
public class MakeRequest {
	private final static String USER_AGENT = "Mozilla/5.0";
	//private final static String HOST = "http://reteblog.it/vima/mythos/webservices/";
	private final static String HOST="http://mythospr.altervista.org/mythos_pr/src/webservices/";

	/**
	 * Metodo che invia la richiesta all'host dove è situata la piattaforma per i p.r.
	 * @param resource {@link String} resource!=null indica la risorsa(nel nostro caso il link con i parametri GET).
	 * @return {@link String} la risposta del server.
	 */
	protected static String sendGet(String resource) throws IOException {

		URL obj = new URL(HOST + resource);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
}
