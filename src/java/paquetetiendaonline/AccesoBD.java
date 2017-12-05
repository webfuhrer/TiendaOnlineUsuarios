/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetetiendaonline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luis
 */
class AccesoBD {
    static Connection c=null;
    static Statement stmt=null;
      private static void abrirConexion()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c=DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda_esoterica", "root", "");
            stmt=c.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccesoBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AccesoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      private static void cerrarConexion()
    {
        try {
            stmt.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccesoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public static int grabarUsuario(Usuario u)
      {
          abrirConexion();
        int id=0;
        try {
            String sql ="INSERT INTO usuarios (usuario, password, saldo) VALUES ('"+u.getNombre()+"', '"+u.getPassword()+"', "+u.getSaldo()+");";
             id=stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
             cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(AccesoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
          return id;
      }

    static List<Producto> obtenerProductos() {
        List<Producto> lista_productos=new ArrayList<>();
        try {
            
            String sql="SELECT * FROM productos WHERE stock>0";
            abrirConexion();
            ResultSet resultados=stmt.executeQuery(sql);
            while(resultados.next())
            {
                int id=resultados.getInt("id");
                String nombre=resultados.getString("nombre");
                float precio=resultados.getFloat("precio");
                int stock=resultados.getInt("stock");
                Producto p=new Producto(nombre, stock, precio, id);
                lista_productos.add(p);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(AccesoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
         return lista_productos;
    }

    static Usuario comprobarUsuario(String usuario, String password) {
        Usuario u=null;
        try {
            
            abrirConexion();
            String sql= "SELECT * FROM usuarios WHERE usuario='"+usuario+"' and password='"+password+"';";
            ResultSet resultados=stmt.executeQuery(sql);
                    if(resultados.next())
                    {
                        float saldo=resultados.getFloat("saldo");
                        int id=resultados.getInt("id");
                        u=new Usuario(usuario, "", "", saldo, id);
                    }} catch (SQLException ex) {
            Logger.getLogger(AccesoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
       
    }

    static void descontarSaldo(String id_producto, String nombre) {
        try {
            abrirConexion();
            String sql="UPDATE usuarios SET saldo=saldo-(select precio from productos where id='"+id_producto+"') WHERE usuario='"+nombre+"';";
            stmt.executeUpdate(sql);
            cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(AccesoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void insertarProducto(String id_producto, String nombre) {
        try {
            String sql="UPDATE usuarios SET productos=CONCAT(productos, ',',"+id_producto+") WHERE usuario='"+nombre+"';";
           
            abrirConexion();
            stmt.executeUpdate(sql);
            cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(AccesoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void descontarStock(String id_producto) {
        try {
            String sql="UPDATE productos SET stock=stock-1 WHERE id='"+id_producto+"';";
            abrirConexion();
            stmt.executeUpdate(sql);
            cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(AccesoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static float obtenerSaldo(int id) {
        float saldo=0;
        try {
            String sql="SELECT saldo FROM usuarios WHERE id="+id;
            abrirConexion();
            ResultSet resultados = stmt.executeQuery(sql);
            if (resultados.next())
            {
                 saldo=resultados.getFloat("saldo");
            }
                } catch (SQLException ex) {
            Logger.getLogger(AccesoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saldo;
    }
}
