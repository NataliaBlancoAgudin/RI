package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Client {
    private String nif;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Address address;

    // atributo accidental
    private Set<Vehicle> vehicles = new HashSet<>();
    private Set<PaymentMean> paymentMeans = new HashSet<>();

    // Constructor reducido
    public Client(String nif, String name, String surname) {
	this(nif, name, surname, "no-email", "no-phone",
	    new Address("no-street", "no-city", "no-zip"));
    }

    // 1. FULL Constructor
    public Client(String nif, String name, String surname, String email,
	String phone, Address address) {
	// 1.1. Validamos
	ArgumentChecks.isNotBlank(nif);
	ArgumentChecks.isNotBlank(name);
	ArgumentChecks.isNotBlank(surname);
	ArgumentChecks.isNotBlank(email);
	ArgumentChecks.isNotBlank(phone);
	ArgumentChecks.isNotNull(address);

	this.nif = nif;
	this.name = name;
	this.surname = surname;
	this.email = email;
	this.phone = phone;
	this.address = address;
    }

    public String getNif() {
	return nif;
    }

    public String getName() {
	return name;
    }

    public String getSurname() {
	return surname;
    }

    public String getEmail() {
	return email;
    }

    public String getPhone() {
	return phone;
    }

    public Address getAddress() {
	return address;
    }

    public Set<Vehicle> getVehicles() {
	return new HashSet<Vehicle>(vehicles);
    }

    Set<Vehicle> _getVehicles() {
	return vehicles;
    }

    public Set<PaymentMean> getPaymentMeans() {
	return new HashSet<PaymentMean>(paymentMeans);
    }

    Set<PaymentMean> _getPaymentMeans() {
	return paymentMeans;
    }

    @Override
    public int hashCode() {
	return Objects.hash(nif);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Client other = (Client) obj;
	return Objects.equals(nif, other.nif);
    }

    @Override
    public String toString() {
	return "Client [nif=" + nif + ", name=" + name + ", surname=" + surname
	    + ", email=" + email + ", phone=" + phone + ", address=" + address
	    + "]";
    }

}
