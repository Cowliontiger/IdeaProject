package com.test.dao;

import com.test.entities.FiddlerTemplate;
import com.test.entities.Persons;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FiddlerTemplateRepository extends JpaRepository<FiddlerTemplate, Long> {
    public FiddlerTemplate findByRequestMethodAndFullUrlAndHeadAndResponseData(String requestMethod,String fullUrl,String head,String responseData);

}
