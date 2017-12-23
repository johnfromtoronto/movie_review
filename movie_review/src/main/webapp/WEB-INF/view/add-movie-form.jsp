<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
	<title>Add movie form</title>
	<style type="text/css">
		.errorColor {color:red;}
	</style>
</head>
<body>
	<h2>Add movie form!</h2>
	<hr />
	<h3>Asterisk fields (*) are required</h3>
	<form:form action="processMovieForm" method="POST" modelAttribute="movie">
		Movie id :
		${movie.id}
		<form:hidden path="id"/>
		<br />
		Movie title:(*)${movie.movieTitle}
		<form:input path="movieTitle" />
		<form:errors path="movieTitle" cssClass="errorColor"/>
		<br />
		Director:(*)${movie.director}
		<form:input path="director" />
		<form:errors path="director" cssClass="errorColor"/>
		<br />
		<br />
		Genre:||
		<br />
		<c:forEach var="temp" items="${movie.genres}">
			${temp} 
			<br />
		</c:forEach>

		<br />

		<br/>
		<c:choose>
			<c:when test="${not empty isHorrorSet}">
			aHorror:<form:checkbox path="genres" value="Horror" checked="checked"/>
			</c:when>
			<c:otherwise>
			bHorror:<form:checkbox path="genres" value="Horror"/>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${not empty isComedySet}">
			aComedy:<form:checkbox path="genres" value="Comedy" checked="checked"/>
			</c:when>
			<c:otherwise>
			bComedy:<form:checkbox path="genres" value="Comedy"/>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${not empty isActionSet}">
			aAction:<form:checkbox path="genres" value="Action" checked="checked"/>
			</c:when>
			<c:otherwise>
			bAction:<form:checkbox path="genres" value="Action"/>
			</c:otherwise>
		</c:choose>
		<br />
		<input type="submit" value="Submit movie!" />
	</form:form>
	
</body>
</html>