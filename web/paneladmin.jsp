<%-- 
    Document   : paneladmin
    Created on : 12-05-2013, 11:50:00 PM
    Author     : cristobal
--%>
<%@page import="java.util.Map"%>
<%@page import="modelo.dao.AdministradorDAOImpl"%>
<%@page import="modelo.dao.AdministradorDAO"%>
<%@page import="modelo.bean.Administrador"%>
<%
    try{
        if (session.getAttribute("admin") == null) {
            response.sendRedirect("loginadmin.jsp");
        }
        AdministradorDAO ad = new AdministradorDAOImpl();
        Map<String, String> m = ad.getAdministrador(session.getAttribute("admin").toString());
        Administrador a = new Administrador(m.get("Correo"), m.get("Nombre"), m.get("UltimaSesion"),
                (m.get("Estado")) == "1" ? true : false, Integer.parseInt(m.get("Nivel")), false);
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <title>Panel de Administraci&oacute;n</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <!--[if lt IE 9]>
            <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <script src="src/js/jquery.min.js" type="text/javascript"></script>
        <script src="src/js/underscore.min.js" type="text/javascript"></script>
        <script src="src/bootstrap/pnotify/jquery.pnotify.min.js" type="text/javascript"></script>
        <script src="src/js/tools.js" type="text/javascript"></script>
        <%out.println(a.getNivel() == 0 ?
                "<script src='src/admi/administrador-super.js' type='text/javascript'></script>"
                : ""); %>
        <script src="src/admi/producto.js" type="text/javascript"></script>
        <script src="src/admi/panel.js" type="text/javascript"></script>
        <link href="src/bootstrap/css/united.bootstrap.min.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/modal/css/bootstrap-modal.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/css/docs.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/pnotify/jquery.pnotify.default.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/pnotify/useicons/jquery.pnotify.default.icons.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/css/animate-custom.min.css" type="text/css" rel="stylesheet"/>
        <link href="src/admi/panel.css" type="text/css" rel="stylesheet"/>
    </head>
    <body class="preview" style="background-image: url('src/images/shattered.png');background-repeat: repeat;">
        <!--header-->
        <div style="display:none" class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <a style="color:#fff" class="brand" href=""><% out.println(a.getNombre()); %></a>
                    <div class="nav-collapse collapse" id="main-menu" style="height: 0px;">
                        <ul class="nav" id="main-menu-left">
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#"><%out.println(a.getCorreo());%> <b class="caret"></b></a>
                                <ul class="dropdown-menu" id="swatch-menu">
                                    <li><a href="logout">Cerrar sesión</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!--container-->
        <div style="display:none" class="container main">
            <div class="row">
                <div class="span3 bs-docs-sidebar">
                    <ul style="top:0" class="nav nav-list bs-docs-sidenav affix">
                        <li class="active"><a href="#Productos" data-toggle="tab"><i class="icon-chevron-right"></i> Productos</a></li>
                        <li class=""><a href="#Categorias" data-toggle="tab"><i class="icon-chevron-right"></i> Categor&iacute;as</a></li>
                        <%
                            if (a.getNivel() == 0) {
               out.println("<li class=''><a href='#Administradores' data-toggle='tab'><i class='icon-chevron-right'></i> Administradores</a></li>");
                            }
                        %>
                    </ul>
                </div>
                <div class="span9 tab-content">
                    <br/>
                    <div id="Categorias" class="tab-pane animated fadeInDown">
                        <h1>Categor&iacute;as</h1>
                        <div class='btn-group'>
                            <button class='btn btn-primary bt-update'><i class='icon-white icon-refresh'></i></button>
                            <button class='btn btn-primary bt-new'><i class='icon-white icon-plus'></i></button>
                        </div>
                        <br/><br/>
                    </div>
                    <div id="Productos" class="tab-pane active animated fadeInDown">
                        <h1>Productos</h1>
                        <div class='btn-group'>
                            <button class='btn btn-primary bt-update'><i class='icon-white icon-refresh'></i></button>
                            <button class='btn btn-primary bt-new'><i class='icon-white icon-plus'></i></button>
                        </div>
                        <br/><br/>
                        <div class="row-fluid">
                            <ul class="thumbnails" id="listProds"></ul>
                        </div>
                    </div>
                        <%
                            if (a.getNivel() == 0) {
                                out.println(
                                        "<div id='Administradores' class='tab-pane animated fadeInDown'>"+
                                        "<h1>Administradores</h1>"+
                                        "<div class='btn-group'>"
                                        + "<button class='btn btn-primary bt-update'><i class='icon-white icon-refresh'></i></button>"
                                        + "<button class='btn btn-primary bt-new'><i class='icon-white icon-plus'></i></button>"
                                        + "</div>"+
                                        "<br/><br/>"+
                        "<table class='table table-striped table-bordered table-hover table-condensed'>"+
                            "<thead>"+
                                "<tr>"+
                                    "<th>Correo</th>"+
                                    "<th>Nivel</th>"+
                                    "<th>Ultima Sesión</th>"+
                                    "<th>Estado</th>"+
                                "</tr>"+
                            "</thead>"+
                            "<tbody></tbody>"+
                        "</table>");
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>

        <script>setInterval(function() {
                $(".navbar-fixed-top").show().addClass("animated fadeInDown");
                setInterval(function() {
                    $(".main").show().addClass("animated fadeInDown");
                }, 600);
            }, 1000);</script>

        <div id="4" class="modal hide fade"></div>

        <div id="loading" class="text-center">
            <div class="progress progress-striped active">
                <div class="bar" style="width: 100%;"></div>
            </div>
            <p>Cargando...</p>
        </div>

        <div id="confirm" class="modal hide fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
            <div class="modal-body"><p class="message"></p></div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn">Cancelar</button>
                <button type="button" class="btn btn-primary true">Aceptar</button>
            </div>
        </div>

        <script src="src/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="src/bootstrap/modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
        <script src="src/bootstrap/modal/js/bootstrap-modal.js" type="text/javascript"></script>
    </body>
</html>
<%
    }
    catch(NullPointerException e){ }
%>