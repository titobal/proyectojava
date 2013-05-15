<%-- 
    Document   : index
    Created on : 10-05-2013, 11:34:26 PM
    Author     : cristobal
--%>
<%
    if(session.getAttribute("admin") != null){
        response.sendRedirect("paneladmin.jsp");
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Inicio de sesi&oacute;n</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="src/js/jquery.min.js" type="text/javascript"></script>
        <script src="src/js/enc.min.js" type="text/javascript"></script>
        <script src="src/admi/welco.js" type="text/javascript"></script>
        <link href="src/bootstrap/css/united.bootstrap.min.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/modal/css/bootstrap-modal.css" type="text/css" rel="stylesheet"/>
        <link href="src/bootstrap/css/animate-custom.min.css" type="text/css" rel="stylesheet"/>
        <style>#loading{display:none;z-index:9999;position: fixed;top: 100px;left: 0;right: 0;margin: auto;width: 300px;background-color: #fff;padding: 20px;border-radius: 5px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.34);}</style>
        <title>JSP Page</title>
    </head>
    <body style="background-image: url('src/images/shattered.png');background-repeat: repeat;"><br/><br/><br/>
        <div class="container">
            <div class="well" style="background-color:#fff;display:none;height:271px">
                <form class="form-horizontal" style="display:none">
                    <fieldset>
                        <legend>Iniciar sesión</legend>
                        <div class="control-group">
                            <label class="control-label">Correo electrónico</label>
                            <div class="controls">
                                <div class="input-prepend">
                                    <span class="add-on"><i class="icon-user"></i></span>
                                    <input name="correo" type="email" required placeholder="ejemplo@ejemplo.cl" class="input-xlarge"/>
                                </div>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">Contraseña</label>
                            <div class="controls">
                                <div class="input-prepend">
                                    <span class="add-on"><i class="icon-lock"></i></span>
                                    <input name="pass" type="password" title="Contraseña segura" required pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" placeholder="Contraseña segura" class="input-xlarge">
                                </div>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"></label>
                            <div class="controls">
                                <button class="btn btn-success" data-loading-text="Cargando...">Iniciar sesión</button>
                                <button href="#modalRec" data-toggle="modal" role="button" class="btn btn-primary" data-loading-text="Cargando...">Recuperar contraseña</button>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
        
        <script>setInterval(function(){$(".well").show().addClass("animated fadeInLeft");
        setInterval(function(){$(".well .form-horizontal").show().addClass("animated fadeInLeft")},300);},1000);</script>
        
        <div id="modalRec" class="modal hide fade"></div>
        
        <div id="loading" class="text-center">
            <div class="progress progress-striped active">
                <div class="bar" style="width: 100%;"></div>
            </div>
            <p>Cargando...</p>
        </div>
        
        <div id="alert" data-backdrop="static" data-keyboard="false" class="modal hide fade">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3>Información</h3>
            </div>
            <div class="modal-body"></div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-primary">Aceptar</button>
            </div>
        </div>
        
        <script src="src/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="src/bootstrap/modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
        <script src="src/bootstrap/modal/js/bootstrap-modal.js" type="text/javascript"></script>
    </body>
</html>