package org.example.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "domicilio")

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Domicilio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String street;
    private int number;
    // Bidireccionalidad de domicilio a cliente
    //@OneToOne(mappedBy = "address")
    //private Cliente client;
}
