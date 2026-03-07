<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="personal" scope="request"/>
<c:set var="pageTitle"   value="Personal Administrativo" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="card">
  <div class="card-header d-flex align-items-center">
    <span class="fw-semibold me-auto"><i class="bi bi-briefcase me-2"></i>Personal Administrativo</span>
    <a href="${pageContext.request.contextPath}/personal?action=nuevo" class="btn btn-accent btn-sm"><i class="bi bi-plus-lg me-1"></i>Nuevo</a>
  </div>
  <div class="card-body p-0">
    <table class="table table-hover table-striped mb-0">
      <thead><tr><th>Nombre</th><th>Usuario</th><th>Cargo</th><th>Acciones</th></tr></thead>
      <tbody>
      <c:forEach var="pa" items="${personalList}">
        <tr>
          <td>${pa.nombreCompleto}</td>
          <td class="text-secondary">${pa.usuarioRol.usuario.nombreUsuario}</td>
          <td>${pa.cargo}</td>
          <td>
            <a href="${pageContext.request.contextPath}/personal?action=editar&id=${pa.usuarioRolId}" class="btn btn-sm btn-outline-warning py-0"><i class="bi bi-pencil"></i></a>
            <form method="post" action="${pageContext.request.contextPath}/personal" class="d-inline">
              <input type="hidden" name="action" value="eliminar"><input type="hidden" name="id" value="${pa.usuarioRolId}">
              <button type="submit" class="btn btn-sm btn-outline-danger py-0" onclick="return confirm('¿Eliminar?')"><i class="bi bi-trash"></i></button>
            </form>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty personalList}"><tr><td colspan="4" class="text-center text-secondary py-4">Sin personal</td></tr></c:if>
      </tbody>
    </table>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
