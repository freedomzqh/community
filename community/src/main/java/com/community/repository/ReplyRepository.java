package com.community.repository;

import com.community.model.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ReplyRepository extends MongoRepository<Reply,String>{

    List<Reply> findByTopicId(Pageable pageable);
}
