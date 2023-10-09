package idv.dev.brucey.project.config;

import io.micrometer.common.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private static final int CONNECT_TIMEOUT = 30000;
    private static final int SOCKET_TIMEOUT = 20000;

    public static String doPostHttpWithHeaderMIMEBug(String url, String content, String charset, String contentType, Map<String, String> header) throws IOException {
        logger.info("post address: " + url + ", parameters: " + content);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .build();
            httpPost.setConfig(requestConfig);

            StringEntity reqEntity = new StringEntity(content, ContentType.create(contentType, charset));
            httpPost.setEntity(reqEntity);

            // Set headers
            for (Map.Entry<String, String> entry : header.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    logger.warn("Request failed: statusCode={}", statusCode);
                    HttpEntity repEntity = response.getEntity();
                    if (repEntity != null) {
                        logger.warn("Request response: resp={}", EntityUtils.toString(repEntity, charset));
                    }
                    return "";
                }
                HttpEntity repEntity = response.getEntity();
                String resp = EntityUtils.toString(repEntity, charset);
                return resp;
            } catch (Exception e) {
                // Handle specific HTTP response exceptions
                logger.error("HTTP response error: " + e.getMessage());
                return "";
            }
        }
    }

    public static String doPostHttpWithHeader(String url, List<NameValuePair> formParams, String charset, BasicHeader header) throws IOException {
        logger.info("post address: " + url + ", parameters: " + formParams.toString());
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create an HTTP POST request
            HttpPost httpPost = new HttpPost(url);
            // Set the request entity as form data
            HttpEntity requestEntity = new UrlEncodedFormEntity(formParams, charset);
            httpPost.setEntity(requestEntity);
            // prep header data
            httpPost.addHeader(header);

            // Execute the POST request
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // Get the response status code
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    logger.warn("Request failed: statusCode={}", statusCode);
                    HttpEntity repEntity = response.getEntity();
                    if (repEntity != null) {
                        logger.warn("Request response: resp={}", EntityUtils.toString(repEntity, charset));
                    }
                    return "";
                }
                HttpEntity repEntity = response.getEntity();
                String resp = EntityUtils.toString(repEntity, charset);
                return resp;
            } catch (Exception e) {
                // Handle specific HTTP response exceptions
                logger.error("HTTP response error: " + e.getMessage());
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> List<NameValuePair> createNameValuePairListForBody(T dataDTO) {
        List<NameValuePair> formParams = new ArrayList<>();
        Field[] fields = dataDTO.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true); // Set the field to be accessible
            }

            // Get the field name and value
            String fieldName = field.getName();
            Object value;
            try {
                value = field.get(dataDTO);
            } catch (IllegalAccessException e) {
                logger.error("Error on HttpUtils.createNameValuePairList: " + e.getMessage());
                e.printStackTrace();
                continue;
            }

            // Check if the value is not null and, if it's a string, not empty
            if (value != null && (value instanceof String ? !StringUtils.isBlank((String) value) : true)) {
                formParams.add(new BasicNameValuePair(fieldName, value.toString()));
            }
        }

        return formParams;
    }
}
