package idv.dev.brucey.project.dto;

import lombok.*;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    // https://notify-bot.line.me/doc/en/
    private String message;
    private String imageThumbnail;
    private String imageFullsize;
    private String imageFile;
    private int stickerPackageId;
    private int stickerId;
    private boolean notificationDisabled;
}
