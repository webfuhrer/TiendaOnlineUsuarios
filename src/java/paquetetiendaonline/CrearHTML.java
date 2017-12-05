/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetetiendaonline;

import java.util.List;

/**
 *
 * @author luis
 */
public class CrearHTML {
    public static String pintarTabla(List<Producto> lista)
    {
        String tabla="<table class='tabla_productos'>";
        tabla+="<tr><th>Nombre</th><th>Precio</th><th>Stock</th><th>Comprar</th></tr>";
        for (Producto p: lista)
        {
            String enlace_compra=crearEnlaceCompra(p.getId());
            tabla+="<tr><td>"+p.getNombre()+"</td><td>"+p.getPrecio()+"</td><td>"+p.getStock()+"</td><td>"+enlace_compra+"</td></tr>";
        }
        return tabla;
    }

    private static String crearEnlaceCompra(int id) {
       String aux="<a href='ServletTienda?accion=comprar&producto="+id+"'>COMPRAR!!</a>";
       return aux;
    }
    
}
