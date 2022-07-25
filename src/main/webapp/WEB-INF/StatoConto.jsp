<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Stato Conto</title>
</head>
<body>
	<table border = "1">
		<c:forEach var="transazione" items = "${transazioni}" varStatus = "row">
			<tr>
				<td id = "id" name = "id">${transazione.id}</td>
				<td id = "importo" name = "importo">${transazione.importo}</td>
				<td id = "data" name = "data">${transazione.data}</td>
				<td id = "id_mit" name = "id_mit">${transazione.id_Mitt}</td>
				<td id = "id_dest" name = "id.dest">${transazione.id_Dest}</td>
				<td id = "causale" name = "causale">${transazione.causale}</td>				
			</tr>
		</c:forEach>
	</table>
	
	<br><br>	
	
	<form method = "POST" action = "doTransaction">
		<input type = "number" step = "any" id = "importo" name = "importo" value = "importo"/>
		<input type = "Date" id = "data" name = "data" value = "Data" />
		<input type = "number" min = "1" id = "id_dest" name = "id_dest" value = "Id_dest"/>
		<input type = "text" id = "causale" name = "causale" value = "Causale" />
		<input type = "submit" />
	</form>
</body>
</html>