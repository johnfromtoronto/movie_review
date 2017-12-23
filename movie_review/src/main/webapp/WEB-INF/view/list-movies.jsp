<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
	<h2>Movies available</h2>
	<hr />
	<!-- <a href="addMovie">Add new movie</a> -->
	<input type="button" value="add new movie" 
	onclick="window.location.href='addMovie'; return false;" />
	
	<hr />
	<c:forEach var="tempMovie" items="${movies}">
		<c:url var="updateMovieLink" value="/movie/updateMovie">
			<c:param name="movieId" value="${tempMovie.id}"/>
		</c:url>
		<c:url var="deleteMovieLink" value="/movie/deleteMovie">
			<c:param name="movieId" value="${tempMovie.id}"/>
		</c:url>
		<c:url var="viewReviewsLink" value="/movie/viewReviews">
			<c:param name="movieId" value="${tempMovie.id}"/>
		</c:url>
	
		${tempMovie.movieTitle} directed by 
		${tempMovie.director}
		Genres: 
		<c:forEach var="tempGenre" items="${tempMovie.genres}">
			${tempGenre.movieGenre}
		</c:forEach>
		<br />
		<a href="${viewReviewsLink}">view reviews</a>||
		<a href="${updateMovieLink}">Update movie</a>||		
		<a href="${deleteMovieLink}"
		onclick="if (!(confirm('Are you sure you want to delete: ${tempMovie.movieTitle}?'))) return false;" >delete movie</a>		
		<hr />
	</c:forEach>
	<a href="home">Back to home page</a>
</body>
</html>