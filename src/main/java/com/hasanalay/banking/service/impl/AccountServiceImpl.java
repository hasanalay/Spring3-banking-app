package com.hasanalay.banking.service.impl;

import com.hasanalay.banking.dto.AccountDto;
import com.hasanalay.banking.entity.Account;
import com.hasanalay.banking.exception.ResourceNotFoundException;
import com.hasanalay.banking.mapper.AccountMapper;
import com.hasanalay.banking.repository.IAccountRepository;
import com.hasanalay.banking.service.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepository accountRepository;

    public AccountServiceImpl(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        accountDto.setAccountNumber(generateAccountNumber());
        accountDto.setHasExtraAcc(false);
        Account account = AccountMapper.mapToAccount(accountDto);
        accountRepository.save(account);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new ResourceNotFoundException("Account with the given id: "+ accountId +" does not exists!"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new ResourceNotFoundException("Account with the given id: "+ accountId +" does not exists!"));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto withdraw(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new ResourceNotFoundException("Account with the given id: "+ accountId +" does not exists!"));
        if (account.getBalance() >= amount || account.getHasExtraAcc()){
            account.setBalance(account.getBalance() - amount);
        }
        else {
            throw new ResourceNotFoundException("Sorry your balance: " + account.getBalance() +" is not enough to withdraw that amount:" + amount);
        }

        accountRepository.save(account);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
        //(account) -> AccountMapper.mapToAccountDto(account) can be used for AccountMapper::mapToAccountDto
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new ResourceNotFoundException("Account with the given id: "+ accountId +" does not exists!"));
        accountRepository.deleteById(accountId);
    }

    @Override
    public AccountDto sendMoney( Long senderAccountId, Long receiverAccountId, Double amount) {
        Account senderAccount = accountRepository.findById(senderAccountId).orElseThrow(
                () -> new ResourceNotFoundException("Account with the given id: "+ senderAccountId +" does not exists!"));
        Account receiverAccount = accountRepository.findById(receiverAccountId).orElseThrow(
                () -> new ResourceNotFoundException("Account with the given id: "+ receiverAccountId +" does not exists!"));
        if (senderAccount.getBalance() >= amount || senderAccount.getHasExtraAcc()){
            senderAccount.setBalance(senderAccount.getBalance() - amount);
            receiverAccount.setBalance(receiverAccount.getBalance() + amount);
            accountRepository.save(senderAccount);
            accountRepository.save(receiverAccount);
        }
        else {
            throw new ResourceNotFoundException("Sorry your balance: " + senderAccount.getBalance() +" is not enough to withdraw that amount:" + amount);
        }
        return AccountMapper.mapToAccountDto(senderAccount);
    }

    public static Integer generateAccountNumber() {
        Random random = new Random();
        return random.nextInt(90000) + 10000;
    }
}
