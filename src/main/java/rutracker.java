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
                    InetSocketAddress.createUnresolved("35.222.1.33", 8080)
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
            double flashSize = 14;


            Document doc = Jsoup.connect(url)
                    .proxy(proxy)
                    .cookies(response.cookies())
                    .get();

            ;
            Elements elements = doc.getElementsByClass("tCenter hl-tr");
            System.out.println(elements);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            List<Film> films = new ArrayList<>();

           for (Element element : elements) {
               String fname = (element.select("a[class = med tLink hl-tags bold]").text());
               String fsize = (element.select("a[class = small tr-dl dl-stub]").text().replace("↓",""));
               String link = ("https://rutracker.org/forum/"+element.select("a[class = med tLink hl-tags bold]").attr("href"));
               double size  =  Double.parseDouble(fsize.replaceAll("[a-zA-Z]+",""));
               if(size < flashSize) films.add(new Film(fname, fsize, link));
           }
            System.out.println(films);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



 