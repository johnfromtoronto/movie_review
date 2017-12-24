<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
<body>
	<h2>Write your review for: ${review.movie.movieTitle} : movieId:${review.movie.id}</h2>
	<h3>MOVIE::${movie}</h3>
	<h3>REVIEW::${review}</h3>
	<h3>REVIEW.movie::${review.movie}</h3>
	<h3>WRAPPERREVIEW.movie::${wrapperReview}</h3>
	<h3>wrapperReview.review.id::${wrapperReview.review.id}</h3>
	<h3>wrapperReview.review.personName::${wrapperReview.review.personName}</h3>
	<h3>wrapperReview.movie.id::${wrapperReview.movie.id}</h3>
	<h3>wrapperReview.movie.movieTitle::${wrapperReview.movie.movieTitle}</h3>
	<hr />
	<form:form action="processReviewForm" method="POST" modelAttribute="wrapperMovieReview">
		<form:hidden path="movie.id"/>
		<form:hidden path="movie.movieTitle"/>
		<form:hidden path="movie.director"/>
		<form:hidden path="movie.genres"/>
		<form:hidden path="review.id"/>
		User name:<form:input path="review.personName"/>
		<br />
		Comment:<br/>
		<form:textarea path="review.comment" rows = "5" cols = "30" />
		<br />
		Rating:
		<br />
		<input type="submit" value="Submit Review!" />
	</form:form>
</body>
</html>