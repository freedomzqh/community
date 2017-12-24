package com.community.repository;

import com.community.model.Collect;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectRepository extends MongoRepository<Collect ,String> {

}
