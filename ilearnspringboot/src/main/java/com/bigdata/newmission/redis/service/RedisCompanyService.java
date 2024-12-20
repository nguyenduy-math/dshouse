package com.bigdata.newmission.redis.service;


import com.bigdata.newmission.redis.model.RedisCompany;
import com.bigdata.newmission.redis.repository.CrudRedisCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisCompanyService {
    @Autowired
    private CrudRedisCompanyRepository crudRedisCompanyRepository;

    public RedisCompany save(RedisCompany entity){
        return crudRedisCompanyRepository.save(entity);
    }
}
