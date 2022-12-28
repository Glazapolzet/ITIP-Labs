package org.example;

import java.util.Scanner;

public class Crawlers {


    public static void main(String[] args) {

        //   https://slashdot.org/ 1 10
        //   https://habr.com/ru/all/ 2 40

        Scanner sc = new Scanner(System.in);
        String[] parsArgs = sc.nextLine().split(" ");

        String startUrl = parsArgs[0];
        int maxDepth = Integer.parseInt(parsArgs[1]);
        int maxThreads = Integer.parseInt(parsArgs[2]);


        if (parsArgs.length != 3 || !startUrl.matches("^https?://\\S*")) {
            System.out.println("usage: java Crawler <URL><maxDepth><pools>");
            System.exit(0);
        }

        URLPool pool = new URLPool(maxDepth);
        pool.addPair(new URLDepthPair(startUrl, 0));

        for (int i = 0; i < maxThreads; i++){
            Thread t = new Thread(new CrawlerTask(pool));
            t.start();
        }


        while (pool.getWaiting() < maxThreads){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore){}

        }

        System.out.println();
        System.out.println("Founded URLs: " + pool.getProcessedURLs());
        System.exit(0);
    }
}
