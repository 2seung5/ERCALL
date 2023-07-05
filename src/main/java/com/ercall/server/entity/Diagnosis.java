package com.ercall.server.entity;

import javax.persistence.*;

@Entity
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_id")
    private Long diagnosisId;

    @ManyToOne
    @JoinColumn(name = "emergencyroom_id")
    private EmergencyRoom emergencyRoom;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "er_triage_id")
    private ErTriage erTriage;

}
