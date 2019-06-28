package com.multiledger.backendsdk.repository;

import com.multiledger.backendsdk.document.UserContext;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserContext, String> {



}
