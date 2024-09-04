package org.example.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;

@Entity
@Table(name = "cliente")

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int dni;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_domicilio")
    private Domicilio address;

    // Bidireccionalidad de cliente a facturas
    //@OneToMany(mappedBy = "cliente")
    //@Builder.Default
    //private Set<Factura> facturas = new HashSet<>();}
}