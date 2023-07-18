package com.ercall.server;

import com.ercall.server.entity.ErTriage;
import com.ercall.server.entity.ErTriageRepository;
import com.ercall.server.entity.Patient;
import com.ercall.server.entity.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class erTriageTest {
    @Autowired
    ErTriageRepository erTriageRepository;

    @Autowired
    PatientRepository patientRepository;

    @Test
    void save(){
        ErTriage params = ErTriage.builder()
                .classifierName("hwang")
                .sortingTime(LocalDateTime.now())
                .erRating(1)
                .build();

        Patient pattient= Patient.builder()
                        .age(11)
                                .gender(1)
                                        .patientName("hwang")
                                                .build();
        patientRepository.save(pattient);

        erTriageRepository.save(params);

        ErTriage entity = erTriageRepository.findById((long) 6).get();

        assertThat(entity.getClassifierName()).isEqualTo("hwang");
        assertThat(entity.getErRating()).isEqualTo(1);

    }
    @Test
    void delete(){
        ErTriage entity = erTriageRepository.findById((long)1).get();
        erTriageRepository.delete(entity);
    }
    @Test
    void findAll(){
        long erTriageCount= erTriageRepository.count();
        List<ErTriage> erTriages = erTriageRepository.findAll();
    }


}
