<%-- 
    Document   : Principal
    Created on : 14/07/2017, 12:36:02 PM
    Author     : Desarrollo
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Service Qualification</title>
        <script src="JQuery/jquery.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="bootstrap/css/bootstrap-reboot.css" rel="stylesheet" type="text/css"/>
        <script src="bootstrap/js/bootstrap.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap-grid.css" rel="stylesheet" type="text/css"/>
        <link href="css/Principal.css" rel="stylesheet" type="text/css"/>
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css"/>
        <script>
            $(function (){
                var datos= [];
                var totalpreguntas = parseInt($('#totalpreguntas').text());
                var panel = 0;
               
                //envia el numero de pregunta actual
                
                $('#numeroPregunta').text(panel+1);
                
                $('.img').click(function (){
                    
                    if(panel==0){
                        $('#depselect').val($(this).find('.value').html());
                    }
                    else{
                        var o = new Object();
                            o.pregunta = $(this).find('.idinterroganteval').html();
                            o.parametro =  $(this).find('.value').html();
                        datos.push(o);
                    }
                    
                    if((panel+1)<totalpreguntas){
                        $('#Panel'+panel).removeClass('active');
                        $('#Panel'+panel).addClass('not-active');
                        panel +=1;
                        $('#Panel'+panel).removeClass('not-active');
                        $('#Panel'+panel).addClass('active');
                        $('#pregunta').text($('#pregunta'+panel).text());
                        $('#numeroPregunta').text(panel+1);
                    }
                    else{
                        for(var i =0;i<panel;i++){
                            console.log(datos[i]);
                        }
                        
                    }
                });
                
                $('#BtnAtras').click(function (){
                    if(panel>0){
                        $('#Panel'+panel).removeClass('active');
                        $('#Panel'+panel).addClass('not-active');
                        panel -=1;
                        $('#Panel'+panel).removeClass('not-active');
                        $('#Panel'+panel).addClass('active');
                        $('#pregunta').text($('#pregunta'+panel).text());
                        $('#numeroPregunta').text(panel+1);
                    }
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
                    <div class="col-md-1">
                        <h1 class="text-white"><span id="numeroPregunta"></span>/<span id="totalpreguntas"><s:property value="totalpreguntas"/></span></h1>
                    </div>
                </div>
            </nav>    
        </header>
        <main>
            <div class="not-active">
                <input type="hidden" id="depselect">
                <span id="pregunta0">Selecciona el departamendo donde fue atendido.</span>
                <s:iterator value="ListQuestions">
                    <span id="pregunta<s:property value="idinterrogante"/>"><s:property value="question"/></span>
                </s:iterator>
            </div> 
            <section>
                <form>
                    <br/>
                    <center class="panel-img active" id="Panel0">
                        <div id="center-img">
                            <s:iterator value="Listdepartamentos">
                                <figure class="img">
                                    <img  class="selected" src="<s:property value="dirImg"/>" alt="<s:property value="nombreDepartamento"/>"/>
                                    <figcaption>
                                        <center><span class="text-name"><s:property value="nombreDepartamento"/></span></center>
                                        <span class="value"><s:property value="iddepartamento"/></span>
                                    </figcaption>
                                </figure>
                            </s:iterator>
                        </div>
                    </center>
                    <s:iterator value="ListQuestions">
                        <center class="panel-img not-active" id="Panel<s:property value="idinterrogante"/>">
                            <div id="center-img">
                                <s:iterator value="listParametros">
                                    <figure class="img">
                                        <img  class="selected" src="<s:property value="dirImg"/>" alt="<s:property value="descripcion"/>"/>
                                        <figcaption>
                                            <span class="value"><s:property value="idparametro"/></span>
                                            <span class="idinterroganteval"><s:property value="idinterrogante"/></span>
                                        </figcaption>
                                    </figure>
                                </s:iterator>
                            </div>
                        </center>
                    </s:iterator>
                </form>
            </section>
            <span><s:property value="ERROR"/></span>
        </main>
        <footer>
            <div  class="color-blue" id="footer-principal">
                <img id="logo" src="imagenes/logo.png" alt=""/>
            </div>
            <div  class="color-blue" id="footer-secundario">
                <div class="copyright">
                    &nbsp;<i class="fa fa-copyright"></i> Departamento De Investigacón y Desarrollo - Cooperativa Tonantel 2017
                </div>
            </div>
        </footer>
    </body>
</html>
