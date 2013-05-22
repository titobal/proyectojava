<%-- 
    Document   : index
    Created on : 21-05-2013, 12:13:22 PM
    Author     : cristobal
--%>

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
        <script src="src/js/underscore.min.js" type="text/javascript"></script>
        <script src="src/bootstrap/pnotify/jquery.pnotify.min.js" type="text/javascript"></script>
        <script src="src/js/accounting.min.js" type="text/javascript"></script>
        <script src="src/js/tools.js" type="text/javascript"></script>
        <script src="src/js/index.js" type="text/javascript"></script>
        <link href="src/bootstrap/css/united.bootstrap.min.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/modal/css/bootstrap-modal.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/css/docs.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/pnotify/jquery.pnotify.default.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/pnotify/useicons/jquery.pnotify.default.icons.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/css/animate-custom.min.css" type="text/css" rel="stylesheet"/>
        <link href="src/admi/panel.css" type="text/css" rel="stylesheet"/>
        <style type="text/css">body {padding-top: 60px;padding-bottom: 40px;}.sidebar-nav {padding: 9px 0;}
            @media (max-width: 980px) {.navbar-text.pull-right {float: none;padding-left: 5px;padding-right: 5px;}}
            .thumbnail{background-color: #ccc !important;}.caption{cursor:pointer;}</style>
    </head>
    <body>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <div class="nav-collapse collapse">
                        <ul class="nav">
                            <li class="">
                                <a role="button" href="#">Carrito de compras <i class="icon-shopping-cart icon-white"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="container"><br/>
            <div class="navbar">
                <div id="categorias" class="navbar-inner"></div>
            </div>
            <div class="row-fluid">
                <div class="span3">
                    <div class="well sidebar-nav">
                        <ul id="navLeft" class="nav nav-list"></ul>
                    </div>
                </div>
                <div class="span9">
                    <ul class="thumbnails" id="listProds"></ul>
                </div>
            </div>
        </div>
        <div class="modal hide fade" id="modal"></div>
        <script src="src/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="src/bootstrap/modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
        <script src="src/bootstrap/modal/js/bootstrap-modal.js" type="text/javascript"></script>
    </body>
</body>
</html>
