package com.company.Laba8;

import java.util.LinkedList;

public class URLPool {
    private static LinkedList<URLDepthPair> urls = new LinkedList<URLDepthPair>();
    private static LinkedList<URLDepthPair> processedUrls = new LinkedList<URLDepthPair>();

    private int maxDepth;
    private int dataWait;

    public URLPool(int maxDepth){
        this.maxDepth = maxDepth;
        processedUrls = new LinkedList<URLDepthPair>();
        urls = new LinkedList<URLDepthPair>();
        this.dataWait = 0;
    }

    public synchronized LinkedList<URLDepthPair> getAllPairs() {
        return urls;
    }
    public synchronized LinkedList<URLDepthPair> getProcessedPairs(){
        return processedUrls;
    }

    public synchronized URLDepthPair pop(){

        while (urls.size() == 0) {
            dataWait++;
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            dataWait--;
        }

        return urls.removeFirst();
    }

    public synchronized void addPair ( URLDepthPair pair ){
        if(!pair.listContains(processedUrls)) {
            processedUrls.add(pair);
            if (pair.getDepth() <= maxDepth) {
                urls.add(pair);
                notify();
            }
        }
    }

    public synchronized  int getWait(){
        return dataWait;
    }

    public synchronized int getMaxDepth(){
        return this.maxDepth;
    }

}