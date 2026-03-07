<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="personas" scope="request"/>
<c:set var="pageTitle"   value="Personas" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="card">
  <div class="card-header d-flex align-items-center">
    <span class="fw-semibold me-auto"><i class="bi bi-people me-2"></i>Personas</span>
    <a href="${pageContext.request.contextPath}/personas?action=nuevo" class="btn btn-accent btn-sm"><i class="bi bi-plus-lg me-1"></i>Nueva</a>
  </div>
  <div class="card-body p-0">
    <div class="table-responsive">
      <table class="table table-hover table-striped mb-0">
        <thead><tr><th>DNI</th><th>Nombre</th><th>Apellido</th><th>Correo</th><th>Teléfono</th><th>Acciones</th></tr></thead>
        <tbody>
        <c:forEach var="p" items="${personas}">
          <tr>
            <td>${p.dni}</td><td>${p.nombre}</td><td>${p.apellido}</td>
            <td class="small text-secondary">${p.correo}</td><td class="small">${p.telefono}</td>
            <td>
              <a href="${pageContext.request.contextPath}/personas?action=editar&id=${p.idPersona}" class="btn btn-sm btn-outline-warning py-0"><i class="bi bi-pencil"></i></a>
              <form method="post" action="${pageContext.request.contextPath}/personas" class="d-inline">
                <input type="hidden" name="action" value="eliminar"><input type="hidden" name="id" value="${p.idPersona}">
                <button type="submit" class="btn btn-sm btn-outline-danger py-0" onclick="return confirm('¿Eliminar persona?')"><i class="bi bi-trash"></i></button>
              </form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty personas}"><tr><td colspan="6" class="text-center text-secondary py-4">Sin personas registradas</td></tr></c:if>
        </tbody>
      </table>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
