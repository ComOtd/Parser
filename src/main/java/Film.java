import org.jsoup.nodes.Element;

public class Film {
    private  String name;
    private  String size;
    private String link;

    public Film() {
    }

    public Film(String name, String size, String link) {
        this.name = name;
        this.size = size;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Film{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", link='" + link + '\'' +
                "}\n";
    }
}
