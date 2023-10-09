package idv.dev.brucey.project.service;

import idv.dev.brucey.project.dto.MessageDTO;

public interface MessageService {
    void sendLineMessage(MessageDTO messageDTO) throws Exception;
    void sendLineMessageMIMEBug(MessageDTO messageDTO) throws Exception;
}
