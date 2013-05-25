<%-- 
    Document   : venta
    Created on : 24-05-2013, 06:34:01 PM
    Author     : cristobal
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tienda en l&iacute;nea</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <!--[if lt IE 9]>
            <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <script src="src/js/jquery.min.js" type="text/javascript"></script>
        <link href="src/bootstrap/css/spacelab.bootstrap.min.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/css/docs.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        <%
            Map<String, String> venta = new HashMap<String, String>();
            venta = (Map<String, String>) request.getAttribute("venta");
            pageContext.setAttribute("venta", venta);
            
            List<Map<String, String>> carro = new ArrayList<Map<String, String>>();
            carro = (List<Map<String, String>>) request.getAttribute("carro");
            pageContext.setAttribute("carro", carro);
            
            String total = new String();
            total = (String)request.getAttribute("total");
            pageContext.setAttribute("total", total);
        %>
        <div class="container">
            <h1 class="text-center">Venta Nro. <c:out value="${venta.get('Codigo')}" /> </h1>
            <p>Nombre: <c:out value="${fn:toUpperCase(venta.get('Nombre'))} ${fn:toUpperCase(venta.get('ApellidoP'))} ${fn:toUpperCase(venta.get('ApellidoM'))}" /> </p>
            <p>Correo: <c:out value="${venta.get('Correo')}"/></p>
            <p>Dirección: <c:out value="${fn:toUpperCase(venta.get('Comuna'))}, ${fn:toUpperCase(venta.get('Calle'))} No ${fn:toUpperCase(venta.get('Numero'))}"/></p>
            <br/><br/>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Precio</th>
                        <th>Cantidad</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="carro" items="${carro}">
                        <tr>
                            <td><c:out value="${fn:toUpperCase(carro.get('Nombre'))}" /></td>
                            <td>$<fmt:formatNumber value="${carro.get('Valor')}" maxFractionDigits="3"/></td>
                            <td><c:out value="${carro.get('Cantidad')}" /></td>
                            <td>$<fmt:formatNumber value="${carro.get('Total')}" maxFractionDigits="3"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h4 class="text-right">Total: <c:out value="${total}" /></h4>
        </div>
    </body>
</html>