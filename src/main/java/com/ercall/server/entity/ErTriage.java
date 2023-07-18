package com.ercall.server.entity;

//<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
//=======
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//>>>>>>> 2fd5c7fa0964869e97414242a7677328586e912c

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity


@Table(name = "er_triage")
@Getter
@Setter
@NoArgsConstructor
public class ErTriage {

    @Column(name = "er_triage_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long erTriageId; //1번부터 저장되도록

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient; //환자id , fk

    @Column(name = "classifier_name")
    private String classifierName; //분류자명

    @Column(name = "sorting_time") //분류시간은 form 제출 시간으로 한다
    private LocalDateTime sortingTime;

    @Column(name = "er_rating") //중증도 1,2,3,4 의 값으로 받을것
    private Integer erRating;

    @Column(name = "patient_name") //환자이름
    private String patientName;

    @Column
    private Integer age; //나이

    @Column
    private Integer gender; //성별

    @Column
    private String place; //발견장소 => geocoding api 를 통해 항상 현재위치로 받아놓게끔 임의설정

    @Column(name = "major_injury_name") //주요 손상명
    private String majorInjuryName;

    @Column(name = "blood_pressure")
    private Double bloodPressure; //혈압

    @Column
    private Double pulse; //맥박

    @Column
    private Integer consciousness; //의식 없을경우 0 있을경우 1

    @Column
    private Integer walkingCheck; //보행여부 없을경우 0 있을경우 1

    @Column
    private Integer ambulance; //사설 여부, 사설아닐경우 0 사설일경우 1

    @Column(name = "transfer_hospital") //이송의료기관
    private String transferHospital;

    @Column(name = "transport_time") //이송(출발)시간
    private LocalDateTime transportTime;

//    @PrePersist
//    public void prePersist(){
//        this.sortingTime = LocalDateTime.now();
//    }

    @Builder
    public ErTriage(String classifierName
            ,LocalDateTime sortingTime
            ,Integer erRating
            ,String patientName
            ,Integer age
            ,Integer gender
            ,String place
            ,String majorInjuryName
            ,Double bloodPressure
            ,Double pulse
            ,Integer consciousness
            ,Integer walkingCheck
            ,Integer ambulance
            ,String transferHospital
            ,LocalDateTime transportTime){
        this.classifierName= classifierName;
        this.sortingTime= sortingTime;
        this.erRating=erRating;
        this.patientName=patientName;
        this.age=age;
        this.gender=gender;
        this.place=place;
        this.majorInjuryName=majorInjuryName;
        this.bloodPressure=bloodPressure;
        this.pulse=pulse;
        this.consciousness=consciousness;
        this.walkingCheck=walkingCheck;
        this.ambulance=ambulance;
        this.transferHospital=transferHospital;
        this.transportTime=transportTime;
    }

}
