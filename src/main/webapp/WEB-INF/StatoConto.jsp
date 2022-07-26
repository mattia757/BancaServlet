<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Stato Conto</title>
</head>
<body style = "text-align: center;">
	<%
		Integer id_conto = Integer.parseInt(request.getParameter("idConto"));
		if(id_conto != null)
		{
			System.out.println(id_conto);
		}
		else{
			System.out.println("ciao2");
		}
	%>
	<h1>Dettagli conto <%=id_conto%></h1>
	<table border = "1" style = "margin-left: 43%;">
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
	<h1>Pianifica un Trasferimento</h1>
	
	<form method = "POST" action = "dotransaction">
		Importo: <input type = "number" step = "any" id = "importo" name = "importo" value = "importo" required/> <br><br>
		Data: <input type = "datetime-local" id = "data" name = "data" value = "Data" required/> <br><br> 
		Importo: <input type="number" min="1" id="id_dest" name="id_dest" value="Id_dest" required /> <br><br>
		Causale: <input type = "text" id = "causale" name = "causale" value = "Causale" required/> <br><br>
		<input type = "hidden" value= "<%=id_conto%>" name = "idconto"> 
		<input type = "submit" />

	</form>
</body>
</html>