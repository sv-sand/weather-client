package ru.sanddev.WeatherClient;

import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 04.07.2023
 */

@Log4j
public class HttpClient {

    public String doGetRequest(String url) throws IOException {
        log.debug(String.format("Get request %s", url));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        log.debug("HTTP connection successful");

        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        log.debug("HTTP response was retrieved");

        client.close();
        log.debug("Close HTTP connection");

        return result;
    }
}
