package com.hasanalay.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String accountHolderName;
    private Double balance;
    private Integer accountNumber;
    private Boolean hasExtraAcc;

}
