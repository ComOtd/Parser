import Entity.Content;
import Proxy.ProxyParser;
import Proxy.ProxyChecker;
import Torrents.TorrentParserRutracker;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;



import static Proxy.ProxyUtil.proxyFileToList;

public class Parser {
    public static void main(String[] args) {

        String findWord = "Миссия невыполнима Последствия";
        double flashSize = 50;
        String param = "1";

        switch (param) {
            case "1":
                long startTime = System.currentTimeMillis();

                List<String> urls = new ArrayList<>();
                urls.add("https://getfreeproxylists.blogspot.com/");
                urls.add("http://free-proxy-server.net/");

                ProxyParser proxyParser = new ProxyParser(0);
                ProxyChecker checker = new ProxyChecker("https://rutracker.org");
                checker.checkProxyAndSaveToFile(proxyParser.parseByUrl(urls));

                long timeSpent = System.currentTimeMillis() - startTime;
                System.out.println("программа выполнялась " + (timeSpent/1000) + " секунд");
                break;

            case "2":
                startTime = System.currentTimeMillis();
                List<Proxy> proxies = proxyFileToList("src/tmp/out.txt");
                for (Proxy p : proxies) {
                    try {
                        TorrentParserRutracker rutracker = new TorrentParserRutracker("SOsipov", "wHRx8", p, "Film");
                        Document doc = rutracker.getSerchPage(findWord);
                        List<Content> contents = rutracker.parsPage(doc, flashSize);
                        System.out.println(contents);
                        break;
                    } catch (IOException e) {
                        System.out.println(p);
                        System.out.println(e.getMessage());
                        System.out.println("Exception was processed. Program continues");
                    }
                }

                timeSpent = System.currentTimeMillis() - startTime;
                System.out.println("программа выполнялась " + (timeSpent/1000) + " секунд");
                break;
            default:

        }
    }}
