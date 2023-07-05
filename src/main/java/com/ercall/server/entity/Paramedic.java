package com.ercall.server.entity;

import javax.persistence.*;

@Entity
public class Paramedic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paramedic_id")
    private Long paramedicId;

    @Column(name = "paramedic_name")
    private String paramedicName;

}
