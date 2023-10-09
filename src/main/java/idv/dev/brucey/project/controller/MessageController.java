package idv.dev.brucey.project.controller;

import idv.dev.brucey.project.dto.MessageDTO;
import idv.dev.brucey.project.dto.ResponseDTO;
import idv.dev.brucey.project.enumeration.ResultStatusCode;
import idv.dev.brucey.project.service.MessageService;
import idv.dev.brucey.project.utils.ResponseDTOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableAsync
@RestController
@RequestMapping("/messageAPI")
public class MessageController {

    @Autowired MessageService messageService;

    protected final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @RequestMapping(value = "/line/sendMessage", method = RequestMethod.POST)
    public ResponseDTO sendLineMessage() {
        try {
            messageService.sendLineMessage(new MessageDTO());
        } catch (Exception e) {
            logger.error("exception in sendLineMessage method: ", e.getMessage());

            return ResponseDTOUtils.error(ResultStatusCode.SEND_MESSAGE_FAILURE.getCode(),
                    ResultStatusCode.SEND_MESSAGE_FAILURE.getMsg());
        }
        return ResponseDTOUtils.success();
    }
}
