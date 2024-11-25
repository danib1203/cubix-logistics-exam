package hu.cubix.logistics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AddressDto {

    private Long id;

    @NotNull
    @NotBlank
    private String countryCode;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String street;

    @NotNull
    private int zipCode;

    private int houseNumber;
    private long latitude;
    private long longitude;

}
