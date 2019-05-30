package Torrents;

import Entity.Content;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
@AllArgsConstructor
public abstract class TorrentParserImp{
    @Getter
    String LOGIN;
    @Getter
    String PASSWORD;
    @Getter
    String content;

    public abstract Map<String,String> getLoginCookies() throws IOException;
    public abstract Document getSerchPage(String findWord, Map<String, String> cookies)throws IOException;
    public abstract List<Content> parsPage(Document doc, double maxSize);
}
