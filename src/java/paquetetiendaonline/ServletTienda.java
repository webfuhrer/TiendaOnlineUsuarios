/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetetiendaonline;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author luis
 */
@WebServlet(name = "ServletTienda", urlPatterns = {"/ServletTienda"})
public class ServletTienda extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion=request.getParameter("accion");
        if(accion.equals("registro"))
        {
            String usuario=request.getParameter("usuario");
            String password=request.getParameter("password");
            String saldo=request.getParameter("saldo");
            Usuario u=new Usuario(usuario, password, Float.parseFloat(saldo));
            int id=AccesoBD.grabarUsuario(u);
            u.setId(id);
            HttpSession sesion=request.getSession();
            sesion.setAttribute("usuario", u);
            List<Producto> lista_productos=AccesoBD.obtenerProductos();
            request.setAttribute("lista_productos", lista_productos);
            request.getRequestDispatcher("comprarproductos.jsp").forward(request, response);
            
        }
        if (accion.equals("login"))
        {
            String usuario=request.getParameter("usuario");
            String password=request.getParameter("password");
            Usuario u=AccesoBD.comprobarUsuario(usuario, password);
            if (u!=null)//Correctamente logueado
            {
            HttpSession sesion=request.getSession();
            sesion.setAttribute("usuario", u);
            List<Producto> lista_productos=AccesoBD.obtenerProductos();
            request.setAttribute("lista_productos", lista_productos);
            request.getRequestDispatcher("comprarproductos.jsp").forward(request, response);
            }
            else
            {
                request.getRequestDispatcher("index.html").forward(request, response);
            }
                
        }
        if (accion.equals("comprar"))
        {
            String id_producto=request.getParameter("producto");
            HttpSession sesion=request.getSession();
            Usuario u=(Usuario)sesion.getAttribute("usuario");
            AccesoBD.descontarSaldo(id_producto, u.getNombre());
            AccesoBD.insertarProducto(id_producto, u.getNombre());
            AccesoBD.descontarStock(id_producto);
            
            float saldo_actualizado=AccesoBD.obtenerSaldo(u.getId());
            u.setSaldo(saldo_actualizado);
            sesion.setAttribute("usuario", u);
            List<Producto> lista_productos=AccesoBD.obtenerProductos();
            request.setAttribute("lista_productos", lista_productos);
            request.getRequestDispatcher("comprarproductos.jsp").forward(request, response);
            
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
