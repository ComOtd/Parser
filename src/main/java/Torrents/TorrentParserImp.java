package Torrents;

import Entity.Content;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.Proxy;
import java.util.List;
import java.util.Map;

@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
@AllArgsConstructor
public abstract class TorrentParserImp implements TorrentParser {
    @Getter
    String LOGIN;
    @Getter
    String PASSWORD;
    @Getter
    Proxy proxy;
    @Getter
    String content;

    public abstract Map<String,String> getLoginCookies() throws IOException;
    public abstract Document getSerchPage(String findWord)throws IOException;
    public abstract List<Content> parsPage(Document doc, double maxSize);
}