package com.community.repository;

import com.community.model.Topic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TopicRepository extends MongoRepository<Topic,String> {
}
