package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class HTMLPage {
    private final URL url;
    private final StringBuilder html = new StringBuilder();

    public HTMLPage(URL url) throws IOException {
        this.url = url;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line);
            }
        }
    }

    public String getHTML() {
        return html.toString();
    }
}
