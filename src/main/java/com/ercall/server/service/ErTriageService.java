package com.ercall.server.service;

import com.ercall.server.dto.ErTriageRequestDto;
import com.ercall.server.dto.ErTriageResponseDto;
import com.ercall.server.entity.ErTriage;
import com.ercall.server.entity.ErTriageRepository;
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
    @Transactional
    public Long save(final ErTriageRequestDto params){ //중증도 분류표 저장
        ErTriage entity= erTriageRepository.save(params.toEntity());
        entity.setSortingTime(LocalDateTime.now());
        return entity.getErTriageId();
    }

    public List<ErTriageResponseDto> findAll(){ // 중증도 분류표 가져오기
        Sort sort = Sort.by(Sort.Direction.DESC,"erTriageId");
        List<ErTriage> list = erTriageRepository.findAll(sort);
        return list.stream().map(ErTriageResponseDto::new).collect(Collectors.toList());

    }


}



