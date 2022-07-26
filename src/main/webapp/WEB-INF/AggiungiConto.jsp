<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Aggiungi Conto</title>
</head>
<body style="text-align: center;">
	<h1>Crea un conto</h1>
	<h3>Inserisci il saldo del conto da creare</h3>
	<form method = "POST" action = "aggiungiconto">
		<input type="number" name="saldo" id="saldo" min="1" max="1000000000">
		<input type = "submit" />
	</form> 
</body>
</html>