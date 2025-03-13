package com.korit.pawsmarket.global.response;

import com.korit.pawsmarket.global.response.enums.ResponseCode;
import lombok.Getter;

@Getter
public class Response<T> {

    private ResponseCode code;
    private T data;

    public Response(ResponseCode responseCode, T message) {
        this.code = responseCode;
        this.data = message;
    }
}
