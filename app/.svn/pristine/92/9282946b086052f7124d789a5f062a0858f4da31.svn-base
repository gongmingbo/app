package app.packet.request;

import lombok.Data;

@Data
public class CommonPagePacket<T> {
    String username;//用户的userId
    String deviceId;
    String token;
    int page;
    int size;
    T data;

    boolean validate(){
        return false;
    }
}
