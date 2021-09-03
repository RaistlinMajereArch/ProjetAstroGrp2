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
<title>Initialisation</title>
</head>
<body>
<h1>Initialisation</h1>

			<div id="addFormPlan">
                               <h3>Entrez les conditions de la simulation</h3>
                              <form action="lancerSimu" method="post">
                              <input type="hidden" name="type_form" value="POST">
                                <label for="add_timestep">Nombre de timestep (1 timestep = 1 jour) :</label> <input required id="add_timestep" name="timestep" type="text" placeholder="Nombre de timestep"><br>
                                <label for="add_calc">Voulez-vous des calculs simplifiés :</label> 
                                <input id="Oui" name="add_calc" type="radio"> <label for="True">Oui</label>
                                <input id="Non" name="add_calc" type="radio"> <label for="False">Non</label>
            					<br>
                                             
								<input class ="btn btn-success" type="submit" name="ajouter" value="Demarrer la simulation">
                                </form>
                              </div>
</body>
</html>