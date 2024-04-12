package com.everythingin.order.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SuccessOutput {
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
