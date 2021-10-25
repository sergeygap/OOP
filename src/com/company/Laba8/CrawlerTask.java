package com.company.Laba8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class CrawlerTask implements Runnable {

    private URLPool urlPool;

    public CrawlerTask(URLPool urlPool){
        this.urlPool = urlPool;
    }

    public void run() {
        while (true) {
            try {
                parseLinks(urlPool.pop());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseLinks(URLDepthPair pair) throws IOException {
        if (pair.getDepth() >= urlPool.getMaxDepth()) {
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

            String str;

            while ((str = in.readLine()) != null) {
                if(Crawler.enabledLogs) System.out.println(str);
                while (str.contains(Crawler.LINK_PREFIX + Crawler.URL_PREFIX)) {
                    StringBuilder link = new StringBuilder();
                    int i = 0;
                    for (i = str.indexOf("http://"); str.charAt(i) != '"'; i++)
                        link.append(str.charAt(i));
                    str = str.substring(i, str.length());
                    URLDepthPair newPair = new URLDepthPair(link.toString(), pair.getDepth() + 1);
                    urlPool.addPair(newPair);
                }
            }

            socket.close();
        } catch (SocketException | SocketTimeoutException | UnknownHostException e) {
            System.out.println(e);
        }
    }
}
