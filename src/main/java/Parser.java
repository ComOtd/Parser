import Entity.Content;
import Proxy.ProxyParser;
import Proxy.ProxyChecker;
import Torrents.TorrentParserRutracker;
import org.jsoup.nodes.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Parser {
    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String findWord = "";
        String param = "";
            System.out.println("Выберите режим парсера: \n1.ПРОКСИ \n2.ТОРРЕНТ");
        try {
            param = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        double flashSize = 14;
        String content = "Film";

        switch (param) {
            case "1":
                System.out.println("РЕЖИМ ПРОКСИ");
                long startTime = System.currentTimeMillis();

                List<String> urls = new ArrayList<>();
                urls.add("https://getfreeproxylists.blogspot.com/");
                urls.add("http://free-proxy-server.net/");

                ProxyParser proxyParser = new ProxyParser(0);
                ProxyChecker checker = new ProxyChecker("https://rutracker.org");
                List<String> parsedProxy = proxyParser.parseByUrl(urls);
                checker.checkProxyAndSaveToFile(parsedProxy);

                long timeSpent = System.currentTimeMillis() - startTime;
                System.out.println("Программа выполнялась " + (timeSpent/1000) + " секунд");
                break;

            case "2":
                System.out.println("РЕЖИМ ТОРРЕНТ");
                System.out.println("Введите название ФИЛЬМА:");

                try {
                    findWord = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("ПОИСК: " + findWord);

                startTime = System.currentTimeMillis();

                TorrentParserRutracker rutracker = new TorrentParserRutracker("SOsipov", "wHRx8", content);
                Map<String,String> cookies = rutracker.getLoginCookies();
                Document doc = rutracker.getSerchPage(findWord, cookies);
                List<Content> contents = rutracker.parsPage(doc, flashSize);
                System.out.println(contents);

                timeSpent = System.currentTimeMillis() - startTime;
                System.out.println("Программа выполнялась " + (timeSpent/1000) + " секунд");
                break;
            default:
        }
    }}
