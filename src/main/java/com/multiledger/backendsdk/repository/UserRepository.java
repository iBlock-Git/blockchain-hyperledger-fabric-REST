package com.multiledger.backendsdk.repository;

import com.multiledger.backendsdk.document.UserCredential;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserCredential, String> {

}
