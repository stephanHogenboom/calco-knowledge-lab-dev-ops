package masterclassers.model;


public class Address {
    private String country;
    private String kixCode;
    private String street;
    private int houseNumber;
    private String extension;
    private String postalCode;
    private String city;

    public Address() {}

    public Address(String country, String kixCode, String street, int houseNumber, String extension, String postalCode,
                   String city) {
        this.country = country;
        this.kixCode = kixCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.extension = extension;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setKixCode(String kixCode) {
        this.kixCode = kixCode;
    }

    public String getKixCode() {
        return kixCode;
    }


    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", kixCode='" + kixCode + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", extension='" + extension + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
