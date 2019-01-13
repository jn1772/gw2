<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.codebytes.core.Item"%>

<nav aria-label="Page navigation example">
	<ul class="pagination">
		<li class="page-item"><a class="page-link" href="?page=${page_6 - 6 }"
			aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				<span class="sr-only">Previous</span>
		</a></li>
		
		<li class="page-item"><a class="page-link" href="?page=${page_1 }"><c:out value="${page_1} " /></a></li>
		<li class="page-item"><a class="page-link" href="?page=${page_2 }"><c:out value="${page_2} " /></a></li>
		<li class="page-item"><a class="page-link" href="?page=${page_3 }"><c:out value="${page_3} " /></a></li>
		<li class="page-item"><a class="page-link" href="?page=${page_4 }"><c:out value="${page_4} " /></a></li>
		<li class="page-item"><a class="page-link" href="?page=${page_5 }"><c:out value="${page_5} " /></a></li>
		<li class="page-item"><a class="page-link" href="?page=${page_6 }"><c:out value="${page_6} " /></a></li>
		<li class="page-item"><a class="page-link" href="?page=${page_7 }"><c:out value="${page_7} " /></a></li>
		<li class="page-item"><a class="page-link" href="?page=${page_8 }"><c:out value="${page_8} " /></a></li>
		<li class="page-item"><a class="page-link" href="?page=${page_9 }"><c:out value="${page_9} " /></a></li>
		<li class="page-item"><a class="page-link" href="?page=${page_10 }"><c:out value="${page_10} " /></a></li>
		<li class="page-item"><a class="page-link" href="?page=${page_11 }"><c:out value="${page_11} " /></a></li>
		
		<li class="page-item"><a class="page-link" href="?page=${page_6 + 6 }"
			aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
				class="sr-only">Next</span>
		</a></li>
	</ul>
	
</nav>