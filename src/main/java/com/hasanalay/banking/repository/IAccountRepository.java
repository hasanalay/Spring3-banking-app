package com.hasanalay.banking.repository;

import com.hasanalay.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account, Long> {
}
