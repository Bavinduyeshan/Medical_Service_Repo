package com.Medical_Recirds2.MEdical.controller;



import  com.Medical_Recirds2.MEdical.model.Medical_Records;
import com.Medical_Recirds2.MEdical.model.PatientResponse;
import  com.Medical_Recirds2.MEdical.repositery.*;
import  com.Medical_Recirds2.MEdical.service.MedicalRecordService;

import com.Medical_Recirds2.MEdical.service.PatientServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordsController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PatientServiceClient patientServiceClient;


    private  final MedicalRepositery medicalRepositery;
    private  final DiseaseRepositery diseaseRepositery;



    public MedicalRecordsController(MedicalRepositery medicalRepositery, DiseaseRepositery diseaseRepositery) {
        this.medicalRepositery = medicalRepositery;
        this.diseaseRepositery = diseaseRepositery;
    }




    @GetMapping("/getAll")
    public List<Medical_Records> getAllRecords(){
        return medicalRecordService.getAllRecords();
    }



//    @PostMapping("/add")
//    public  String addMedicalRecord(@RequestBody Medical_Records medicalRecords){
//        medicalRecordService.MedicalRecord(medicalRecords);
//        return "medical record added succesfully";
//    }



    //correct down without report serrelcting from pc
//@PostMapping("/add")
//public ResponseEntity<String> addMedicalRecord(@RequestBody Medical_Records medicalRecords) {
//    try {
//        String message = medicalRecordService.MedicalRecord(medicalRecords);
//        return ResponseEntity.ok(message);
//    } catch (Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Error: " + ex.getMessage());
//    }
//}


   // @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Medical_Records> addMedicalRecord(
//            @RequestPart("record") Medical_Records record,
//            @RequestPart(value = "report", required = false) MultipartFile report) throws IOException {
//        if (report != null && !report.isEmpty()) {
//            String fileName = UUID.randomUUID().toString() + "_" + report.getOriginalFilename();
//            File uploadDir = new File("uploads");
//            if (!uploadDir.exists()) {
//                uploadDir.mkdirs();
//            }
//            File destFile = new File(uploadDir, fileName);
//            System.out.println("Saving file to: " + destFile.getAbsolutePath());
//            report.transferTo(destFile);
//            System.out.println("File saved successfully: " + destFile.exists());
//            record.setReportUrl("http://localhost:8081/uploads/" + fileName);
//        }
//
//            // Save and get the actual saved record
//            Medical_Records savedRecord = medicalRecordService.MedicalRecord(record);
//            return ResponseEntity.ok(savedRecord);
//
//
//    }


    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<Medical_Records> createMedicalRecord(
            @RequestPart("record") Medical_Records record,
            @RequestPart(value = "report", required = false) MultipartFile report) throws IOException {
        if (report != null && !report.isEmpty()) {
            // Define an absolute path for the uploads directory
            String uploadDirPath = "E:/Digital health id system/MEdical/src/main/resources/uploads";            File uploadDir = new File(uploadDirPath);

            // Ensure the directory exists
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                if (!created) {
                    throw new IOException("Failed to create upload directory: " + uploadDirPath);
                }
            }

            // Generate a unique filename
            String fileName = UUID.randomUUID().toString() + "_" + report.getOriginalFilename();
            File destFile = new File(uploadDir, fileName);

            // Log the save operation
            System.out.println("Saving file to: " + destFile.getAbsolutePath());

            // Transfer the file
            report.transferTo(destFile);

            // Verify the file was saved
            if (destFile.exists()) {
                System.out.println("File saved successfully: " + destFile.getAbsolutePath() + ", Size: " + destFile.length() + " bytes");
            } else {
                throw new IOException("File transfer failed: " + destFile.getAbsolutePath());
            }

            // Set the report URL
            record.setReportUrl("/uploads/" + fileName);
        }
        Medical_Records savedRecord = medicalRecordService.MedicalRecord(record);
        return ResponseEntity.ok(savedRecord);
    }

    @GetMapping("/records/{patientId}")
    public PatientResponse getPatientMedicalData(@PathVariable int patientId) {
        // Validate patient existence via Patient Service
        PatientResponse patient = patientServiceClient.getPatientData(patientId);

        if (patient == null) {
            throw new RuntimeException("Patient with ID " + patientId + " not found.");
        }

        // Fetch medical records from repository
        List<Medical_Records> records = medicalRepositery.findByPatientID(patientId);

        // Attach medical records to PatientResponse
        patient.setMedicalRecords(records);

        return patient;
    }

    @GetMapping("/medical-records/{patientId}")
    public List<Medical_Records> getMedicalRecordsOnly(@PathVariable int patientId) {
        // Fetch medical records directly without patient details
        List<Medical_Records> records = medicalRepositery.findByPatientID(patientId);

        if (records.isEmpty()) {
            throw new RuntimeException("No medical records found for Patient ID: " + patientId);
        }

        return records; // ✅ Now it returns ONLY the list of medical records
    }


//    @PostMapping("/add")
//    public ResponseEntity<?> addMedicalRecord(@RequestBody Medical_Records medicalRecords) {
//        try {
//            // Call the service method to add the medical record
//            Medical_Records savedMedicalRecord = medicalRecordService.addMedicalRecord(
//                    medicalRecords.getPatient().getPatientId(),
//                    medicalRecords.getDisease().getId(),
//                    medicalRecords.getDiagnosticData(),
//                    medicalRecords.getTreatments(),
//                    medicalRecords.getReportUrl()
//            );
//
//            // Return the created medical record as the response
//            return ResponseEntity.ok(savedMedicalRecord);
//        } catch (RuntimeException e) {
//            // Return a 500 error with the exception message
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }
//@PostMapping("/add")
//public ResponseEntity<String> addMedicalRecord(@RequestBody Medical_Records record) {
//    try {
//        medicalRecordService.addMedicalRecord(record);
//        return ResponseEntity.ok("Medical record added successfully");
//    } catch (IllegalArgumentException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body("Error: " + e.getMessage());
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Error: An error occurred while adding the medical record");
//    }
//}




//@PostMapping("/add")
//public ResponseEntity<Medical_Records> addMedicalRecord(@RequestBody Medical_Records record) {
//    return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordService.addMedicalRecord(record));
//}
//@PostMapping("/add")
//public ResponseEntity<Medical_Records> addMedicalRecord(@RequestBody MedicalRecordRequest request) {
//    Medical_Records record = medicalRecordService.addMedicalRecord(
//            request.getPatientId(),
//            request.getDiseaseId(),
//            request.getDiagnosticData(),
//            request.getTreatments()
//    );
//    return ResponseEntity.ok(record);
//}







//    @GetMapping("/patients/{patientid}")
@GetMapping("/patient/{patientId}")
public List<Medical_Records> getMedicalRecordsByPatientId(@PathVariable int patientId) {
    return medicalRecordService.getMedicalRecordsByPatientId(patientId);
}

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMedicalRecord(@PathVariable Long id,@RequestBody Medical_Records medicalRecords){
        try {
            String result=medicalRecordService.updateMedicalRecors(id,medicalRecords);
            return  new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalrecord(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/count")
    public  ResponseEntity<Long> getMedicalRecordCount(){
        long count= medicalRecordService.medicalCount();
        return  ResponseEntity.ok(count);
    }
}
