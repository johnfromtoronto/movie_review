<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>List of reviews</title>
</head>
<body>
	<h2>List of reviews for the movie: ${movie.movieTitle} directed by: ${movie.director}</h2>
	<hr />
	Movie.reviews: ${movie.reviews}
	<hr />
	<c:url var="addReviewLink" value="/movie/addReview">
		<c:param name="movieId" value="${movie.id}"/>
	</c:url>
	<a href="${addReviewLink}">Add a new review</a>
	
	<c:forEach var="tempReview" items="${movie.reviews}">
	<hr /> 
		id: ${tempReview.id}
		|${tempReview.personName} has wrote:
		${tempReview.comment}
	</c:forEach>
	<hr />
	<c:url var="viewReviewsLink" value="/movie/list">
		<c:param name="movieId" value="${movie.id}"/>
	</c:url>
	
	<a href="${viewReviewsLink}">Back to movies list</a>
</body>

</html>