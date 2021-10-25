package com.company.Laba7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedList;

class Crawler {
    private static LinkedList<URLDepthPair> urls = new LinkedList<URLDepthPair>();
    private static LinkedList<URLDepthPair> processedUrls = new LinkedList<URLDepthPair>();

    public static final String LINK_PREFIX = "<a href=" + '"';
    public static final String URL_PREFIX = "http://";

    private int depth = 0;
    private String url = "";

    private static int counter = 0;

    private static boolean enabledLogs = false;

    public Crawler(String url, int depth) {
        this.url = url;
        this.depth = depth;
        urls.add(new URLDepthPair(url, 0));
        processedUrls.add(new URLDepthPair(url, 0));
    }

    public void work() throws IOException {
        while (urls.size() > 0)
            parseLinks(urls.pop());

        System.out.println("\n=============== Links ================");
        for (URLDepthPair pair : processedUrls)
            System.out.println(pair.getURL());


    }

    private void parseLinks(URLDepthPair pair) throws IOException {
        if (pair.getDepth() >= this.depth) {
            return;
        }

        try {
            Socket socket = new Socket(pair.getHost(), 80);
            socket.setSoTimeout(1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("GET " + pair.getPath() + " HTTP/1.1");
            out.println("Host: " + pair.getHost());
            out.println("Connection: close");
            out.println();
            out.flush();
            counter++;
            System.out.println("Checking " + Integer.toString(counter) + " page: " + pair.getHost());

            String str;

            while ((str = in.readLine()) != null) {
                if(enabledLogs) System.out.println(str);
                while (str.contains(LINK_PREFIX + URL_PREFIX)) {
                    StringBuilder link = new StringBuilder();
                    int i = 0;
                    for (i = str.indexOf("http://"); str.charAt(i) != '"'; i++)
                        link.append(str.charAt(i));
                    str = str.substring(i, str.length());
                    URLDepthPair newPair = new URLDepthPair(link.toString(), pair.getDepth() + 1);
                    if (!(newPair.listContains(processedUrls))) {
                        processedUrls.add(newPair);
                        if (newPair.getDepth() < this.depth) {
                            urls.add(newPair);
                        }
                    }
                }
            }

            socket.close();
        } catch (SocketException | SocketTimeoutException | UnknownHostException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        try {
//            Crawler crawler = new Crawler(args[0], Integer.parseInt(args[1]));
            Crawler crawler = new Crawler("http://mtuci.theweather.space/", 1);
            crawler.work();
        } catch (ArrayIndexOutOfBoundsException | IOException e) {
            System.out.println(e);
            System.out.println("usage: java Crawler <url> <depth>");
        }
    }
}
