package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlerTask implements Runnable {

    private final URLPool pool;

    private final Pattern urlPattern = Pattern.compile("((?<=href=\")https?://\\S*(?=\"))");

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public CrawlerTask(URLPool pool){
        this.pool = pool;
    }

    private String sendGet(String URL) {

        HttpResponse<String> response;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(URL))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot")
                    .build();

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (Exception ignore){
            return null;
        }

        return response.body();


    }

    @Override
    public void run() {
        while (true){
            System.out.println("Processing...");
            URLDepthPair cur = pool.getPair();

            String body;
            body = this.sendGet(cur.getURL());

            if (body == null) {
                continue;
            }

            Matcher matcher = urlPattern.matcher(body);

            while (matcher.find()) {
                URLDepthPair pendingPair = new URLDepthPair(matcher.group(), cur.getDepth()+1);

                pool.addPair(pendingPair);
            }
        }
    }
}
