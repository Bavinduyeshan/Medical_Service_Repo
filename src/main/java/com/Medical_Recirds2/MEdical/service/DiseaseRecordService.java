package com.Medical_Recirds2.MEdical.service;



import  com.Medical_Recirds2.MEdical.repositery.DiseaseRepositery;
import  com.Medical_Recirds2.MEdical.model.Disease;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseRecordService {


    @Autowired
    private DiseaseRepositery diseaseRepositery;

    public List<Disease> getAllDiseases(){
        return  diseaseRepositery.findAll();
    }

    @Transactional
    public Disease addDisease(Disease disease){
        return diseaseRepositery.save(disease);
    }

}
