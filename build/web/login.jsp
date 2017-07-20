<%-- 
    Document   : login
    Created on : 13/07/2017, 03:05:11 PM
    Author     : Desarrollo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Service Qualification</title>
        <link href="bootstrap/css/bootstrap-grid.css" rel="stylesheet" type="text/css"/>
        <link href="bootstrap/css/bootstrap-reboot.css" rel="stylesheet" type="text/css"/>
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <script src="JQuery/jquery.min.js" type="text/javascript"></script>
        <script src="bootstrap/js/bootstrap.js" type="text/javascript"></script>
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css"/>
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <script>
            $(function (){
               $('#iniciar_sesion').click(function (){
                   window.location.href = "http://10.10.10.16:8084/ServiceQualification/vista/Principal.jsp";
               });
            });
        </script>
    </head>
    <body>
        <div class="main">
            <div class="container">
                <center>
                    <div class="middle">
                        <div id="login">
                            <form action="javascript:void(0);" method="get">
                                <fieldset class="clearfix">
                                    <p><span class="fa fa-user"></span><input type="text"  Placeholder="Username" required></p> <!-- JS because of IE support; better: placeholder="Username" -->
                                    <p><span class="fa fa-lock"></span><input type="password"  Placeholder="Password" required></p> <!-- JS because of IE support; better: placeholder="Password" -->
                                    <div>
                                        <span style="width:48%; text-align:left;  display: inline-block;">
                                            <a class="small-text" href="#">He olvidado mi contraseña</a>
                                        </span>
                                        <span style="width:50%; text-align:right;  display: inline-block;"><input id="iniciar_sesion" type="submit" value="Sign In"></span>
                                    </div>
                                </fieldset>
                                <div class="clearfix"></div>
                            </form>
                            <div class="clearfix"></div>
                        </div> <!-- end login -->
                        <div class="logo">
                            <img src="imagenes/somosgentediferente.png" alt="" style="height: 100%;width: 100%"/>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </center>
            </div>
        </div>
    </body>
</html>
