import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Pattern;

public class ProxyParser {




    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://getfreeproxylists.blogspot.com/")
                    .method(Connection.Method.GET)
                    .get();
            Element element = doc.select("div [class=post-body entry-content]").first();

            String text = element.html();
            String HTTP = text.substring(text.indexOf("<h2>HTTP</h2>"), text.indexOf("<h2>SOCKS</h2>"))
                    .replaceAll("<h2>HTTP[S]?</h2>|<br>","");

          //System.out.println(HTTP);


            Document doc2 = Jsoup.connect("http://free-proxy-server.net/")
                    .method(Connection.Method.GET)
                    .get();
            Element element2 = doc2.getElementsByTag("article").first();
            text = element2.html();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
            text = text.substring(text.indexOf("Clean Version:"));
            text = text.substring(text.indexOf("<br><br>"), text.indexOf("<br></span></span></pre>"))
                    .replaceAll("<br>","\n");

            System.out.println(text);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
