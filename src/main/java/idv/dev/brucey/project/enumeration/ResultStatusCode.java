package idv.dev.brucey.project.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ResultStatusCode {
    DELETE_SUCCESS(200, "Delete operation succesfully."),
    OK(200, "Success"),
    SEND_MESSAGE_FAILURE(900, "Send message error.")
    ;

    private Integer code;
    private String msg;

}
