package idv.dev.brucey.project.service.impl;

import idv.dev.brucey.project.config.HttpUtils;
import idv.dev.brucey.project.dto.MessageDTO;
import idv.dev.brucey.project.service.MessageService;
import idv.dev.brucey.project.utils.StringUtils;
import idv.dev.brucey.project.vo.LineAPIResponseVO;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    protected final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Value("${message.line.token}")
    private String messageLineToken;

    @Value("${message.line.url}")
    private String messageLineUrl;

    @Override
    public void sendLineMessageMIMEBug(MessageDTO messageDTO) throws Exception {
        // Set API sent body
        LineAPIResponseVO responseVO = new LineAPIResponseVO();
        String textMessage = "";
        String timeStamp = "";
        String status = "";


        // Line notification
        // Header: Authorization("Bearer " + token)
        Map<String, String> map = new HashMap<String, String>();
        map.put("Authorization", "Bearer " + messageLineToken);

        // Set Line sent body, %0D%0A = \n

        String text1 = "\"StatusCode\":" + " StatusCode" + ",%0D%0A";
        String text2 = "\"TimeStamp\":" + "now" + ",%0D%0A";
        String text3 = "\"TextMessage\":" + "Text message";


        String resultText = "%0D%0A" + text1 + text2 + text3;

        // String body  = "param1=data1&param2=data2&param3=data3";
        //        String body = "message=" + resultText;
        String body = "message= Test";
        messageDTO.setMessage("Test");
        messageDTO.setStickerPackageId(1);
        messageDTO.setStickerId(113);

        // Line format: application/x-www-form-urlencoded
        HttpUtils.doPostHttpWithHeaderMIMEBug(messageLineUrl, StringUtils.toJson(messageDTO), "UTF-8", ContentType.APPLICATION_JSON.toString(), map);
        //        HttpUtils.doPostHttpWithHeaderNew(postUrl, body, "UTF-8", newContentType, map);
        // Line notification : End
    }

    @Override
    public void sendLineMessage(MessageDTO messageDTO) throws Exception {
        // Set API sent body
        LineAPIResponseVO responseVO = new LineAPIResponseVO();
        String textMessage = "";
        String timeStamp = "";
        String status = "";

        // Line notification

        // Header: Authorization("Bearer " + token)
        Map<String, String> map = new HashMap<String, String>();
        map.put("Authorization", "Bearer " + messageLineToken);

        // String body  = "param1=data1&param2=data2&param3=data3";
        //        String body = "message=" + resultText;
        String body = "message= Test";
        messageDTO.setMessage("Test");
        messageDTO.setStickerPackageId(1);
        messageDTO.setStickerId(113);

        // prep message body
        // Line format: application/x-www-form-urlencoded
        List<NameValuePair> formParams = HttpUtils.createNameValuePairListForBody(messageDTO);
        BasicHeader header = new BasicHeader("Authorization", "Bearer " + messageLineToken);

        HttpUtils.doPostHttpWithHeader(messageLineUrl, formParams, "UTF-8", header);
    }
}
