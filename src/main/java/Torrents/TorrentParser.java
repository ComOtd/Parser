package Torrents;

import Entity.Content;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TorrentParser {
    Map<String,String> getLoginCookies() throws IOException;
    Document getSerchPage(String findWord)throws IOException;
    List<Content> parsPage(Document doc, double maxSize);
}
