package org.example;

public class URLDepthPair {
    private String URL;
    private int depth;

    public URLDepthPair(String URL, int depth) {
        this.URL = URL;
        this.depth = depth;
    }

    public String getURL() {
        return URL;
    }

    public int getDepth() {
        return depth;
    }

    public String toString() {
        return URL + " " + depth;
    }
}
