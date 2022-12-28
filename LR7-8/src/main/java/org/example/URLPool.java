package org.example;

import java.util.HashSet;
import java.util.LinkedList;

public class URLPool {
    public LinkedList<URLDepthPair> pendingURLs = new LinkedList<>();
    public HashSet<String> seenURLs = new HashSet<>();
    public LinkedList<URLDepthPair> processedURLs = new LinkedList<>();

    private final int maxDepth;

    private int waitingThreads = 0;

    public URLPool(int maxDepth){
        this.maxDepth = maxDepth;
    }

    public synchronized void addPair(URLDepthPair item){
        if (item.getDepth() <= maxDepth){
            if (!seenURLs.contains(item.getURL())) {
                seenURLs.add(item.getURL());
                pendingURLs.add(item);
            }
            notify();
        }
    }

    public synchronized URLDepthPair getPair(){
        while (pendingURLs.isEmpty()){
            waitingThreads++;
            try {
                wait();
            } catch (InterruptedException ignore) {
            }
            waitingThreads--;
        }
        URLDepthPair item = pendingURLs.pop();
        processedURLs.add(item);
        return item;
    }

    public synchronized int getWaiting(){
//        System.out.println(waitingThreads);
        return waitingThreads;
    }

    public synchronized LinkedList<URLDepthPair> getProcessedURLs(){
        return processedURLs;
    }

}
