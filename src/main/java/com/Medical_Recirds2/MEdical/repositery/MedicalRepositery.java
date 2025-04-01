package com.Medical_Recirds2.MEdical.repositery;


import com.Medical_Recirds2.MEdical.model.Medical_Records;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRepositery extends JpaRepository<Medical_Records,Long> {

    List<Medical_Records> findByPatientID(int patientID);

}
