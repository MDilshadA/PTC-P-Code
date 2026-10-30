package com.zeeshan.account.mapper;

import com.zeeshan.account.dto.AccountsDto;
import com.zeeshan.account.entity.Accounts;

public class AccountMapper {

    public static AccountsDto mapToAccountDto(Accounts accounts , AccountsDto accountsDto){
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts mapToAccount(Accounts accounts , AccountsDto accountsDto){
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
