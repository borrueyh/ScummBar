<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>

<div class="menu">
	<spring:message code="menu.titulo" />
	<ul>
		<li><spring:url value="/restaurantes" var="homeUrl"
				htmlEscape="true" /> <a href="${homeUrl}"><spring:message
					code="menu.restaurantes" /></a></li>
		<li><spring:url value="/reservar" var="bookingUrl"
				htmlEscape="true" /> <a href="${bookingUrl}"><spring:message
					code="menu.reservar" /></a></li>
		<li><spring:url value="/cancelar" var="cancelUrl"
				htmlEscape="true" /> <a href="${cancelUrl}"><spring:message
					code="menu.cancelar" /></a></li>
	</ul>
</div>