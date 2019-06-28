package com.multiledger.backendsdk.document;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
public class UserContext implements User {

    @Id
    private String id;

    protected String name;
    protected Set<String> roles;
    protected String account;
    protected String affiliation;
    protected Enrollment enrollment;
    protected String mspId;


    public UserContext(String name, String affiliation, String mspId) {
        this.name = name;
        this.affiliation = affiliation;
        this.mspId = mspId;
    }

    public UserContext(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public UserContext() {
    }



    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    @Override
    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    @Override
    public String getMspId() {
        return mspId;
    }

    public void setMspId(String mspId) {
        this.mspId = mspId;
    }


}