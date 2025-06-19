package com.kimsong.digital_banking.shared.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private int status = 0;
    private String message = "SUCCESS";

    public static ResponseDTO success(){
        ResponseDTO responseDto = new ResponseDTO();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setMessage("SUCCESS");
        return responseDto;
    }

    public static ResponseDTO create(){
        ResponseDTO responseDto = new ResponseDTO();
        responseDto.setStatus(HttpStatus.CREATED.value());
        responseDto.setMessage(HttpStatus.CREATED.name());
        return responseDto;
    }

    public static ResponseDTO update(){
        ResponseDTO responseDto = new ResponseDTO();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setMessage("UPDATED");
        return responseDto;
    }

    public static ResponseDTO delete(){
        ResponseDTO responseDto = new ResponseDTO();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setMessage("DELETED");
        return responseDto;
    }

}
