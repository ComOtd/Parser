package Proxy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class ProxyParser {
    @Getter
    String regex;
    @Getter
    String[] regexes = {
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5]):([0-9]?[0-9]?[0-9]?[0-9][0-9])",
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\s([0-9]?[0-9]?[0-9]?[0-9][0-9])"

    };

    public ProxyParser(int regexID) {
        regex = regexes[regexID];
    }


    public List<String> parseByUrl(String url) {
        List<String> ip = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .get();
            String text = doc.html();
            Pattern patternIP = Pattern.compile(regex);
            Matcher matcher = patternIP.matcher(text);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                String line = text.substring(start, end) + "\tHTTP";
                ip.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ip;
    }
}

