package com.community.repository;

import com.community.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User,String> {

    @Query(fields = "{'password':0}")
    User findByUserNameAndPassword(String userName,String password);

    @Query(fields = "{'password':0}")
    User findByUserId(String userId);
}
