package Proxy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class ProxyParser {
    @Getter
    String regex;
    @Getter
    String[] regexes = {
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5]):([8]?[0]?[8][0])",
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\s([0-9]?[0-9]?[0-9]?[0-9][0-9])"

    };

    public ProxyParser(int regexID) {
        regex = regexes[regexID];
    }

    public List<String> parseByUrl(List<String> urls) {
        Pattern patternIP = Pattern.compile(regex);
        return urls.parallelStream().map(url ->{
            try {
                return Jsoup.connect(url).get();
            } catch (IOException e) {
                return null;
            }
        })
                .filter(Objects::nonNull)
                .map(Element::html)
                .flatMap(text -> patternIP.matcher(text).results())
                .map(MatchResult::group)
                .distinct()
                .map(text -> text + "\tHTTP")
                .collect(Collectors.toList());
    }

}

