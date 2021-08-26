<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
	integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
	integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
	crossorigin="anonymous"></script>
  <%@ page import="astro.metier.Position"%>
  <%@ page import="DAOjpa.DAOPositionsjpa"%>
  <%@ page import="java.util.List"%>
<style>
body {
	margin: 10px;
	background-color: black;
}

#header {
	display: flex;
	flex-wrap: wrap;
	height: 15%;
	background-image: url("img/ciel.jpeg");
	background-size: 25%;
}

#center {
	display : flex;
	flex-wrap:wrap;
	height:74.5%;
	text-align:center;
}

.row {
	width: 105%;
}

footer {
	display: flex;
	flex-wrap: wrap;
	height: 10%;
	width: 100%;
	background-image: url("img/ciel.jpeg");
}

#text {
	display: flex;
	flex-wrap: wrap;
	color: lightyellow;
}

#fRight {
	color: lightyellow;
}

#link {
	color: white;
}

#active-link {
	color: white;
	background-color: #b1bdd4;
}

a {
	color: white;
}

.boxFooter {
	display: inline-block;
	position: relative;
	top: 15px;
}

.boxFooterCenter {
	left: 65%;;
}

#orbite {
	height: 16384px;
	width: 16384px;
	background-color: white;
	display: none;
}
</style>


<body>
	<div id="site">
		<div id="header" class="container">
			<div class="row align-self-center">
				<nav class="nav nav-pills nav-fill">
					<div class="row ">
						<div class="col-4 align-self-center d-flex justify-content-center"></div>
						<div class="col-4 align-self-center d-flex justify-content-center">
							<a id="link" class="nav-link" href="create.html">Retour</a>
						</div>
						<div class="col-4 align-self-center d-flex justify-content-center"></div>
					</div>
				</nav>
			</div>
		</div>
	</div>
	<div id="center">
		<canvas id="orbite"></canvas>
	</div>
</body>
<footer>
	<div class="boxFooter" id="fLeft">
		<a href="">Accueil</a> <a href="">Plan</a> <a href="">Contact</a> <a
			href="">CGU</a>
	</div>
	<div class="boxFooter boxFooterCenter" id="fRight">
		<span> Copyrigth &#8471; 2021, made by Group 2!</span>
	</div>
</footer>

<script>
  var couleurs = ['black','red','blue','green','gray','blueviolet','aqua','brown', 'chartreuse', 'chocolate', 'coral','crimson','cyan'];
  var cpt=0;
  //DAOPositionjpa daoP = new DAOPositionjpa();
var positions = [
                  [{"x":0,"y":0},{"x":2000,"y":2000}],
                  [{"x":0,"y":20},{"x":0,"y":19},{"x":0,"y":18},{"x":0,"y":17},{"x":0,"y":16},{"x":0,"y":15},{"x":0,"y":14},{"x":0,"y":13},{"x":0,"y":12},{"x":0,"y":11}]
                ];

 //for (var corps in systeme ="${Systeme}"){
//   positions.add(daoP.findByIdCorps(corps.id));
//  }

  for (var pos in positions){
    traj(positions[pos],couleurs[cpt]);
    console.log(couleurs[cpt]);
    cpt++;
  }
	//orbite.style.transform="scale(0.1)"
	orbite.style.width=window.innerWidth;
	orbite.style.height=window.innerHeight*0.7;
	orbite.style.display="block";

  function traj(pos,couleur){
    var objet = document.getElementById('orbite');
    var dessin = objet.getContext('2d');
    dessin.beginPath();
    dessin.moveTo(pos[0].x, pos[0].y);
    console.log(pos[0]);
    for (var p in pos){
      dessin.lineTo(pos[p].x, pos[p].y);
    }
    dessin.strokeStyle =couleur;
    dessin.lineWidth = 1;
    dessin.stroke();
    dessin.closePath();
  }

</script>
