package com.ercall.server.service;

import com.ercall.server.dto.MatchRequest;
import org.springframework.http.ResponseEntity;

public interface MatchService {


    ResponseEntity<String> getErName(MatchRequest matchRequest);
}
