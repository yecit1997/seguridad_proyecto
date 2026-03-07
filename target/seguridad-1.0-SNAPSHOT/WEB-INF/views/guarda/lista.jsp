<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="guardas" scope="request"/>
<c:set var="pageTitle"   value="Guardas" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="card">
  <div class="card-header d-flex align-items-center">
    <span class="fw-semibold me-auto"><i class="bi bi-person-badge me-2"></i>Guardas</span>
    <a href="${pageContext.request.contextPath}/guardas?action=nuevo" class="btn btn-accent btn-sm"><i class="bi bi-plus-lg me-1"></i>Nuevo</a>
  </div>
  <div class="card-body p-0">
    <div class="table-responsive">
      <table class="table table-hover table-striped mb-0">
        <thead><tr><th>Nombre</th><th>Usuario</th><th>Área Asignada</th><th>Supervisor</th><th>Rol</th><th>Acciones</th></tr></thead>
        <tbody>
        <c:forEach var="g" items="${guardas}">
          <tr>
            <td>${g.usuarioRol.usuario.persona.nombre} ${g.usuarioRol.usuario.persona.apellido}</td>
            <td class="text-secondary">${g.usuarioRol.usuario.nombreUsuario}</td>
            <td>${g.areaAsignada}</td>
            <td>${g.supervisor != null ? g.supervisor.usuarioRolId : '—'}</td>
            <td><span class="badge bg-secondary">${g.usuarioRol.rol.nombre}</span></td>
            <td>
              <a href="${pageContext.request.contextPath}/guardas?action=editar&id=${g.usuarioRolId}" class="btn btn-sm btn-outline-warning py-0"><i class="bi bi-pencil"></i></a>
              <form method="post" action="${pageContext.request.contextPath}/guardas" class="d-inline">
                <input type="hidden" name="action" value="eliminar"><input type="hidden" name="id" value="${g.usuarioRolId}">
                <button type="submit" class="btn btn-sm btn-outline-danger py-0" onclick="return confirm('¿Eliminar guarda?')"><i class="bi bi-trash"></i></button>
              </form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty guardas}"><tr><td colspan="6" class="text-center text-secondary py-4">Sin guardas registrados</td></tr></c:if>
        </tbody>
      </table>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
