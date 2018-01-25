package com.stephanHogenboom.model;

public class AddressBuilder {

    private String country;
    private String kixCode;
    private String street;
    private int houseNumber;
    private String extension;
    private String postalCode;
    private String city;

    public AddressBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public AddressBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public AddressBuilder setExtension(String extension) {
        this.extension = extension;
        return this;
    }

    public AddressBuilder setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }


    public AddressBuilder setKixCode() {
        this.kixCode = String.format("%s%s%sx%s", this.country, this.postalCode, makeKixCodeHouseNumber(this.houseNumber), this.extension );
        return this;
    }

    public AddressBuilder setCity(String city) {
        this.city = city;
        return this;
    }



    public Address build() {
        return new Address(
                this.country,
                this.kixCode,
                this.street,
                this.houseNumber,
                this.extension,
                this.postalCode,
                this.city
        );
    }


    private String makeKixCodeHouseNumber(int houseNumber) {
        String kixHouseNumber = "";
        String houseNumberString = String.valueOf(houseNumber);
        if (houseNumberString.length() == 1) kixHouseNumber = "000".concat(houseNumberString);
        if (houseNumberString.length() == 2) kixHouseNumber = "00".concat(houseNumberString);
        if (houseNumberString.length() == 3) kixHouseNumber = "0".concat(houseNumberString);
        if (houseNumberString.length() == 4) kixHouseNumber = houseNumberString;
        if (houseNumberString.length() > 4 || houseNumberString.length() == 0) throw new IllegalArgumentException("housenumber must contain between 1 and 4 digits");  ;
        return kixHouseNumber;
    }

}
