package com.ercall.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity


@Table(name = "er_triage")
public class ErTriage {

    @Column(name = "er_triage_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long erTriageId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "classifier_name")
    private String classifierName;

    @Column(name = "sorting_time")
    private LocalDateTime sortingTime;

    @Column(name = "er_rating")
    private Integer erRating;

    @Column(name = "patient_name")
    private String patientName;

    @Column
    private Integer age;

    @Column
    private Integer gender;

    @Column
    private String place;

    @Column(name = "major_injury_name")
    private String majorInjuryName;

    @Column(name = "blood_pressure")
    private Double bloodPressure;

    @Column
    private Double pulse;

    @Column
    private Integer consciousness;

    @Column
    private Integer walkingCheck;

    @Column
    private Integer ambulance;

    @Column(name = "transfer_hospital")
    private String transferHospital;

    @Column(name = "transport_time")
    private LocalDateTime transportTime;

}
