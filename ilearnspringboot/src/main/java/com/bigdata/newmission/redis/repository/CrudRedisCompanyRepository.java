package com.bigdata.newmission.redis.repository;

import com.bigdata.newmission.redis.model.RedisCompany;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudRedisCompanyRepository extends CrudRepository<RedisCompany, String> {
}
