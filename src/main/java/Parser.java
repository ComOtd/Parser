import Entity.Content;
import Proxy.ProxyParser;
import Proxy.ProxyChecker;
import Torrents.TorrentRutracker;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static Proxy.ProxyUtil.proxyFileToList;

public class Parser {
    public static void main(String[] args) {

        String findWord = "Миссия невыполнима Последствия";
        double flashSize = 50;
        String param = "1";

        switch (param) {
            case "1":

                List<String> proxy = new ArrayList<>();
                List<String> url = new ArrayList<>();
                url.add("https://getfreeproxylists.blogspot.com/");
                url.add("http://free-proxy-server.net/");
                ProxyParser proxyParser = new ProxyParser(0);

                for (String s : url) {
                    proxy.addAll(proxyParser.parseByUrl(s));
                }
                proxy = proxy.stream().distinct().collect(Collectors.toList());

                ProxyChecker checker = new ProxyChecker("https://rutracker.org");
                checker.checkProxyAndSaveToFile(proxy, 3);
                break;


            case "2":
                Document doc = null;
                List<Proxy> proxies = proxyFileToList("src/tmp/out.txt");
                List<Content> contents = new ArrayList<>();

                for (Proxy p : proxies) {
                    try {
                        TorrentRutracker rutracker = new TorrentRutracker("SOsipov", "wHRx8", p, "Film");
                        doc = rutracker.getSerchPage(findWord);
                        contents = rutracker.parsPage(doc, flashSize);
                        break;
                    } catch (IOException e) {
                        System.out.println(p);
                        System.out.println(e.getMessage());
                        System.out.println("Exception was processed. Program continues");
                    }

                }

                System.out.println(contents);
                break;
        }
    }}
