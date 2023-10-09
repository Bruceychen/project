package idv.dev.brucey.project.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils {
    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);
    private static final Gson localGson = new Gson();

    public static String toJson(Object object) {
        return localGson.toJson(object);
    }
}
