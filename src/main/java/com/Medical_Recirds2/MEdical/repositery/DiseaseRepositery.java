package com.Medical_Recirds2.MEdical.repositery;


import  com.Medical_Recirds2.MEdical.model.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiseaseRepositery extends JpaRepository<Disease,Long> {

    Optional <Disease> findByName(String name);

    
}
