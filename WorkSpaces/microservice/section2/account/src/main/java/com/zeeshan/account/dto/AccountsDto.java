package com.zeeshan.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)

public class AccountsDto {

    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    @Schema(
            description = "Account Number of Yono Bank account", example = "3454433243"
    )
    private Long accountNumber;


    @NotEmpty(message = "AccountType can not be a null or empty")
    @Schema(
            description = "Account type of Yono Bank account", example = "Savings"
    )
    private String accountType;

    @Schema(
            description = "Yono Bank branch address", example = "123 Viman Nagar, Pune"
    )
    
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;
}