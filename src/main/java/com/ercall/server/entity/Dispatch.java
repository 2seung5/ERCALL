package com.ercall.server.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Dispatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dispatch_id")
    private Long dispatchId;

    @ManyToOne
    @JoinColumn(name = "paramedic_id")
    private Paramedic paramedic;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // 위도
    @Column
    private Double latitude;
    
    // 경도
    @Column
    private Double longitude;


}
