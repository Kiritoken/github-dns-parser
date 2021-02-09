package dns.parser;

import dns.parser.task.CrawlTask;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * github dns解析
 * 解决图片显示不了的问题
 * @author eli
 */
public class DnsParser {

    private static final PoolingHttpClientConnectionManager MANAGER = new PoolingHttpClientConnectionManager();

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.custom()
            .setConnectionManager(MANAGER)
            .build();

    private static final String[] DOMAINS = {"https://github.com.ipaddress.com/www.github.com",
            "https://githubusercontent.com.ipaddress.com/raw.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/gist.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/cloud.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/camo.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/avatars0.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/avatars1.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/avatars2.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/avatars3.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/avatars4.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/avatars5.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/avatars6.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/avatars7.githubusercontent.com" ,
            "https://githubusercontent.com.ipaddress.com/avatars8.githubusercontent.com",
            "https://githubusercontent.com.ipaddress.com/avatars.githubusercontent.com"};


    public static void main(String[] args){
        System.out.println("#GitHub start");
        ExecutorService executorService = Executors.newFixedThreadPool(DOMAINS.length);
        for (String domain : DOMAINS) {
            executorService.execute(new CrawlTask(HTTP_CLIENT, domain));
        }
        executorService.shutdown();
        while (true) {
            if(executorService.isTerminated()) {
                break;
            }
        }
        System.out.println("#GitHub end");
    }


}
