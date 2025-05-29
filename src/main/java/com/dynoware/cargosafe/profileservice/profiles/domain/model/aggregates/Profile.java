package com.dynoware.cargosafe.profileservice.profiles.domain.model.aggregates;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.commands.CreateProfileCommand;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.valueobjects.EmailAddress;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.valueobjects.PersonName;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.valueobjects.StreetAddress;
import com.dynoware.cargosafe.profileservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;


/**
 * Profile Aggregate Root
 */
@Entity
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Embedded
    private PersonName name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email_address"))})
    private EmailAddress emailAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "street_address_street")),
            @AttributeOverride(name = "number", column = @Column(name = "street_address_number")),
            @AttributeOverride(name = "city", column = @Column(name = "street_address_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "street_address_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "street_address_country"))})
    private StreetAddress streetAddress;
    @Column(name = "user_id")
    private Long userId;
    public Long getUser() {
        return userId;
    }

    public void setUser(Long user) {
        this.userId = user;
    }
    public Profile(String firstName, String lastName, String email, String street, String number, String city, String postalCode, String country) {
        this.name = new PersonName(firstName, lastName);
        this.emailAddress = new EmailAddress(email);
        this.streetAddress = new StreetAddress(street, number, city, postalCode, country);
    }


    public Profile() {}

    public Profile(CreateProfileCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.emailAddress = new EmailAddress(command.email());
        this.streetAddress = new StreetAddress(command.street(), command.number(), command.city(), command.postalCode(), command.country());
    }

    public String getFullName() {
        return name.getFullName();
    }

    public String getEmailAddress() {
        return emailAddress.address();
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    public void updateName(String firstName, String lastName) {
        this.name = new PersonName(firstName, lastName);
    }

    public void updateEmailAddress(String email) {
        this.emailAddress = new EmailAddress(email);
    }

    public void updateProfile(String firstName, String lastName, String email, String street, String number, String city, String postalCode, String country) {
        this.name = new PersonName(firstName, lastName);
        this.emailAddress = new EmailAddress(email);
        this.streetAddress = new StreetAddress(street, number, city, postalCode, country);
    }
}