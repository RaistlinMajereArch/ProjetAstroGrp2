<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
	crossorigin="anonymous">

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Palette+Mosaic&display=swap"
	rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.servletContext.contextPath}" />
<style>
body {
	background-image: url("../img/photofunky.gif");
	background-repeat: no-repeat;
	background-size: 100%;
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

#Password {
	text-align: center;
	margin-top: 20px;
	margin-bottom: -2px;
}

#EnterPassword {
	text-align: center;
	margin-bottom: 10px;
}

#Login {
	text-align: center;
	margin-bottom: -2px;
}

#Subscribe {
	margin-top: -20px;
	margin-bottom: 10px;
	color: blueviolet;
	text-decoration: underline;
	cursor: pointer
}

#Compte {
	margin-top: 10px;
}

#Compte1 {
	margin-top: 10px;
}

#con {
	margin-top: -20px;
	margin-bottom: 10px;
	color: blueviolet;
	text-decoration: underline;
	cursor: pointer
}

.Boutons {
	justify-content: space-evenly;
	display: flex;
}

#ligne {
	background-color: white;
}


#EnterLogin {
	width: 100%;
}

#EnterPassword {
	width: 100%;
}

::-webkit-input-placeholder {
	text-align: center;
}
</style>
<%-- ${ctx}/ --%>
<body>
	<main class="container ">
		<form id="Sub" action="${ctx}/save" method="post" modelAttribute="compte">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
			<div class="form-group">
				<label for="login">login:</label> <input id="login"
					class="form-control" name="login" required="required"
					placeholder="saisir votre login">
			</div>
			<div class="form-group">
				<label for="password">mot de passe:</label> <input type="password"
					class="form-control" id="password" name="password"
					required="required" placeholder="saisir votre mot de passe">
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-outline-primary">envoyer</button>
				<a href="${ctx}" class="btn btn-outline-warning">annuler</a>
			</div>
		
<!-- 			<div class="row "> -->
<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 				<div -->
<!-- 					class="col-4 align-self-center d-flex justify-content-center border border-secondary rounded-top" -->
<!-- 					id="ligne"> -->
<!-- 					<h1 id="titre">Inscription</h1> -->
<!-- 				</div> -->
<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 			</div> -->
<!-- 			<div class="row "> -->
<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 				<div class="col-4 align-self-center middle"> -->
<!-- 					<div class="col-12 align-self-center d-flex justify-content-left "> -->
<!-- 						<p id="Login">Nom d'utilisateur</p> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 				<div class="col-4 align-self-center middle"> -->
<!-- 					<div -->
<!-- 						class="col-12 align-self-center d-flex justify-content-left border-right border-left "> -->
<!-- 						<input autofocus id="EnterLogin" required placeholder="Login" -->
<!-- 							input type="text" label="login"> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="col-4 align-self-center"></div> -->

<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 				<div class="col-4 align-self-center middle"> -->
<!-- 					<div -->
<!-- 						class="col-12 align-self-center d-flex justify-content-left border-right border-left"> -->
<!-- 						<p id="Password">Mot de passe</p> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 				<div class="col-4 align-self-center middle"> -->
<!-- 					<div -->
<!-- 						class="col-12 align-self-center d-flex justify-content-left border-right border-left"> -->
<!-- 						<input id="EnterPassword" required placeholder="Password" input -->
<!-- 							type="password"><br> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="col-4 align-self-center"></div> -->

<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 				<div class="col-4 align-self-center middle"> -->
<!-- 					<div class="Boutons"> -->
<!-- 						<button id="Envoyer" type="submit" class="btn btn-success" -->
<!-- 							href="menu">Envoyer</button> -->
<!-- 						<input class="btn btn-warning" type="reset" value="Effacer"> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="col-4 align-self-center"></div> -->

<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 				<div class="col-4 align-self-center border-left middle"> -->
<!-- 					<div class="col-12 align-self-center d-flex justify-content-left "> -->
<!-- 						<p id="Compte1">Vous avez deja un compte ?</p> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 				<div class="col-4 align-self-center bottom rounded-bottom"> -->
<!-- 					<div class="col-12 align-self-center d-flex justify-content-left"> -->
<!-- 						<p id="Con">Connectez vous</p> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="col-4 align-self-center"></div> -->
<!-- 			</div> -->
		</form>
	</main>
</body>
</html>