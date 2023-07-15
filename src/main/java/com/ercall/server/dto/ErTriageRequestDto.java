package com.ercall.server.dto;

import com.ercall.server.entity.ErTriage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ErTriageRequestDto {

    private Integer erRating;
    private String classifierName;
    private String patientName;
    private Integer gender;
    private Integer age;
    private String majorInjuryName;
    private Double bloodPressure;
    private Double pulse;
    private Integer consciousness;
    private Integer walkingCheck;
    private Integer ambulance;
    public ErTriage toEntity(){
        return ErTriage.builder()
                .erRating(erRating)
                .classifierName(classifierName)
                .patientName(patientName)
                .gender(gender)
                .age(age)
                .majorInjuryName(majorInjuryName)
                .bloodPressure(bloodPressure)
                .pulse(pulse)
                .consciousness(consciousness)
                .walkingCheck(walkingCheck)
                .ambulance(ambulance)
                .build();
    }
}
