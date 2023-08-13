package com.webflux.userservice.dto.response;

import com.webflux.userservice.dto.enums.TransactionStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionResponseDto {

    private Integer userId;
    private Integer amount;
    private TransactionStatus status;

}
