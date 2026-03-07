<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="supervisores" scope="request"/>
<c:set var="pageTitle"   value="Supervisores" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="card">
  <div class="card-header d-flex align-items-center">
    <span class="fw-semibold me-auto"><i class="bi bi-person-check me-2"></i>Supervisores</span>
    <a href="${pageContext.request.contextPath}/supervisores?action=nuevo" class="btn btn-accent btn-sm"><i class="bi bi-plus-lg me-1"></i>Nuevo</a>
  </div>
  <div class="card-body p-0">
    <div class="table-responsive">
      <table class="table table-hover table-striped mb-0">
        <thead><tr><th>Nombre</th><th>Usuario</th><th>Rol</th><th>Fecha Ascenso</th><th>Acciones</th></tr></thead>
        <tbody>
        <c:forEach var="s" items="${supervisores}">
          <tr>
            <td>${s.nombreCompleto}</td>
            <td class="text-secondary">${s.usuarioRol.usuario.nombreUsuario}</td>
            <td><span class="badge bg-secondary">${s.usuarioRol.rol.nombre}</span></td>
            <td>${s.fechaAscenso != null ? s.fechaAscenso : '—'}</td>
            <td>
              <a href="${pageContext.request.contextPath}/supervisores?action=editar&id=${s.usuarioRolId}" class="btn btn-sm btn-outline-warning py-0"><i class="bi bi-pencil"></i></a>
              <form method="post" action="${pageContext.request.contextPath}/supervisores" class="d-inline">
                <input type="hidden" name="action" value="eliminar"><input type="hidden" name="id" value="${s.usuarioRolId}">
                <button type="submit" class="btn btn-sm btn-outline-danger py-0" onclick="return confirm('¿Eliminar supervisor?')"><i class="bi bi-trash"></i></button>
              </form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty supervisores}"><tr><td colspan="5" class="text-center text-secondary py-4">Sin supervisores</td></tr></c:if>
        </tbody>
      </table>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
