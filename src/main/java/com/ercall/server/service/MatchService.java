package com.ercall.server.service;

import com.ercall.server.dto.MatchRequest;

import java.util.List;
import java.util.Set;

public interface MatchService {

    List<String> getErCode(MatchRequest matchRequest);

    Set<String> getErName(MatchRequest matchRequest);
}
