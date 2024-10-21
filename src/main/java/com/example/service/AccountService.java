package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository ar;

    public int registerAccount(Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4){
            return 400;
        }
        Optional<Account> optionalAccount = ar.findAccountByUsername(account.getUsername());
        
        if(optionalAccount.isPresent()){
            return 409;
        }
        ar.save(account);

        Optional<Account> insertedAccount = ar.findAccountByUsername(account.getUsername());
        return insertedAccount.isPresent() ? 200 : 400;
        
    }

    public int login(Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4){
            return 401;
        }
        Optional<Account> optionalAccount = ar.findAccountByUsername(account.getUsername());
        if(optionalAccount.isEmpty()){
            return 401;
        }
        Account checkAccount = optionalAccount.get();
        if(checkAccount.getUsername().contentEquals(account.getUsername()) 
        && checkAccount.getPassword().contentEquals(account.getPassword()))
        {
            return 200;
        }

        return 401;
        
    }

    public Optional<Account> getAccount(Account account) {
        Optional<Account> optionalAccount = ar.findAccountByUsername(account.getUsername());
        return optionalAccount.isPresent() ? optionalAccount : null; 
    }
    public Optional<Account> getAccountById(int id) {
        Optional<Account> optionalAccount = ar.findById(id);
        return optionalAccount.isPresent() ? optionalAccount : null; 
    }
    

}
