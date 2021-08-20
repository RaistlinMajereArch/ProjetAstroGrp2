<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>


    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<link rel="stylesheet" href="style.css">


<meta charset="utf-8">
</head>
<body>

  <div id="content">

    <div class="tab-content" id="pills-tabContent">
      <div class="tab-pane fade show active" id="pills-Plan" role="tabpanel" aria-labelledby="pills-Plan-tab">

      <div id="addEtoile">
        <h3>Creez votre etoile</h3>
        <form action="systeminit" method="post">
        <input type="hidden" name="type_form" value="POST">
        <label for="add_nom">Nom :</label> <input required id="add_nom" name="nom" type="text" placeholder="Saisir le nom"><br>
        <label for="add_type"></label> <input type="hidden" name="type" value="etoile">
        <label for="add_masse">Masse (en kg) :</label> <input required id="add_masse" name="masse" type="number" placeholder="Saisir la masse"><br>
        <label for="add_diametre">Diametre (en km) :</label> <input required id="add_diametre" name="diametre" type="number" placeholder="Saisir le diametre"><br>
        <label for="add_x0"></label> <input type="hidden" name="x0" value="0">
        <label for="add_y0"></label> <input type="hidden" name="y0" value="0">
        <label for="add_vx0"></label> <input type="hidden" name="vx0" value="0">
        <label for="add_vy0"></label> <input type="hidden" name="vy0" value="0">
        <label for="add_id_parent"></label> <input type="hidden" name="id_parent" value="0">
        <input id="hideAddEtoile" class ="btn btn-success" type="submit" name="ajouter" value="Ajouter">
        </div>
      </div>
 </div>
</div>

<script>

hideAddEtoile.onclick=function()
{
  <a id="link" href="Modification.jsp"></a>

}

</script>
</body>
</html>
