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
    
    public TorrentParserRutracker(String LOGIN, String PASSWORD, Proxy proxy, String content) {
        super(LOGIN, PASSWORD, proxy, content);
    }

    @Override
    public Map<String, String> getLoginCookies() throws IOException {
        Connection.Response response  = Jsoup.connect("https://rutracker.org/forum/login.php")
                .proxy(getProxy())
                .referrer("https://rutracker.org/forum/login.php")
                .data("login_username", getLOGIN())
                .data("login_password", getPASSWORD())
                .data("login", "")
                .method(Connection.Method.POST)
                .execute();
        return response.cookies();
    }

    @Override
    public Document getSerchPage(String findWord) throws IOException {
        String url = ContentType.valueOf(getContent()).getTitle() + findWord;
        return Jsoup.connect(url)
                .proxy(getProxy())
                .cookies(getLoginCookies())
                .get();
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
