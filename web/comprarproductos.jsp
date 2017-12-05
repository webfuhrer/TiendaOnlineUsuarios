<%-- 
    Document   : comprarproductos
    Created on : 05-dic-2017, 10:21:35
    Author     : luis
--%>

<%@page import="paquetetiendaonline.CrearHTML"%>
<%@page import="paquetetiendaonline.Usuario"%>
<%@page import="paquetetiendaonline.Producto"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%
            List<Producto> lista_productos=(List<Producto>)request.getAttribute("lista_productos");
            Usuario u=(Usuario)session.getAttribute("usuario");
            String html_tabla=CrearHTML.pintarTabla(lista_productos);
            %>
            
        <title>Comprar productos</title>
    </head>
    <body>
        Bienvenido, <%=u.getNombre()%> tienes <%=u.getSaldo()%> de saldo
        <h1>Hello World!</h1>
        <%=html_tabla%>
    </body>
</html>
