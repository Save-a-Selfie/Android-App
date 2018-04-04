package com.imsearch.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by deadcode on 18/11/2017.
 */

public class Person implements Serializable {

    String id;
    Credential credential;
    Contact contact;
    Address address;
    private ArrayList<String> scans;

    public Person(Credential credential, Contact contact, Address address) {
        this.credential = credential;
        this.contact = contact;
        this.address = address;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Credential getCredential() {
        return credential;
    }

    public Contact getContact() {
        return contact;
    }

    public Address getAddress() {
        return address;
    }

    public ArrayList<String> getScans() {
        return scans;
    }

    public void setScans(ArrayList<String> scans) {
        this.scans = scans;
    }
}
