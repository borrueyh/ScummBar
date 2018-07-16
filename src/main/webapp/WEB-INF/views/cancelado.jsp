<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false"%>

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.7.3/themes/base/jquery-ui.css">

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<div class="body">
			<div class="alert alert-block alert-success">
				<c:choose>
					<c:when test="${cancelado}">
						<spring:message code="cancelar.reserva" />
					</c:when>
					<c:otherwise>
						<spring:message code="cancelar.nolocalizador" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>