package com.Medical_Recirds2.MEdical.controller;



import com.Medical_Recirds2.MEdical.model.Disease;
import com.Medical_Recirds2.MEdical.service.DiseaseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diseases")
public class DiseaserecordController {

    @Autowired
    private DiseaseRecordService diseaseRecordService;



    @GetMapping("/getAll")
    public List<Disease> getAllDiseases(){
        return  diseaseRecordService.getAllDiseases();
    }

    @PostMapping("/add")
    public String addDisease(@RequestBody Disease disease){
        diseaseRecordService.addDisease(disease);
        return  "disease added succesfully";
    }

//    @PostMapping("/add")
//    public ResponseEntity<Disease> addDisease(@RequestBody Disease disease) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(diseaseRecordService.addDisease(disease));
//    }
}
