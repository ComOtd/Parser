package Torrents;

import Entity.Content;
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


@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
enum ContentType {
    Film("https://rutracker.org/forum/tracker.php?f=313&nm=");
    @Getter
    String title;
}

public class TorrentParserRutracker extends TorrentParserImp {
    
    public TorrentParserRutracker(String LOGIN, String PASSWORD, List<Proxy> proxy, String content) {
        super(LOGIN, PASSWORD, proxy, content);
    }

    @Override
    public Map<String, String> getLoginCookies() {
        Connection.Response response = null;
        for (Proxy proxy : getProxy()) {
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
               System.out.println("Exception was processed. Program continues");
           }
        }
        return response.cookies();
    }

    @Override
    public Document getSerchPage(String findWord) {
        Document doc = null;
        for (Proxy proxy : getProxy()) {
            try {
        String url = ContentType.valueOf(getContent()).getTitle() + findWord;
                doc = Jsoup.connect(url)
                .proxy(proxy)
                .cookies(getLoginCookies())
                .get();
                break;
            }catch (IOException e){System.out.println(proxy);
                System.out.println(e.getMessage());
                System.out.println("Exception was processed. Program continues");
            }
        }return doc;
    }
    
    @Override
    public List<Content> parsPage(Document doc, double maxSize) {
        Elements elements = doc.select("tr[class = tCenter hl-tr]");
        List<Content> contents = new ArrayList<>();
        for(Element element : elements){
            String name = element.select("a[class = med tLink hl-tags bold]").text();
            String fsize = element.select("a[class = small tr-dl dl-stub]").text()
                    .replaceAll("↓|[a-zA-Z]+", "");
            String link = "https://rutracker.org/forum/" + element.select("a[class = med tLink hl-tags bold]")
                    .attr("href");
            if (fsize.isEmpty()) continue;
            double size = Double.parseDouble(fsize);
            if (size < maxSize) contents.add(new Content(name, size, link, getContent()));
        }
        return contents;
    }
}
