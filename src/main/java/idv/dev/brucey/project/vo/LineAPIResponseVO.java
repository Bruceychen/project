package idv.dev.brucey.project.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LineAPIResponseVO {
    private String textMessage;
    private String timestamp;
    private String statusCode;

}
