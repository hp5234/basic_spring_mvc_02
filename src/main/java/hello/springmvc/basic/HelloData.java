package hello.springmvc.basic;

import lombok.Data;

@Data // 롬복에 의해 @Getter , @Setter , @ToString , @EqualsAndHashCode , @RequiredArgsConstructor 자동 적용
public class HelloData {

    private String username;
    private int age;

}
