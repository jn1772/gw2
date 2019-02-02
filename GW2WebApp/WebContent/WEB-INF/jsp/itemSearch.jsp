<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.codebytes.base.Item"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet"
	href='<c:url value="/resources/css/homeStyle.css"></c:url>'
	type="text/css">

<meta charset="UTF-8">
<title>Insert title here</title>




</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="container">
		<div class="jumbotron">
			<form id="itemQueryParams" action="/GW2TP/itemSearch"
				method="GET">
				<div class="form-row">
					<div class="form-group col-md-8">
						<input id="name" name="name" type="text" class="form-control"
							placeholder="Name">
					</div>
				</div>

				<div class="form-row">
					<div class="form-group col-md-4">
						<select id="type" name="type" class="form-control">
							<option selected>Type</option>
							<option>Armor</option>
							<option>Back</option>
							<option>Bag</option>
							<option>Consumable</option>
							<option>Container</option>
							<option>CraftingMaterial</option>
							<option>Gathering</option>
							<option>Gizmo</option>
							<option>MiniPet</option>
							<option>Tool</option>
							<option>Trait</option>
							<option>Trinket</option>
							<option>Trophy</option>
							<option>UgradeComponent</option>
							<option>Weapon</option>
						</select>
					</div>


					<div class="form-group col-md-4">
						<select id="rarity" name="rarity" class="form-control">
							<option selected>Rarity</option>
							<option>Armor</option>
							<option>Junk</option>
							<option>Basic</option>
							<option>Fine</option>
							<option>Masterwork</option>
							<option>Rare</option>
							<option>Exotic</option>
							<option>Ascended</option>
							<option>Legendary</option>
						</select>
					</div>
				</div>


				<div class="form-row">
					<div class="form-group input-group col-lg-4 col-md-6">
						<div class="input-group-prepend">
							<div class="input-group-text">Buy Price</div>
						</div>
						<input id="buyPriceMin" name="buyPriceMin" type="number"
							class="form-control" placeholder="Min"> <input
							id="buyPriceMax" name="buyPriceMax" type="number"
							class="form-control" placeholder="Max">
					</div>

					<div class="form-group input-group col-lg-4 col-md-6">
						<div class="input-group-prepend">
							<div class="input-group-text">Sell Price</div>
						</div>
						<input id="sellPriceMin" name="sellPriceMin" type="number"
							class="form-control" placeholder="Min"> <input
							id="sellPriceMax" name="sellPriceMax" type="number"
							class="form-control" placeholder="Max">
					</div>


				</div>

				<div class="form-row">
					<div class="form-group input-group col-lg-4 col-md-6">
						<div class="input-group-prepend">
							<div class="input-group-text">Demand</div>
						</div>
						<input id="demandMin" name="demandMin" type="number"
							class="form-control" placeholder="Min"> <input
							id="demandMax" name="demandMax" type="number"
							class="form-control" placeholder="Max">
					</div>

					<div class="form-group input-group col-lg-4 col-md-6">
						<div class="input-group-prepend">
							<div class="input-group-text">Supply</div>
						</div>
						<input id="supplyMin" name="supplyMin" type="number"
							class="form-control" placeholder="Min"> <input
							id="supplyMax" name="supplyMax" type="number"
							class="form-control" placeholder="Max">
					</div>
				</div>

				<div class="form-row">
					<div class="form-group input-group col-lg-4 col-md-6">
						<div class="input-group-prepend">
							<div class="input-group-text">Profit</div>
						</div>
						<input id="profitMin" name="profitMin" type="number"
							class="form-control" placeholder="Min"> <input
							id="profitMax" name="profitMax" type="number"
							class="form-control" placeholder="Max">
					</div>
				</div>

				<input class="btn btn-primary" type="submit" value="Submit">
				<input class="btn btn-primary" type="reset" value="Reset">
			</form>
		</div>
		<p>Items Matching Criteria:</p>
		<jsp:include page="pageNav.jsp"/>

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
				<c:forEach items="${items}" begin="${begin}" end="${end}" var="item">
					<tr>
						<td><c:out value="${item.id}" /></td>
							<td>
								<img width="32px" height="32px" class="mw-5 mh-5" alt="${item.name}" src=<c:out value="${item.icon}" /> /> 
									<a href="/GW2TP?item=${item.id}">	<c:out value="${item.name}" /></a>
							</td>
						<td><c:out value="${item.type}" /></td>
						<td><c:out value="${item.rarity}" /></td>
						<td><c:out value="${item.buyUnitPriceCF.gold} " /><img
							src='<c:url value="resources/images/goldcoin.png" />' /> <c:out
								value="${item.buyUnitPriceCF.silver} " /><img
							src='<c:url value="resources/images/silvercoin.png" />' /> <c:out
								value="${item.buyUnitPriceCF.bronze} " /><img
							src='<c:url value="resources/images/coppercoin.png" />' /></td>
						<td><c:out value="${item.sellUnitPriceCF.gold} " /><img
							src='<c:url value="resources/images/goldcoin.png" />' /> <c:out
								value="${item.sellUnitPriceCF.silver} " /><img
							src='<c:url value="resources/images/silvercoin.png" />' /> <c:out
								value="${item.sellUnitPriceCF.bronze} " /><img
							src='<c:url value="resources/images/coppercoin.png" />' /></td>
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

		<jsp:include page="pageNav.jsp"/>
	</div>

	<script src="resources/js/itemSearch.js"></script>
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