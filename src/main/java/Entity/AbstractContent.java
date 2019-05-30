package Entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
abstract class AbstractContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    Long id;
    @Getter
    @Setter
    String name;
    @Getter
    @Setter
    double size;
    @Getter
    @Setter
    String link;
    @Getter
    @Setter
    String content;

    public AbstractContent(String name, double size, String link, String content) {
        this.name = name;
        this.size = size;
        this.link = link;
        this.content = content;
    }

    @Override
    public String toString() {
        return "AbstractContent{" +
                "id=" + id +
                ", name = '" + name + '\'' +
                ", size = " + size +
                ", link = '" + link + '\'' +
                ", content = '" + content + '\'' +
                "}\n";
    }
}