package com.hasanalay.banking.controller;

import com.hasanalay.banking.dto.AccountDto;
import com.hasanalay.banking.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {
    private IAccountService accountService;

    //region Requests
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto){
        AccountDto account = accountService.createAccount(accountDto);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") Long accountId){
        AccountDto account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }
    // Deposit Request V1
    @PutMapping("{id}/deposit/{deposit}")
    public ResponseEntity<AccountDto> deposit(@PathVariable("id") Long accountId, @PathVariable("deposit") Double amount){
        AccountDto account = accountService.deposit(accountId, amount);
        return ResponseEntity.ok(account);
    }

    // Deposit Request V2
    @PutMapping("{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable("id") Long accountId, @RequestBody Map<String, Double> request){
        AccountDto account = accountService.deposit(accountId, request.get("amount"));
        return ResponseEntity.ok(account);
    }
    // Withdraw Request V1
    @PutMapping("{id}/withdraw/{withdraw}")
    public ResponseEntity<AccountDto> withdraw(@PathVariable("id") Long accountId, @PathVariable("withdraw") Double amount){
        AccountDto account = accountService.withdraw(accountId, amount);
        return ResponseEntity.ok(account);
    }

    // Withdraw Request V2
    @PutMapping("{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable("id") Long accountId, @RequestBody Map<String, Double> request){
        AccountDto account = accountService.withdraw(accountId, request.get("amount"));
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long accountId){
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok("The account with id:" +accountId+ " deleted successfully!");
    }

    @PutMapping("{id}/sendMoney")
    public ResponseEntity<AccountDto> sendMoney(@PathVariable("id") Long senderAccountId, @RequestBody Map<String, Object> request){
        AccountDto account = accountService.sendMoney(senderAccountId, Long.valueOf(request.get("receiverAccountId").toString()), Double.valueOf(request.get("amount").toString()));
        return ResponseEntity.ok(account);
    }

    //endregion
}
