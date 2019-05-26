import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @Getter
    @Setter
    private  String name;

    @Getter
    @Setter
    private  double size;

    @Getter
    @Setter
    private String link;

}
