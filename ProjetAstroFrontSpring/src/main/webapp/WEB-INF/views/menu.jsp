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



<style>
body {
	margin: 10px;
	background-color: black;
}

#header {
	display: flex;
	flex-wrap: wrap;
	height: 15%;
	background-image: url("../img/ciel.jpeg");
	background-size: 25%;
}

#center {
	display: flex;
	flex-wrap: wrap;
	height: 115%;
	margin-left: 450px;
	margin-right: 500px;
	text-align: center;
}

.row {
	width: 105%;
}

footer {
	display: flex;
	flex-wrap: wrap;
	height: 10%;
	width: 100%;
	background-image: url("../ciel.jpeg");
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
</style>


<body>
	<div id="site">
		<div id="header" class="container">
			<div class="row align-self-center">
				<nav class="nav nav-pills nav-fill">
					<div class="row ">
						<div class="col-3 align-self-center d-flex justify-content-center">
							<a id="link" class="nav-link" aria-current="page"
								href="initialisation">Simulation</a>
						</div>
						<div class="col-3 align-self-center d-flex justify-content-center">
							<a id="link" class="nav-link" href="create">Cr?ation
								d'un syst?me</a>
						</div>
						<div class="col-3 align-self-center d-flex justify-content-center">
							<a id="link" class="nav-link" href="Modification">Modification
								d'un syst?me</a>
						</div>
						<div class="col-3 align-self-center d-flex justify-content-center">
							<a id="link" class="nav-link" href="connect">D?connexion</a>
						</div>
					</div>
				</nav>
			</div>
</body>
</div>

</div>
<div id="center">
	<div id="text" class="box">
		<div>
			<br>
			<br>
			<h2>Bienvenue sur AJCstrolab !</h2>
			<p>L'idee de cette application est de vous permettre de voir
				l'evolution de votre propre systeme solaire! Pour cela, il vous
				suffira juste de creer votre etoile et les planetes qui l'entoure!
				Et ensuite d'apprecier la magie de la physique qui saura vous
				montrer comment chaque planete evolue autour de votre etoile!</p>
			<p>Choisissez un menu en tete de page pour commencer ou continuer
				votre aventure spatiale</p>
			<br> <img alt="photo systeme solaire" src="../img/photofunky.gif" />

		</div>
	</div>
</div>

<footer>
	<div class="boxFooter" id="fLeft">
		<a href="">Accueil</a> <a href="">Plan</a> <a href="">Contact</a> <a
			href="">CGU</a>
	</div>
	<div class="boxFooter boxFooterCenter" id="fRight">
		<span> Copyrigth &#8471; 2021, made by Group 2!</span>
	</div>
</footer>
