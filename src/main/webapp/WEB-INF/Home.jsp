<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>

<script>
</script>

</head>
<body>
	<table border = "1">
		<thead>
			<tr>
				<th>Id Conto</th>
				<th>Saldo Conto</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="conto" items = "${conti}" varStatus = "row">
				<tr>
					<td id = "id" name = "id">${conto.id}</td>
					<td id = "saldo" name = "saldo">${conto.saldo}</td>
					<td><a href = "dettagliconto?idConto=${conto.id}">Dettagli</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	 <p><a href="<c:url value="aggiungiconto"/>">Aggiungi Conto</a></p> 
	<!-- <form method ="POST" action = "aggiungiconto">
		<input type = "text" id = "saldoconto" name = "saldoconto" />
		<input type = "submit" />-->
	</form>
	<p><a href="<c:url value="logout"/>">Log out</a></p>
</body>
</html>