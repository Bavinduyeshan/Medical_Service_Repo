package com.Medical_Recirds2.MEdical.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")

public class Medical_Records {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;



//    @ManyToOne
//    @JoinColumn(name = "patient_id")
//    private Patient patient;

    @ManyToOne @JoinColumn(name = "disease_id", nullable = false) private Disease disease; //


    @Column(name = "patient_id", nullable = false)
    private  Integer patientID;

    @Column(name = "doctor_Id",nullable = false)
    private  Integer doctor_Id;
    @Transient
    private String name;

    @Column(name = "diagnostic_data", nullable = false, columnDefinition = "TEXT")
    private String diagnosticData;

    @Column(name = "treatments", nullable = false, columnDefinition = "TEXT")
    private String treatments;

    @Column(name = "report_url")
    private String reportUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Patient getPatient() {
//        return patient;
//    }
//
//
//
//    public void setPatient(Patient patient) {
//        this.patient = patient;
//    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    //    public Long getDiseaseId() {
//        return diseaseId;
//    }
//
//    public void setDiseaseId(Long diseaseId) {
//        this.diseaseId = diseaseId;
//    }


    public Integer getDoctor_Id() {
        return doctor_Id;
    }

    public void setDoctor_Id(Integer doctor_Id) {
        this.doctor_Id = doctor_Id;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public String getDiagnosticData() {
        return diagnosticData;
    }

    public void setDiagnosticData(String diagnosticData) {
        this.diagnosticData = diagnosticData;
    }

    public String getTreatments() {
        return treatments;
    }

    public void setTreatments(String treatments) {
        this.treatments = treatments;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
