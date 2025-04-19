package com.Medical_Recirds2.MEdical.service;



import com.Medical_Recirds2.MEdical.model.*;
import com.Medical_Recirds2.MEdical.repositery.DiseaseRepositery;
import com.Medical_Recirds2.MEdical.repositery.MedicalRepositery;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class MedicalRecordService {


    @Autowired
    private MedicalRepositery medicalRepositery;

    @Autowired
    private DiseaseRepositery diseaseRepositery;

    @Autowired
    private PatientServiceClient patientServiceClient;

    @Autowired
    private DoctorServiceClient doctorServiceClient;










    public List<Medical_Records>getAllRecords(){
        return medicalRepositery.findAll();
    }

    public List<Medical_Records> getMedicalRecordsByPatientId(int patientId) {
        return medicalRepositery.findByPatientID(patientId);
    }

//    @Transactional
//    public String MedicalRecord(Medical_Records medicalRecords) {
//        try {
//
//            if (medicalRecords.getPatientID() == null) {
//                throw new RuntimeException("Patient ID cannot be null");
//            }
//            if (medicalRecords.getDoctor_Id()==null){
//                throw new RuntimeException("Doctor id cannot found");
//            }
//
//            // Validate patient existence through the external service
//            try {
//                PatientResponse patientResponse = patientServiceClient.validatePatient(medicalRecords.getPatientID());
//
//            } catch (FeignException.NotFound e) {
//                throw new RuntimeException("Requested Patient ID " + medicalRecords.getPatientID() + " does not exist.");
//            }
//            try {
//
//                DoctorResponse doctorResponse=doctorServiceClient.validateDoctor(medicalRecords.getDoctor_Id());
//            } catch (FeignException.NotFound e) {
//                throw new RuntimeException("Requested Doctor ID " + medicalRecords.getDoctor_Id() + " does not exist.");
//            }
//            // Find the Patient by ID
////            Patient patient = patientRepositery.findById(medicalRecords.getPatientID())
////                    .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + medicalRecords.getPatientID()));
//
//            // Find the Disease by Name
//            Disease disease = diseaseRepositery.findByName(medicalRecords.getName())
//                    .orElseThrow(() -> new RuntimeException("Disease not found with name: " + medicalRecords.getName()));
//
//            // Set the patient and disease to the medical record
//           // medicalRecords.setPatient(patient);
//            medicalRecords.setDisease(disease);
//
//            // Save the medical record to the repository
//            medicalRepositery.save(medicalRecords);
//
//            return "Medical record added successfully";
//        } catch (Exception ex) {
//            // Log the exception for more insight
//            System.err.println("Error occurred: " + ex.getMessage());
//            ex.printStackTrace();
//            throw new RuntimeException("An error occurred while adding the medical record: " + ex.getMessage());
//        }
//    }



    public Medical_Records MedicalRecord(Medical_Records medicalRecords) {
        try {
            if (medicalRecords.getPatientID() == null) {
                throw new RuntimeException("Patient ID cannot be null");
            }
            if (medicalRecords.getDoctor_Id() == null) {
                throw new RuntimeException("Doctor ID cannot be null");
            }

            // Validate patient existence through the external service
            try {
                PatientResponse patientResponse = patientServiceClient.validatePatient(medicalRecords.getPatientID());
            } catch (FeignException.NotFound e) {
                throw new RuntimeException("Requested Patient ID " + medicalRecords.getPatientID() + " does not exist.");
            }

            // Validate doctor existence through the external service
            try {
                DoctorResponse doctorResponse = doctorServiceClient.validateDoctor(medicalRecords.getDoctor_Id());
            } catch (FeignException.NotFound e) {
                throw new RuntimeException("Requested Doctor ID " + medicalRecords.getDoctor_Id() + " does not exist.");
            }

            // Find the Disease by Name
            Disease disease = diseaseRepositery.findByName(medicalRecords.getName())
                    .orElseThrow(() -> new RuntimeException("Disease not found with name: " + medicalRecords.getName()));

            // Set the disease to the medical record
            medicalRecords.setDisease(disease);

            // Save and return the medical record
            return medicalRepositery.save(medicalRecords);

        } catch (Exception ex) {
            System.err.println("Error occurred: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("An error occurred while adding the medical record: " + ex.getMessage());
        }
    }


//    @Transactional
//    public String MedicalRecord(Medical_Records medicalRecords) {
//        try {
//            // Fetch the patient by ID
//            Patient patient = patientRepositery.findById(medicalRecords.getPatientID())
//                    .orElseThrow(() -> new RuntimeException("Patient not found"));
//
//            // Fetch the disease by name
//            Disease disease = diseaseRepositery.findByName(medicalRecords.getName())
//                    .orElseThrow(() -> new RuntimeException("Disease not found"));
//
//            // Set patient and disease in the medical record
//            medicalRecords.setPatient(patient);
//            medicalRecords.setDisease(disease);
//
//            // Save the medical record
//            medicalRepositery.save(medicalRecords);
//
//            return "Medical record added successfully";
//        } catch (Exception ex) {
//            // Log the error and include the exception message
//            System.err.println("Error occurred: " + ex.getMessage());
//            ex.printStackTrace(); // Print stack trace for debugging
//            throw new RuntimeException("An error occurred while adding the medical record: " + ex.getMessage());
//        }
//    }

//    public  Medical_Records addMedicalRecord(Medical_Records records){
//        return medicalRepositery.save(records);
//    }

//    public Medical_Records addMedicalRecord(Long patientId, Long diseaseId, String diagnosticData, String treatments, String reportUrl) {
//        try {
//            // Check if the patient exists
//            Patient patient = patientRepositery.findById(patientId)
//                    .orElseThrow(() -> new RuntimeException("Patient not found"));
//
//            // Check if the disease exists
//            Disease disease = diseaseRepositery.findById(diseaseId)
//                    .orElseThrow(() -> new RuntimeException("Disease not found"));
//
//            // Create a new medical record
//            Medical_Records medicalRecord = new Medical_Records();
//            medicalRecord.setPatient(patient);
//            medicalRecord.setDisease(disease);
//            medicalRecord.setDiagnosticData(diagnosticData);
//            medicalRecord.setTreatments(treatments);
//            medicalRecord.setReportUrl(reportUrl);
//
//            // Save the medical record to the database
//            return medicalRepositery.save(medicalRecord);
//        } catch (Exception e) {
//            // Log the error (optional)
//            System.out.println("Error occurred while adding medical record: " + e.getMessage());
//            throw new RuntimeException("An error occurred while adding the medical record");
//        }
//    }
//public Medical_Records addMedicalRecord(Medical_Records record) {
//    if (record.getId() != null) {
//        throw new IllegalArgumentException("The ID should not be provided for new records.");
//    }
//
//    // Ensure other required fields are present
//    if (record.getPatient().getPatientId() == null || record.getDiagnosticData() == null || record.getTreatments() == null) {
//        throw new IllegalArgumentException("Patient ID, diagnosis, and treatment must be provided.");
//    }
//
//    // Save the record to the database
//    return medicalRepositery.save(record);
//}

//    public  Medical_Records addMedicalRecord(Medical_Records medicalRecords){
//        return medicalRepositery.save(medicalRecords);
//    }



    public String updateMedicalRecors(Long id,Medical_Records medicalRecords){
        Optional<Medical_Records> exsitingrecord=medicalRepositery.findById(id);
        if (exsitingrecord.isPresent()){
            Medical_Records updatemedicalrecord=exsitingrecord.get();

            updatemedicalrecord.setDisease(medicalRecords.getDisease());

            updatemedicalrecord.setDiagnosticData(medicalRecords.getDiagnosticData());
            updatemedicalrecord.setTreatments(medicalRecords.getTreatments());
            updatemedicalrecord.setReportUrl(medicalRecords.getReportUrl());
            updatemedicalrecord.setUpdatedAt(LocalDateTime.now());

            medicalRepositery.save(updatemedicalrecord);
            return "record updated succesfully";
        }
        else{
            throw  new RuntimeException("record not found");
        }


    }

    public  void deleteMedicalrecord(Long id){
         medicalRepositery.deleteById(id);
    }


    public  long medicalCount(){
        return  medicalRepositery.count();
    }

}
