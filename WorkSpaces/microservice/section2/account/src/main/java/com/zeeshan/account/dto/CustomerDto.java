package com.zeeshan.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer", example = "Zeeshan Tech"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5 , max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;


    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digit")
    private String mobileNumber;


    @Schema(
            description = "Email address of the customer", example = "zeeshan@Techno.com"
    )
    @NotEmpty(message = "Email field must net be empty")
    @Email(message = "Email Address must be valid value")
    private String email;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;
}