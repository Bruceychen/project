package idv.dev.brucey.project.utils;

import idv.dev.brucey.project.dto.ResponseDTO;
import idv.dev.brucey.project.enumeration.ResultStatusCode;

public class ResponseDTOUtils {

    public static ResponseDTO success(Object object) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatusCode(ResultStatusCode.OK.getCode());
        responseDTO.setMessage(ResultStatusCode.OK.getMsg());
        responseDTO.setDataObj(object);
        return responseDTO;
    }

    public static ResponseDTO deleteSuccess() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatusCode(ResultStatusCode.DELETE_SUCCESS.getCode());
        responseDTO.setMessage(ResultStatusCode.DELETE_SUCCESS.getMsg());
        responseDTO.setDataObj("");
        return responseDTO;
    }

    public static ResponseDTO success() {
        return success(null);
    }

    public static ResponseDTO error(Integer code, String mesage) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatusCode(code);
        responseDTO.setMessage(mesage);
        return responseDTO;
    }

    public static ResponseDTO error(Integer code, String mesage, Object object) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatusCode(code);
        responseDTO.setMessage(mesage);
        responseDTO.setDataObj(object);
        return responseDTO;
    }
}
