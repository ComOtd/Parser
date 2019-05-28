package Torrents;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Torrent<K> {
    Map<String,String> getLoginCookies() throws IOException;
    Document getSerchPage(String findWord)throws IOException;
    List<K> parsPage(Document doc, double maxSize);
}
