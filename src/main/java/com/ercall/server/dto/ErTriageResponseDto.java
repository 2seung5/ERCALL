package com.ercall.server.dto;

import com.ercall.server.entity.ErTriage;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
@Getter
public class ErTriageResponseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long erTriageId;
    private String classifierName;
    private Integer erRating;
    private String patientName;
    private Integer gender;
    private LocalDateTime sortingTime;
    private Integer age;
    private String majorInjuryName;
    private Double bloodPressure;
    private Double pulse;
    private Integer consciousness;
    private Integer walkingCheck;
    private Integer ambulance;

    @Builder
    public ErTriageResponseDto(ErTriage entity){
        this.erTriageId= entity.getErTriageId();
        this.classifierName= entity.getClassifierName();
        this.sortingTime=entity.getSortingTime();
        this.erRating=entity.getErRating();
        this.patientName=entity.getPatientName();
        this.gender=entity.getGender();
        this.age=entity.getAge();
        this.majorInjuryName=entity.getMajorInjuryName();
        this.bloodPressure=entity.getBloodPressure();
        this.pulse=entity.getPulse();
        this.consciousness=entity.getConsciousness();
        this.walkingCheck=entity.getWalkingCheck();
        this.ambulance=entity.getAmbulance();
    }


}
