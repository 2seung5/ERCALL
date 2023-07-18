package com.ercall.server.repository;

import com.ercall.server.dto.MatchDto;
import com.ercall.server.entity.ErTriage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<ErTriage, Long> {
}
