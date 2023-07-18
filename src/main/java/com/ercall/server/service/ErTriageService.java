package com.ercall.server.service;

import com.ercall.server.dto.ErTriageRequestDto;
import com.ercall.server.dto.ErTriageResponseDto;
import com.ercall.server.entity.ErTriage;
import com.ercall.server.entity.ErTriageRepository;
import com.ercall.server.entity.Patient;
import com.ercall.server.entity.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //final 필드 생성자 자동생성
public class ErTriageService {
    private  final ErTriageRepository erTriageRepository;
    private final PatientRepository patientRepository;
    @Transactional
    public Long save(final ErTriageRequestDto params){ //중증도 분류표 저장
        ErTriage ertriage_entity= params.toEntity();

        Patient patient_entity= Patient.builder() //환자 entity 생성
                                .patientName(ertriage_entity.getPatientName())
                                .age(ertriage_entity.getAge())
                                .gender(ertriage_entity.getGender())
                                .build();
        patientRepository.save(patient_entity); //환자 저장


        ertriage_entity.setPatient(patient_entity);
        ertriage_entity.setSortingTime(LocalDateTime.now());

        erTriageRepository.save(ertriage_entity);

        return ertriage_entity.getErTriageId();
    }

    public List<ErTriageResponseDto> findAll(){ // 중증도 분류표 가져오기
        Sort sort = Sort.by(Sort.Direction.DESC,"erTriageId");
        List<ErTriage> list = erTriageRepository.findAll(sort);
        return list.stream().map(ErTriageResponseDto::new).collect(Collectors.toList());

    }


}



