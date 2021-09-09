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
        

<style>
body {
	margin-left:10px;
	background-color: black;
	 color: white;
}

.middle {
	border-left: solid 1px;
	border-right: double 1px;
	border-color: grey;
	background-color: white;
}

.bottom {
	border-left: solid 1px;
	border-right: double 1px;
	border-bottom: double 1px;
	border-color: grey;
	background-color: white;
}
#content {
margin-left:10px;
color: white;}
#tablestyle{
margin-left:10px;
color: white;}


#addFormPlan,#updateFormPlan,#addFormSat
{
  margin-left:10px;
  display:none;
}
</style>


          <meta charset="utf-8">
            <title>Corps Celestes</title>
            <br>
     </head>
          <body>


            <div id="content">

              <div class="tab-content" id="pills-tabContent">
                <div class="tab-pane fade show active" id="pills-Plan" role="tabpanel" aria-labelledby="pills-Plan-tab">


                  <h1>Liste des Corps Celestes</h1>
            		<br>
            		<br>
                  <input id="btnAddPlan" type="button" class ="btn btn-success" value="Ajouter">
                    <input id="filterPlan" placeholder="filtre">
                      <table id = tablestyle class="table table-striped">
                        <thead>
                          <tr>
                            <th>Id</th>
                            <th>Nom</th>
                            <%--<th>Type</th>--%>
                            <th>Masse</th>
                            <th>Diametre</th>
                            <th>Position x</th>
                            <th>Position y</th>
                            <th>Vitesse x</th>
                            <th>Vitesse y</th>
                            <th>Parent</th>
                          </tr>
                        </thead>
                        <tbody id="contentTable">
                          <c:forEach items="${systeminit}" var="corpsceleste">
                            <tr>
                              <td>${corpsceleste.id}</td>
                              <td>${corpsceleste.nomInit}</td>
                              <%--<td>${corpsceleste.DTYPE}</td>--%>
                              <td>${corpsceleste.masseInit}</td>
                              <td>${corpsceleste.diametreInit}</td>
                              <td>${corpsceleste.xInit}</td>
                              <td>${corpsceleste.yInit}</td>
                              <td>${corpsceleste.vxInit}</td>
                              <td>${corpsceleste.vyInit}</td>
                              
                              <td>${corpsceleste.parent.id} : ${corpsceleste.parent.nomInit}</td>
                              <td>
                                <input onclick="updateCorps(${corpsceleste.id},'${corpsceleste.nomInit}','${corpsceleste.masseInit}','${corpsceleste.diametreInit}','${corpsceleste.xInit}','${corpsceleste.yInit}','${corpsceleste.vxInit}','${corpsceleste.vyInit}','${corpsceleste.parent.id}')" type="button" class ="btn btn-warning" value="Modifier">
                               <c:if test = "${corpsceleste.id!='1'}">
                               <input onclick="addSat(${corpsceleste.id})" type="button" class ="btn btn-success" value="Ajout Satellite">
                               </c:if> 
                               <c:if test="${corpsceleste.id>'1'}">  
                                  <input onclick="deleteCorps(${corpsceleste.id},'${corpsceleste.nomInit}')" type="button" class ="btn btn-danger" value="Supprimer">
                                </c:if>    
                                    </td>
                                  </tr>
                                </c:forEach>

                              </tbody>
                            </table>

                            <div id="addFormPlan">
                               <h3>Ajouter nouvelle planete</h3>
                               <br>
                              <form action="addPlanet" method="post">
                              <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
                              <input type="hidden" name="type_form" value="POST">
                                <label for="add_nom">Nom :</label> <input required id="add_nom" name="nomInit" type="text" placeholder="Saisir le nom"><br>
                                <label for="add_masse">Masse :</label> <input required id="add_masse" name="masseInit" type="number" placeholder="Saisir la masse (kg)"><br>
                                <label for="add_taille">Diametre :</label> <input required id="add_diametre" name="diametreInit" type="number" placeholder="Saisir le diametre (km)"><br>
                                <label for="add_x">x0 :</label> <input required id="add_x" name="xInit" type="number" placeholder="Saisir la coordonnee x0 (km)"><br>
                                <label for="add_y">y0 :</label> <input required id="add_y" name="yInit" type="number" placeholder="Saisir la coordonnee y0 (km)"><br>
                                <label for="add_vx">vx0 :</label> <input required id="add_vx" name="vxInit" type="number" placeholder="Saisir la vitesse vx0 (km/s)"><br>
                                <label for="add_vy">vy0 :</label> <input required id="add_vy" name="vyInit" type="number" placeholder="Saisir la vitesse vy0 (km/s)"><br>
                                <label for="add_type"></label>  <input id="add_type" type="hidden" name="typeInit" value="Planete"><br>
                                <label for="add_id_parent"></label> <input id="add_id_parent" type="hidden" name="id_parent" value="1">
                                
								<input class ="btn btn-success" type="submit" name="ajouter" value="Ajouter">
                                </form>
                              </div>
                              
                              <div id="addFormSat">
                               <h3>Ajouter nouveau satellite</h3>
                               <br>
                              <form action="addSat" method="post">
                              <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
                              <input type="hidden" name="type_form" value="POST">
                                <label for="add_nom">Nom :</label> <input required id="sat_add_nom" name="nomInit" type="text" placeholder="Saisir le nom"><br>
                                <label for="add_masse">Masse :</label> <input required id="sat_add_masse" name="masseInit" type="number" placeholder="Saisir la masse (kg)"><br>
                                <label for="add_taille">Diametre :</label> <input required id="sat_add_diametre" name="diametreInit" type="number" placeholder="Saisir le diametre (km)"><br>
                                <label for="add_x">x0 :</label> <input required id="sat_add_x" name="xInit" type="number" placeholder="Saisir la coordonnee x0 (km)"><br>
                                <label for="add_y">y0 :</label> <input required id="sat_add_y" name="yInit" type="number" placeholder="Saisir la coordonnee y0 (km)"><br>
                                <label for="add_vx">vx0 :</label> <input required id="sat_add_vx" name="vxInit" type="number" placeholder="Saisir la vitesse vx0 (km/s)"><br>
                                <label for="add_vy">vy0 :</label> <input required id="sat_add_vy" name="vyInit" type="number" placeholder="Saisir la vitesse vy0 (km/s)"><br>
                                <label for="add_type"></label>  <input id="sat_add_type" type="hidden" name="typeInit" value="Satellite"><br>
                                <label for="add_id_parent"></label> <input id="sat_add_id_parent" type="hidden" name="id_parent">
                                
                                
								<input class ="btn btn-success" type="submit" name="ajouter" value="Ajouter">
                                </form>
                              </div>
                              
                              <div id="updateFormEtoile">
                                <h3>Modifier l'etoile</h3>
                                <form action="updateEtoile" method="post">
                                <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
                                    <input type="hidden" name="type_form" value="PUT">
									<input type="hidden" id="update_id_etoile" name="id">
                                      <label for="update_nom">Nom :</label> <input required id="etoile_update_nom" name="nomInit" type="text" placeholder="Saisir le nom"><br>
                                      <label for="update_masse">Masse :</label> <input required id="etoile_update_masse" name="masseInit" type="number" placeholder="Saisir la masse (km)"><br>
                                      <label for="update_taille">Diametre :</label> <input required id="etoile_update_diametre" name="diametreInit" type="number" placeholder="Saisir le diametre (km)"><br>
   									  <input class ="btn btn-warning" type="submit" name="modifier" value="Modifier">
                                      </form>
                                 </div> 
                      
                              <div id="updateFormPlan">
                                <h3>Modifier la planete</h3>
                                <form action="updateCorps" method="post">
                                <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
                                    <input type="hidden" name="type_form" value="PUT">
									<input type="hidden" id="update_id_plan" name="id">
                                      <label for="update_nom">Nom :</label> <input required id="update_nom" name="nomInit" type="text" placeholder="Saisir le nom"><br>
                                      <label for="update_masse">Masse :</label> <input required id="update_masse" name="masseInit" type="number" placeholder="Saisir la masse (km)"><br>
                                      <label for="update_taille">Diametre :</label> <input required id="update_diametre" name="diametreInit" type="number" placeholder="Saisir le diametre (km)"><br>
   									  <input type="hidden" id="update_type" value="Planete" name="typeInit">
   									  <input type="hidden" id="update_parent_id" name="id_parent" value="1">  
   									  <label for="update_x">x0 :</label> <input required id="update_x" name="xInit" type="number" placeholder="Saisir la coordonnee x0 (km)"><br>
                                      <label for="update_y">y0 :</label> <input required id="update_y" name="yInit" type="number" placeholder="Saisir la coordonnee y0 (km)"><br>
                                      <label for="update_vx">vx0 :</label> <input required id="update_vx" name="vxInit" type="number" placeholder="Saisir la vitesse vx0 (km/s)"><br>
                                      <label for="update_vy">vy0 :</label> <input required id="update_vy" name="vyInit" type="number" placeholder="Saisir la vitesse vy0 (km/s)"><br>                                      
                                      <input class ="btn btn-warning" type="submit" name="modifier" value="Modifier">
                                      </form>
                                 </div> 
                                    
                                <div id="updateFormSat">
                                <h3>Modifier le satellite</h3>
                                <form action="updateCorps" method="post">
                                <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
                                    <input type="hidden" name="type_form" value="PUT">
									<input type="hidden" id="sat_update_id_plan" name="id">
                                      <label for="update_nom">Nom :</label> <input required id="sat_update_nom" name="nomInit" type="text" placeholder="Saisir le nom"><br>
                                      <label for="update_masse">Masse :</label> <input required id="sat_update_masse" name="masseInit" type="number" placeholder="Saisir la masse (km)"><br>
                                      <label for="update_taille">Diametre :</label> <input required id="sat_update_diametre" name="diametreInit" type="number" placeholder="Saisir le diametre (km)"><br>
   									  <input type="hidden" id="sat_update_type" value="Satellite" name="typeInit">
   									  <label for="update_x">x0 :</label> <input required id="sat_update_x" name="xInit" type="number" placeholder="Saisir la coordonnee x0 (km)"><br>
                                      <label for="update_y">y0 :</label> <input required id="sat_update_y" name="yInit" type="number" placeholder="Saisir la coordonnee y0 (km)"><br>
                                      <label for="update_vx">vx0 :</label> <input required id="sat_update_vx" name="vxInit" type="number" placeholder="Saisir la vitesse vx0 (km/s)"><br>
                                      <label for="update_vy">vy0 :</label> <input required id="sat_update_vy" name="vyInit" type="number" placeholder="Saisir la vitesse vy0 (km/s)"><br>                                      
                                      
                                      <input class ="btn btn-warning" type="submit" name="modifier" value="Modifier">
                                      </form>
                                  </div>
                                    
                                    <br>
                                    <a style=color:yellow; href="menu">Retour</a>
                                  </div>
                                </div>
                              </div>


                              
                            </body>
                          </html>
							<script>
                    		 hideForms();

							 function hideForms(){
                           	  addFormSat.style.display="none";
                           	  addFormPlan.style.display="none";
                           	  updateFormSat.style.display="none";
                           	  updateFormPlan.style.display="none";
                           	  updateFormEtoile.style.display="none";  
                             }
                             
							
                              filterPlan.onkeyup=filtreAjax;

                              btnAddPlan.onclick=function()
                              {
                            	hideForms();
                            	addFormPlan.style.display="block";
                                
                              }
                              
                              function addSat(id)
                              {
                            	hideForms();
                            	addFormSat.style.display="block";
                                sat_add_id_parent.value=id;

                              }
                              
                              function updateCorps(id,nom,masse,diametre,x,y,vx,vy, parent_id)
                              {
                            	  console.log("id: "+id);
                            	  console.log("parent_id : "+parent_id);

                            	  if (parent_id==1){
                            		  updatePlan(id,nom,masse,diametre,x,y,vx,vy);
                            	  } else if (parent_id>1){
                            		  updateSat(id,nom,masse,diametre,x,y,vx,vy);
                            	  } else {
                            		  updateEtoile(id, nom, masse,diametre);	  
                            	  }
                              }	  
              
                              
                              function updateEtoile(id,nom,masse,diametre)
                              {
                            	
                            		hideForms();
                                	updateFormEtoile.style.display="block";

                                	etoile_update_nom.value=nom;
                                	etoile_update_masse.value=parseInt(masse);
                                	etoile_update_diametre.value=parseInt(diametre);
                                	
                              			
                              	}

                              function updatePlan(id,nom,masse,diametre,x,y,vx,vy, parent_id)
                              {
                            	
                            		hideForms();
                                	updateFormPlan.style.display="block";

                                	update_nom.value=nom;
                                	update_masse.value=parseInt(masse);
                                	update_diametre.value=parseInt(diametre);
                                	update_x.value=parseInt(x);
                                	update_y.value=parseInt(y);
                                	update_vx.value=parseInt(vx);
                                	update_vy.value=parseInt(vy);
                                	update_id_plan.value=id;
                                	update_parent_id=parent_id;
                              			
                              	}
                              
                               
                              function updateSat(id,nom,masse,diametre,x,y,vx,vy, parent_id)
                              {
                              			hideForms();
                                        updateFormSat.style.display="block";

                                        sat_update_nom.value=nom;
                                        sat_update_masse.value=parseInt(masse);
                                        sat_update_diametre.value=parseInt(diametre);
                                        sat_update_x.value=parseInt(x);
                                        sat_update_y.value=parseInt(y);
                                        sat_update_vx.value=parseInt(vx);
                                        sat_update_vy.value=parseInt(vy);
                                        sat_update_id_plan.value=id;
                                        sat_update_parent_id=parent_id;

                              			
                              	}
                              

                     
                              function deleteCorps(id, nom)
                              {
                            	  
                                if(confirm("Etes vous sur de vouloir supprimer "+nom+" ?"))
                                {
                                  $.ajax("deleteCorps", {
                                    type: "POST",
                                    data:
                                    {
                                      id : id,
                                      type_form:"DELETE"
                                    },
                                    success: function (resp) {
                                      location.href="Modification";
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
                                    search : filterPlan.value,
                                    page:"systeminit"
                                  },
                                  success: function (resp) {
                                    contentTable.innerHTML=resp;
                                  }
                                });
                              }


                              </script>