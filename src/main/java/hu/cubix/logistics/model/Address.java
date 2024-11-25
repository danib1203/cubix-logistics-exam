package hu.cubix.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String countryCode;
    private String city;
    private String street;
    private int zipCode;
    private int houseNumber;
    private long latitude;
    private long longitude;

    public Address(String countryCode, String city, String street, int zipCode, int houseNumber,
                   long latitude, long longitude) {
        this.countryCode = countryCode;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.houseNumber = houseNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Address() {

    }

}
