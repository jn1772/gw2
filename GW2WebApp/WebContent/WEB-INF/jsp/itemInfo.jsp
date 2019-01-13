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
	<jsp:include page="navbar.jsp"/>
	
	<div class="container">
	
		<div class="page-header">
			<h2>
				<img src="${item.icon}" width="50px" height="50px"></img>
				${item.name}
			</h2>
		</div>
		
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-6" style="padding-left:0;">
		
					<div class="card mb-3">
  					<div class="card-header bg-success text-light">Information</div>
  					<div class="card-body">
    					<p class="card-text">
    						${item.description}
    					</p>
    					<table id="dtBasicExample"	class="table table-striped table-responsive-md table-bordered table-md">
							<tbody>
								<tr>
									<td>Name</td>
									<td>${item.name}</td>
								</tr>
								<tr><td>Level</td><td>${item.level}</td></tr>
								<tr><td>Type</td><td>${item.type}</td></tr>
								<tr><td>Rarity</td><td>${item.rarity.toString()}</td></tr>
								<tr><td>ID</td><td>${item.id}</td></tr>
							</tbody>
						</table>
  					</div>
					</div>
			</div>
			
				<div class="col-md-6" style="padding-right:0">
					
					<div class="card mb-3">
  					<div class="card-header bg-danger text-light">Trade Post Listings</div>
  					<div class="card-body">
    				<p class="card-text">
    					${item.description}
    				</p>
    				<table id="dtBasicExample"	class="table table-striped table-responsive-md table-bordered table-md">
						<tbody>
							<tr>
								<td>Name</td>
								<td>${item.name}</td>
							</tr>
							<tr><td>Level</td><td>${item.level}</td></tr>
							<tr><td>Type</td><td>${item.type}</td></tr>
							<tr><td>Rarity</td><td>${item.rarity.toString()}</td></tr>
							<tr><td>ID</td><td>${item.id}</td></tr>
						</tbody>
					</table>
  					</div>
  					</div>
			</div>
			
			
		</div>
		
		<div class="row">
		<div class="col-md-12" style="padding-left:0;padding-right:0;">
					
					<div class="card mb-3">
  					<div class="card-header bg-info text-light">Price Chart</div>
  					<div class="card-body">
    				<p class="card-text">
    					${item.description}
    				</p>
    				<table id="dtBasicExample"	class="table table-striped table-responsive-md table-bordered table-md">
						<tbody>
							<tr>
								<td>Name</td>
								<td>${item.name}</td>
							</tr>
							<tr><td>Level</td><td>${item.level}</td></tr>
							<tr><td>Type</td><td>${item.type}</td></tr>
							<tr><td>Rarity</td><td>${item.rarity.toString()}</td></tr>
							<tr><td>ID</td><td>${item.id}</td></tr>
						</tbody>
					</table>
  					</div>
  					</div>
			</div>
		</div>
		
	
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