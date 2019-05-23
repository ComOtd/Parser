import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class rutracker {

    public static void main(String[] args) {


        try {
            Proxy proxy = new Proxy(
                    Proxy.Type.HTTP,
                    InetSocketAddress.createUnresolved("183.179.199.232", 8080)
            );
            Connection.Response response = Jsoup.connect("https://rutracker.org/forum/login.php")
                    .proxy(proxy)
                    .method(Connection.Method.GET)
                    .execute();

            response = Jsoup.connect("https://rutracker.org/forum/login.php")
                    .proxy(proxy)
                    .referrer("https://rutracker.org/forum/login.php")
                    .data("login_username", "SOsipov")
                    .data("login_password", "wHRx8")
                    .data("login", "")
                    .cookies(response.cookies())
                    .method(Connection.Method.POST)
                    .execute();


            String name = "Миссия%20невыполнима%20Последствия";
            String url = "https://rutracker.org/forum/tracker.php?f=313&nm=" + name;


            Document doc = Jsoup.connect(url)
                    .proxy(proxy)
                    .cookies(response.cookies())
                    .get();
            Elements aElements = doc.select(".tCenter hl-tr");
            List<Film> films = new ArrayList<>();
            for (Element aElement : aElements) {
                Film film = new Film();
                film.setName(aElement);
                film.setSize(aElement);
                film.setLink(aElement);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



 