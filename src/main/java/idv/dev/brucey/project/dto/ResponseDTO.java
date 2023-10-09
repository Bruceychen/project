package idv.dev.brucey.project.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
    private Integer statusCode;
    private String message;
    private T dataObj;
}
