package com.ercall.server.entity;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
public class EmergencyRoom {

    @Id
    @Column(name = "emergencyroom_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emergencyRoomId;

    @Column(name = "emergencyroom_name")
    private String emergencyRoomName;

    @Column(name = "call_score")
    private Integer callScore;

    @Column(name = "emergencyroom_code")
    private String emergencyRoomCode;

}
