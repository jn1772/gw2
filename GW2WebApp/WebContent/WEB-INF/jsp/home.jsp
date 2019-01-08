<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.codebytes.core.Item"%>

<html>
<head>
<title>GreedyTP</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet" href='<c:url value="/resources/css/homeStyle.css"></c:url>' type="text/css">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="#">GreedyTP</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNav" aria-controls="navbarNav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item active"><a class="nav-link" href="#">Home
							<span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item active"><a class="nav-link" href="#">Items
							<span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item active"><a class="nav-link" href="#">About
							<span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item active"><a class="nav-link" href="#">Contact
							<span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item dropdown nav-item active"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownMenuLink" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> More Features </a>
						<div class="dropdown-menu"
							aria-labelledby="navbarDropdownMenuLink">
							<a class="dropdown-item" href="#">Action</a> <a
								class="dropdown-item" href="#">Another action</a> <a
								class="dropdown-item" href="#">Something else here</a>
						</div></li>
				</ul>
			</div>

		</div>
	</nav>


	<div class="container">
		<div class="jumbotron">
			<h2>${welcomeShort}</h2>
			<h4>${welcomeLong}</h4>
			<button class="btn btn-success btn-lg">Recommendations</button>
		</div>

		<p>
		Current Item Purchase Recommendations
		</p>
		
		<nav aria-label="Page navigation example">
			<ul class="pagination">
				<li class="page-item"><a class="page-link" href="#"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						<span class="sr-only">Previous</span>
				</a></li>
				<li class="page-item"><a class="page-link" href="#">1</a></li>
				<li class="page-item"><a class="page-link" href="#">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item"><a class="page-link" href="#">4</a></li>
				<li class="page-item"><a class="page-link" href="#">5</a></li>
				<li class="page-item"><a class="page-link" href="#">6</a></li>
				<li class="page-item"><a class="page-link" href="#">7</a></li>
				<li class="page-item"><a class="page-link" href="#">8</a></li>
				<li class="page-item"><a class="page-link" href="#"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
						class="sr-only">Next</span>
				</a></li>
			</ul>
			
		</nav>
			<table id="dtBasicExample"
				class="table table-striped table-bordered table-sm">
				<thead>
					<tr>
						<th class="th-sm">ID</th>
						<th class="th-sm">Name</th>
						<th class="th-sm">Type</th>
						<th class="th-sm">Rarity</th>
						<th class="th-sm">Buy Price</th>
						<th class="th-sm">Sell Price</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${items}" var="item">
						<tr>
							<td><c:out value="${item.id}" /></td>
							<td><img width="32px" height="32px" class="mw-5 mh-5"
								alt="Responsive image" src=<c:out value="${item.icon}" /> /> <c:out
									value="${item.name}" /></td>
							<td><c:out value="${item.type}" /></td>
							<td><c:out value="${item.rarity}" /></td>
							<td>
								<c:out value="${item.buyUnitPriceCF.gold} " /><img src='<c:url value="resources/images/goldcoin.png" />'/> 
								<c:out value="${item.buyUnitPriceCF.silver} " /><img src='<c:url value="resources/images/silvercoin.png" />'/>
								<c:out value="${item.buyUnitPriceCF.bronze} " /><img src='<c:url value="resources/images/coppercoin.png" />'/>
							</td>
							<td>
								<c:out value="${item.sellUnitPriceCF.gold} " /><img src='<c:url value="resources/images/goldcoin.png" />'/> 
								<c:out value="${item.sellUnitPriceCF.silver} " /><img src='<c:url value="resources/images/silvercoin.png" />'/>
								<c:out value="${item.sellUnitPriceCF.bronze} " /><img src='<c:url value="resources/images/coppercoin.png" />'/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<th class="th-sm">ID</th>
						<th class="th-sm">Name</th>
						<th class="th-sm">Type</th>
						<th class="th-sm">Rarity</th>
						<th class="th-sm">Buy Price</th>
						<th class="th-sm">Sell Price</th>
					</tr>
				</tfoot>
			</table>
		
		<nav aria-label="Page navigation example">
			<ul class="pagination">
				<li class="page-item"><a class="page-link" href="#"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						<span class="sr-only">Previous</span>
				</a></li>
				<li class="page-item"><a class="page-link" href="./rec=10">1</a></li>
				<li class="page-item"><a class="page-link" href="./rec=20">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item"><a class="page-link" href="#">4</a></li>
				<li class="page-item"><a class="page-link" href="#">5</a></li>
				<li class="page-item"><a class="page-link" href="#">6</a></li>
				<li class="page-item"><a class="page-link" href="#">7</a></li>
				<li class="page-item"><a class="page-link" href="#">8</a></li>
				<li class="page-item"><a class="page-link" href="#"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
						class="sr-only">Next</span>
				</a></li>
			</ul>
		</nav>
		</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>

	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
		integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
		crossorigin="anonymous"></script>
</body>
</html>