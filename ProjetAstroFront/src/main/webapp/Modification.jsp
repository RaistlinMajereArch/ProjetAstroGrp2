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
            <title>Corps Celestes</title>
          </head>
          <body>


            <div id="content">

              <div class="tab-content" id="pills-tabContent">
                <div class="tab-pane fade show active" id="pills-Plan" role="tabpanel" aria-labelledby="pills-Plan-tab">


                  <h1>Liste des Corps Celestes</h1>
                  <input id="btnAddPlan" type="button" class ="btn btn-success" value="Ajouter">
                    <input id="filterPlan" placeholder="filtre">
                      <table class="table table-striped">
                        <thead>
                          <tr>
                            <th>Id</th>
                            <th>Nom</th>
                            <th>Type</th>
                            <th>Masse</th>
                            <th>Diametre</th>
                            <th>Position x</th>
                            <th>Position y</th>
                            <th>Vitesse x</th>
                            <th>Vitesse y</th>
                            <th>Id parent</th>
                          </tr>
                        </thead>
                        <tbody id="contentTable">
                          <c:forEach items="${systeminit}" var="corpsceleste">
                            <tr>
                              <td>${corpsceleste.id}</td>
                              <td>${corpsceleste.nom}</td>
                              <td>${corpsceleste.masse}</td>
                              <td>${corpsceleste.diametre}</td>
                              <td>${corpsceleste.x}</td>
                              <td>${corpsceleste.y}</td>
                              <td>${corpsceleste.vx}</td>
                              <td>${corpsceleste.vy}</td>
                              <td>${corpsceleste.type}</td>
                              <td>${corpsceleste.id_parent}</td>
                              <td>
                                <input onclick="updatePlan(${corpsceleste.id},'${corpsceleste.nom}','${corpsceleste.masse}','${corpsceleste.diametre}','${corpsceleste.x}','${corpsceleste.y}','${corpsceleste.vx}','${corpsceleste.vy}','${corpsceleste.type}','${corpsceleste.id_parent}')" type="button" class ="btn btn-warning" value="Modifier">
                                  <input onclick="deletePlan(${corpsceleste.id})" type="button" class ="btn btn-danger" value="Supprimer">
                                    <c:if test = "${corpsceleste.type =="planete"}">
                                      <input onclick="addSat(${corpsceleste.id})" type="button" class ="btn btn-success btnAddSat" value="Ajouter Satellite">
                                      </c:if>
                                    </td>
                                  </tr>
                                </c:forEach>

                              </tbody>
                            </table>

                            <div id="addFormPlan">
                              <h3>Ajouter nouvelle planete</h3>
                              <form action="systeminit" method="get">
                                <label for="add_nom">Nom :</label> <input required id="add_nom" name="nom" type="text" placeholder="Saisir le nom"><br>
                                <label for="add_masse">Masse :</label> <input required id="add_masse" name="masse" type="number" placeholder="Saisir la masse (kg)"><br>
                                <label for="add_taille">Taille :</label> <input required id="add_taille" name="taille" type="number" placeholder="Saisir la taille (km)"><br>
                                <label for="add_x0">x :</label> <input required id="add_x0" name="x0" type="number" placeholder="Saisir la coordonnee x (km)"><br>
                                <label for="add_y0">y :</label> <input required id="add_y0" name="y0" type="number" placeholder="Saisir la coordonnee y (km)"><br>
                                <label for="add_vx0">vx :</label> <input required id="add_vx0" name="vx0" type="number" placeholder="Saisir la vitesse vx (km/s)"><br>
                                <label for="add_vy0">vy :</label> <input required id="add_vy0" name="vy0" type="number" placeholder="Saisir la vitesse vy (km/s)"><br>
                                <label for="add_type"></label>  <input id="add_type" type="hidden" name="type" value="planete"><br>
                                <label for="add_id_parent"></label> <input id="add_id_parent" type="hidden" name="id_parent" value="1">


                                <input class ="btn btn-success" type="submit" name="ajouter" value="Ajouter">
                                </form>
                              </div>


                              <div id="updateFormPlan">
                                <h3>Modifier la planete</h3>

                                <form action="systeminit" method="post">
                                  <input type="hidden" id="update_id_plan" name="id">
                                    <input type="hidden" name="type_form" value="PUT">

                                      <label for="update_nom">Nom :</label> <input required id="update_nom" name="nom" value="nom" type="text" placeholder="Saisir le nom"><br>
                                      <label for="update_masse">Masse :</label> <input required id="update_masse" name="masse" type="number" placeholder="Saisir la masse (km)"><br>
                                      <label for="update_taille">Taille :</label> <input required id="update_taille" name="taille" type="number" placeholder="Saisir la taille (km)"><br>
                                      <label for="update_x">x :</label> <input required id="update_x" name="x" type="number" placeholder="Saisir la coordonnee x (km)"><br>
                                      <label for="update_y">y :</label> <input required id="update_y" name="y" type="number" placeholder="Saisir la coordonnee y (km)"><br>
                                      <label for="update_vx">vx :</label> <input required id="update_vx" name="vx" type="number" placeholder="Saisir la vitesse vx (km/s)"><br>
                                      <label for="update_vy">vy :</label> <input required id="update_vy" name="vy" type="number" placeholder="Saisir la vitesse vy (km/s)"><br>
                                      <input class ="btn btn-warning" type="submit" name="modifier" value="Modifier">
                                      </form>
                                    </div>
                                    <a href="admin.html">Retour</a>
                                  </div>
                                </div>
                              </div>


                              <script>

                              filterDep.onkeyup=filtreAjax;

                              btnAddPlan.onclick=function()
                              {
                                updateFormPlan.style.display="none";
                                addFormPlan.style.display="block";
                              }

                              function updatePlan(id,nom,masse,taille,x,y,vx,vy)
                              {

                                addFormDep.style.display="none";
                                updateFormDep.style.display="block";

                                update_nom.value=nom;
                                update_masse.value=masse;
                                update_taille.value=region;
                                update_x.value=x;
                                update_y.value=y;
                                update_vx.value=vx;
                                update_vy.value=vy;
                                update_id.value=id;

                              }

                              funtion AddSat(id, type)
                              {
                                updateFormPlan.style.display="none";
                                addFormPlan.style.display="block";
                                add_id_parent.value=id;
                                add_type.value=type;

                              }

                              function deletePlan(id)
                              {
                                if(confirm("Etes vous sur de vouloir supprimer cette planete ?"))
                                {
                                  $.ajax("systeminit", {
                                    type: "POST",
                                    data:
                                    {
                                      id : id,
                                      type_form:"DELETE"
                                    },
                                    success: function (resp) {
                                      location.href="systeminit";
                                    }
                                  });
                                }
                              }


                              function filtreAjax()
                              {
                                $.ajax("filter", {
                                  type: "POST",
                                  data:
                                  {
                                    search : filterDep.value,
                                    page:"systeminit"
                                  },
                                  success: function (resp) {
                                    contentTable.innerHTML=resp;
                                  }
                                });
                              }


                              </script>
                            </body>
                          </html>
