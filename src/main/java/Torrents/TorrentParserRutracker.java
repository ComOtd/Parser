package Torrents;

import Entity.Content;
import Utils.FileProcessor;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static Proxy.ProxyUtil.proxyFileToList;


@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
enum ContentType {
    Film("https://rutracker.org/forum/tracker.php?f=313&nm=");
    @Getter
    String title;
}

public class TorrentParserRutracker extends TorrentParserImp {
    
    public TorrentParserRutracker(String LOGIN, String PASSWORD, String content) {
        super(LOGIN, PASSWORD, content);
    }

    @Override
    public Map<String, String> getLoginCookies() {
        Connection.Response response = null;
        List<Proxy> proxies = proxyFileToList("src/tmp/out.txt");
        for (Proxy proxy : proxies) {
           try {
                response = Jsoup.connect("https://rutracker.org/forum/login.php")
                       .proxy(proxy)
                       .referrer("https://rutracker.org/forum/login.php")
                       .data("login_username", getLOGIN())
                       .data("login_password", getPASSWORD())
                       .data("login", "")
                       .method(Connection.Method.POST)
                       .execute();
                        break;
           }catch (IOException e){System.out.println(proxy);
                System.out.println(e.getMessage());
                System.out.println("Exception was processed. Program getLoginCookies continues");
                FileProcessor.removeLine(proxy.toString(),"src/tmp/out.txt");

           }
        }
        return Objects.requireNonNull(response).cookies();
    }

    @Override
    public Document getSerchPage(String findWord, Map<String, String> cookies) {
        Document doc = null;
        List<Proxy> proxies = proxyFileToList("src/tmp/out.txt");
        for (Proxy proxy :proxies) {
            try {
        String url = ContentType.valueOf(getContent()).getTitle() + findWord;
                doc = Jsoup.connect(url)
                .proxy(proxy)
                .cookies(cookies)
                .get();
                break;
            }catch (IOException e){System.out.println(proxy);
                System.out.println(e.getMessage());
                System.out.println("Exception was processed. Program continues");
                FileProcessor.removeLine(proxy.toString(),"src/tmp/out.txt");
            }
        }return doc;
    }
    
    @Override
    public List<Content> parsPage(Document doc, double maxSize) {
        Elements elements = doc.select("tr[class = tCenter hl-tr]");
        List<Content> contents = new ArrayList<>();
        for(Element element : elements){
            String name = element.select("a[class = med tLink hl-tags bold]").text();
            name = name.substring(0, name.indexOf("]")+1);
            String fsize = element.select("a[class = small tr-dl dl-stub]").text()
                    .replaceAll("↓|[a-zA-Z]+", "");
            String link = "https://rutracker.org/forum/" + element.select("a[class = med tLink hl-tags bold]")
                    .attr("href");
            if (fsize.isEmpty()|name.isEmpty()|link.isEmpty()) continue;
            double size = Double.parseDouble(fsize);
            if (size < maxSize) contents.add(new Content(name, size, link, getContent()));
        }
        return contents;
    }
}
