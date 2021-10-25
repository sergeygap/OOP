package com.company.Laba8;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

class URLDepthPair {
    private String url;
    private int depth;
    private int visited;

    public URLDepthPair(String url){
        this.url = url;
        this.depth = 0;
        this.visited = 1;
    }
    public URLDepthPair(String url, int depth) {
        this.url = url;
        this.depth = depth;
        this.visited = 1;
    }

    public String getURL() {
        return url;
    }

    public int getDepth() {
        return depth;
    }
    public String getHost() {
        String host = "";
        try {
            URL url = new URL(this.url);
            host = url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return host;
    }

    public String getPath() {
        String path = "";
        try {
            URL url = new URL(this.url);
            path = url.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return path;
    }

    public boolean listContains(LinkedList<URLDepthPair> list){
        for(URLDepthPair item : list)
            if(item.getURL().equals(this.url))
                return true;
        return false;
    }
}
