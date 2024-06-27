package com.hasanalay.banking.service;

import com.hasanalay.banking.dto.AccountDto;

import java.util.List;

public interface IAccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long accountId);
    AccountDto deposit(Long accountId, Double amount);
    AccountDto withdraw(Long accountId, Double amount);
    List<AccountDto> getAllAccounts();
    void deleteAccount(Long accountId);
}
