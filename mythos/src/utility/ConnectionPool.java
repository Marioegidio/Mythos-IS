package utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * Classe che si occupa di gestire le connessioni al db in locale.
 */
public class ConnectionPool {

	/**
	 * Metodo per ottenere la connessione del database.
	 * @return {@link Connection} connessione al db.
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		// se passa più di un tot tempo,devo far ricreare di nuovo la connessione
		// perchè se no succewde che 'datasource' punta ancora ad una connessione(non e'
		// null,quindi)
		// ma in relatà la reale connessione al database è andata in timeout;pertanto
		// non funziona
		if (lastCall == 0) {
			lastCall = System.currentTimeMillis();
		} else {
			long current = System.currentTimeMillis();
			if (current - lastCall >= 28800000) {
				lastCall = 0;
				datasource = null;
			}
		}

		if (datasource == null) {
			PoolProperties p = new PoolProperties();
			p.setDriverClassName("com.mysql.cj.jdbc.Driver");
			p.setUrl("jdbc:mysql://localhost:3306/mythos_is?characterEncoding=UTF-8&serverTimezone="
					+ TimeZone.getDefault().getID());// autoReconnect=true
			p.setUsername("root");
			p.setPassword("");
			p.setMaxActive(150);
			p.setInitialSize(10);
			p.setMinIdle(10);
			p.setRemoveAbandonedTimeout(1200);
			p.setRemoveAbandoned(true);
			// p.setMaxWait(30000000);
			datasource = new DataSource();
			datasource.setPoolProperties(p);
		}
		return datasource.getConnection();
	}

	/**
	 * datasource.
	 */
	private static DataSource datasource;
	/**
	 * ultima chiamata al db.
	 */
	private static long lastCall;

}
