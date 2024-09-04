package org.example;

import org.example.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");

        // Instanciamos emf
        EntityManager em = emf.createEntityManager();
        System.out.println("Running...");

        try {
            // Iniciamos una transaccion
            em.getTransaction().begin();

            // Creamos una factura
            Factura f1 = Factura.builder().number(12).date("04/09/2024").build();
            // Creamos un domicilio
            Domicilio dom = Domicilio.builder().street("Los Tamarindos").number(1697).build();
            // Creamos un cliente
            Cliente cli = Cliente.builder().name("Leandro").surname("Eraso").dni(45137848).build();
            // Seteamos el domicilio del cliente
            cli.setAddress(dom);
            // Seteamos el cliente de la factura
            f1.setClient(cli);
            // Creamos categorias
            Categoria cat1 = Categoria.builder().denomination("percederos").build();
            Categoria cat2 = Categoria.builder().denomination("lacteos").build();
            Categoria cat3 = Categoria.builder().denomination("limpieza").build();
            // Creamos articulos
            Articulo art1 = Articulo.builder().quantity(200).denomination("Yogurt Ser Sabor Vainilla").price(2800).build();
            Articulo art2 = Articulo.builder().quantity(340).denomination("Detergente Ariel").price(1600).build();
            // Le asignamos al articulo su categoria y viceversa
            art1.getCategorias().add(cat1);
            art1.getCategorias().add(cat2);

            cat1.getArticulos().add(art1);
            cat2.getArticulos().add(art1);

            art2.getCategorias().add(cat3);
            cat3.getArticulos().add(art2);
            //Creamos detalleFactura
            DetalleFactura df1 = DetalleFactura.builder().build();
            // Seteamos el articulo, cantidad y subtotal
            df1.setArticulo(art1);
            df1.setQuantity(4);
            df1.setSubtotal(11200);
            // Seteamos un detalle a un articulo
            art1.getDetalles().add(df1);
            // Seteamos un detalle a una factura y viceversa
            f1.getDetalles().add(df1);
            df1.setFactura(f1);
            // Creamos otro detalleFactura
            DetalleFactura df2 = DetalleFactura.builder().build();
            // Seteamos el articulo, cantidad y precio
            df2.setArticulo(art2);
            df2.setQuantity(2);
            df2.setSubtotal(3200);
            //Seteamos un detalle a un articulo
            art2.getDetalles().add(df2);
            // Seteamos un detalle a una factura y viceversa
            df2.setFactura(f1);
            f1.getDetalles().add(df2);
            // Definimos el total de la factura
            f1.setTotal();

            // Persistimos la factura
            em.persist(f1);

            // Hacemos un commit
            em.getTransaction().commit();
        } catch (Exception e) {
            // Si falla nuestra persistencia hacemos un rollback para volver al estado anterior
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase Factura");
        }

        // Cerramos la conexion
        em.close();
        emf.close();
    }
}
