package org.example.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "factura")

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private int number;
    private int total;
    // Solo vamos a querer persistir un cliente al generar una factura
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_cliente")
    private Cliente client;
    // Si elimino una factura quiero que sus detalles tmb se eliminen
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //private Set<DetalleFactura> detalles = new HashSet<>();
    // MappedBy para bidireccionalidad
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<DetalleFactura> detalles = new HashSet<>();

    // Método para calcular y setear el total de la factura
    public void setTotal() {
        this.total = this.detalles.stream()
                .mapToInt(DetalleFactura::getSubtotal)
                .sum();
    }
}
