package com.example.agenda;

public class Contact {

    // private variables
    public int _id;
    public String _name;
    public String _phone_number;
    public String _email;
	private String _address;

    public Contact() {
    }

    // constructor
    public Contact(int id, String name, String _phone_number, String _email, String _address) {
		this._id = id;
		this._name = name;
		this._phone_number = _phone_number;
		this._email = _email;
		this._address = _address;

    }

    // constructor
    public Contact(String name, String _phone_number, String _email, String _address) {
		this._name = name;
		this._phone_number = _phone_number;
		this._email = _email;
		this._address = _address;
    }

    // getting ID
    public int getID() {
    	return this._id;
    }

    // setting id
    public void setID(int id) {
    	this._id = id;
    }

    // getting name
    public String getName() {
    	return this._name;
    }

    // setting name
    public void setName(String name) {
    	this._name = name;
    }

    // getting phone number
    public String getPhoneNumber() {
    	return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
    	this._phone_number = phone_number;
    }

    // getting email
    public String getEmail() {
    	return this._email;
    }

    // setting email
    public void setEmail(String email) {
    	this._email = email;
    }

    // getting address
	public String getAddress() {
		return this._address;
	}
	
	// setting address
	public void setAddress(String address) {
		this._address = address;
	}

}