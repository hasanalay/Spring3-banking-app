package com.hasanalay.banking.mapper;

import com.hasanalay.banking.dto.AccountDto;
import com.hasanalay.banking.entity.Account;

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto){
        return new Account(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance(),
                accountDto.getAccountNumber(),
                accountDto.getHasExtraAcc()
        );
    }

    public static AccountDto mapToAccountDto(Account account){
        return new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance(),
                account.getAccountNumber(),
                account.getHasExtraAcc()
        );
    }
}
