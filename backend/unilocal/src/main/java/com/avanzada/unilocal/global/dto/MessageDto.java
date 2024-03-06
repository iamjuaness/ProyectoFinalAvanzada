package com.avanzada.unilocal.global.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class MessageDto {

    private HttpStatus status;
    private String message;


    public MessageDto() {
    }

    public MessageDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
