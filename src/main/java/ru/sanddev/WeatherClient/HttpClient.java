package ru.sanddev.WeatherClient;

import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 04.07.2023
 */

@Log4j
public class HttpClient {

    private DefaultHttpClient client;

    public HttpClient() {
        log.debug("HTTP client: Prepare ssl");
        SSLSocketFactory ssl = SSLSocketFactory.getSocketFactory();
        ssl.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme httpsScheme = new Scheme("https", ssl, 443);

        log.debug("HTTP client: Prepare client");
        client = new DefaultHttpClient();
        client.getConnectionManager().getSchemeRegistry().register(httpsScheme);
    }

    public String execute(String url) throws IOException {
        log.debug(String.format("HTTP connecting to %s", url));

        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        log.debug("HTTP connecting successful");

        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        log.debug("HTTP response was retrieved");

        return result;
    }

}
