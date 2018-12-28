package app.packet.response;

import app.packet.error.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonMessage<T> {
    boolean success;
    int errorCode;
    String errorMessage;
    String token;
    T data;
    public static CommonMessage failure(ErrorEnum error){
        return new CommonMessage<>(false, error.getCode(), error.getMessage(), null, null);
    }
    public static CommonMessage failure(String message){
        return new CommonMessage<>(false, -1, message, null, null);
    }

    public static <T>CommonMessage<T> success(T data, String token) {
        return new CommonMessage<>(true, 0, "success", token, data);
    }
    public static <T>CommonMessage<T> success(T data) {
        return new CommonMessage<>(true, 0, "success", null, data);
    }
    public static <T>CommonMessage<T> success() {
        return new CommonMessage<>(true, 0, "success", null, null);
    }
}
