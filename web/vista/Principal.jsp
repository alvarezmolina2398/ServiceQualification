<%-- 
    Document   : Principal
    Created on : 14/07/2017, 12:36:02 PM
    Author     : Desarrollo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Service Qualification</title>
        <script src="../JQuery/jquery.min.js" type="text/javascript"></script>
        <link href="../bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="../bootstrap/css/bootstrap-reboot.css" rel="stylesheet" type="text/css"/>
        <script src="../bootstrap/js/bootstrap.js" type="text/javascript"></script>
        <link href="../bootstrap/css/bootstrap-grid.css" rel="stylesheet" type="text/css"/>
        <link href="../css/Principal.css" rel="stylesheet" type="text/css"/>
        <link href="../font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css"/>
        <script>
            $(function (){
                $('.img').click(function (){
                    $('#Panel1').removeClass('active');
                    $('#Panel1').addClass('not-active');
                    $('#Panel2').removeClass('not-active');
                    $('#Panel2').addClass('active');
                });
                $('#BtnAtras').click(function (){
                    $('#Panel2').removeClass('active');
                    $('#Panel2').addClass('not-active');
                    $('#Panel1').removeClass('not-active');
                    $('#Panel1').addClass('active');
                });
            });
        </script>
    </head>
    <body>
        <header>
            <nav class="navbar navbar-inverse color-blue">
                <div class="row">
                    <div class="col-md-1">
                        <i id="BtnAtras" class="fa fa-arrow-left fa-2x text-white"></i>
                    </div>
                    <div class="col-md-10">
                        <center>
                            <a id="pregunta">Selecciona el departamendo donde fue atendido.</a>
                        </center>
                    </div>
                </div>
            </nav>    
        </header>
        <main>
            <section>
                <form>
                    <br/>
                    <!--<center><p>Selecione el Departamento en que fue Atendido.<p></center>-->
                    <center class="panel-img active" id="Panel1">
                        <div id="center-img">
                            <img class="img" src="http://10.10.10.17:8080/Documentos/caja.png" alt=""/>
                            <img class="img" src="http://10.10.10.17:8080/Documentos/creditos.png" alt=""/>
                            <img class="img" src="http://10.10.10.17:8080/Documentos/secretaria.png" alt=""/>
                            <img class="img" src="http://10.10.10.17:8080/Documentos/seguros.png" alt=""/>
                        </div>
                    </center>
                     <center class="panel-img not-active" id="Panel2">
                         <!--<p>1.Tomcat </p>-->
                        <div id="center-img">
                            <img class="img" src="http://10.10.10.17:8080/Documentos/malo.png" alt=""/>
                            <img class="img" src="http://10.10.10.17:8080/Documentos/horrible.png" alt=""/>
                            <img class="img" src="http://10.10.10.17:8080/Documentos/indiferente.png" alt=""/>
                            <img class="img" src="http://10.10.10.17:8080/Documentos/bien.png" alt=""/>
                            <img class="img" src="http://10.10.10.17:8080/Documentos/excelente.png" alt=""/>
                        </div>
                    </center>
                    <center id="panel-comentario" class="not-active">
                        <div class="form-group row">
                            <input type="text" class="form-control col-md-12" id="comentario" rows="3" placeholder="¿Que opinas de nuestro Servicio?">
                        </div>
                    </center>
                </form>
            </section>
        </main>
        <footer>
            <div  class="color-blue" id="footer-principal">
                <img id="logo" src="../imagenes/logo.png" alt=""/>
            </div>
            <div  class="color-blue" id="footer-secundario">
                <div class="copyright">
                    &nbsp;<i class="fa fa-copyright"></i> Departamento De Investigacón y Desarrollo - Cooperativa Tonantel 2017
                </div>
            </div>
        </footer>
    </body>
</html>
