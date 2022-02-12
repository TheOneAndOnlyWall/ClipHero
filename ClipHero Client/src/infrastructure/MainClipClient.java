package infrastructure;

import client.ServerFinderKotlin;

public class MainClipClient {

    public static void main(String[] args) {

        ServerFinderKotlin finder = new ServerFinderKotlin();
        finder.start();

    }

}