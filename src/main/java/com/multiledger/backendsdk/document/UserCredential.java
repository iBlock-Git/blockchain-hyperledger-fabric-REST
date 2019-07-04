package com.multiledger.backendsdk.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class UserCredential  {

    @Id
    private Integer id;


    private String key;
    private String cert;
    private String name;

    public UserCredential(Integer id, String name, String key, String cert) {
        this.id = id;
        this.key = key;
        this.cert = cert;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }
}