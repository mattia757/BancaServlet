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
					<td>${conto.id}</td>
					<td>${conto.saldo}</td>
					<td><a href = "<%= getServletContext().getContextPath() %>/dettagliconto?idConto=${conto.id}"><button>Dettagli</button></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<p><a href="<c:url value="logout"/>">Log out</a></p>
</body>
</html>