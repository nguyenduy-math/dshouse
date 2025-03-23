package com.bigdata.pubsub.resolver;

import com.bigdata.pubsub.model.Companies;
import com.bigdata.pubsub.repository.CompaniesRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CompaniesQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private CompaniesRepository companiesRepository;

    public List<Companies> getAllModels() {
        return companiesRepository.findAll();
    }

    public Optional<Companies> getModelById(String id) {
        return companiesRepository.findById(id);
    }
}
