<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="usuarios" scope="request"/>
<c:set var="pageTitle"   value="Usuarios" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="card">
  <div class="card-header d-flex align-items-center">
    <span class="fw-semibold me-auto"><i class="bi bi-person-gear me-2"></i>Usuarios</span>
    <a href="${pageContext.request.contextPath}/usuarios?action=nuevo" class="btn btn-accent btn-sm"><i class="bi bi-plus-lg me-1"></i>Nuevo</a>
  </div>
  <div class="card-body p-0">
    <div class="table-responsive">
      <table class="table table-hover table-striped mb-0">
        <thead><tr><th>Usuario</th><th>Persona</th><th>DNI</th><th>Estado</th><th>Acciones</th></tr></thead>
        <tbody>
        <c:forEach var="u" items="${usuarios}">
          <tr>
            <td>${u.nombreUsuario}</td>
            <td>${u.persona.nombre} ${u.persona.apellido}</td>
            <td class="small text-secondary">${u.persona.dni}</td>
            <td><span class="badge ${u.status ? 'bg-success' : 'bg-danger'}">${u.status ? 'Activo' : 'Inactivo'}</span></td>
            <td>
              <a href="${pageContext.request.contextPath}/usuarios?action=editar&id=${u.idUsuario}" class="btn btn-sm btn-outline-warning py-0"><i class="bi bi-pencil"></i></a>
              <form method="post" action="${pageContext.request.contextPath}/usuarios" class="d-inline">
                <input type="hidden" name="action" value="eliminar"><input type="hidden" name="id" value="${u.idUsuario}">
                <button type="submit" class="btn btn-sm btn-outline-danger py-0" onclick="return confirm('¿Eliminar usuario?')"><i class="bi bi-trash"></i></button>
              </form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty usuarios}"><tr><td colspan="5" class="text-center text-secondary py-4">Sin usuarios</td></tr></c:if>
        </tbody>
      </table>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
