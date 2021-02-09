package dns.parser.task;

import dns.parser.entity.Host;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author eli
 */
@Slf4j
public class CrawlTask implements Runnable {


    private final HttpClient httpClient;

    private final String url;

    public CrawlTask(HttpClient httpClient, String url) {
        this.httpClient = httpClient;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            HttpResponse response = httpClient.execute(new HttpGet(url));
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity, "utf-8");
            Host host = parseDocument(body);
            log.info("{} {}", host.getIpv4(), host.getDomain());
        } catch (Exception e) {
            log.error("error occur while parse {}", url, e);
        }
    }


    private Host parseDocument(String body) {
        Document doc = Jsoup.parse(body);
        String[] split = url.split("/");
        String ipv4 = doc.select("table > tbody > tr:nth-child(2) > td > ul > li").html();
        return new Host(split[split.length-1], ipv4);
    }
}
