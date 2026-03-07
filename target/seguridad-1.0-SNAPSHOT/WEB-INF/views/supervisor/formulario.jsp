<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="supervisores" scope="request"/>
<c:set var="pageTitle"   value="${empty supervisor ? 'Nuevo Supervisor' : 'Editar Supervisor'}" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="row justify-content-center"><div class="col-lg-5">
<div class="card">
  <div class="card-header fw-semibold"><i class="bi bi-person-check me-2"></i>${empty supervisor ? 'Registrar Supervisor' : 'Editar Supervisor'}</div>
  <div class="card-body">
    <form method="post" action="${pageContext.request.contextPath}/supervisores">
      <input type="hidden" name="action" value="${empty supervisor ? 'crear' : 'actualizar'}">
      <c:if test="${not empty supervisor}"><input type="hidden" name="usuarioRolId" value="${supervisor.usuarioRolId}"></c:if>
      <c:if test="${empty supervisor}">
        <div class="mb-3">
          <label class="form-label">Usuario / Rol <span class="text-danger">*</span></label>
          <select name="usuarioRolId" class="form-select" required>
            <option value="">Seleccionar...</option>
            <c:forEach var="ur" items="${usuariosRol}">
              <option value="${ur.idUsuarioRol}">${ur.usuario.persona.nombre} ${ur.usuario.persona.apellido} — ${ur.rol.nombre}</option>
            </c:forEach>
          </select>
        </div>
      </c:if>
      <div class="mb-3">
        <label class="form-label">Fecha de Ascenso</label>
        <input type="date" name="fechaAscenso" class="form-control" value="${supervisor.fechaAscenso}">
      </div>
      <div class="d-flex gap-2 justify-content-end">
        <a href="${pageContext.request.contextPath}/supervisores" class="btn btn-outline-secondary">Cancelar</a>
        <button type="submit" class="btn btn-accent"><i class="bi bi-check-lg me-1"></i>Guardar</button>
      </div>
    </form>
  </div>
</div>
</div></div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
