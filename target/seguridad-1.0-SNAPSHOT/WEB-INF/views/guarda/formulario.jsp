<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="guardas" scope="request"/>
<c:set var="pageTitle"   value="${empty guarda ? 'Nuevo Guarda' : 'Editar Guarda'}" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="row justify-content-center"><div class="col-lg-6">
<div class="card">
  <div class="card-header fw-semibold"><i class="bi bi-person-badge me-2"></i>${empty guarda ? 'Registrar Guarda' : 'Editar Guarda'}</div>
  <div class="card-body">
    <form method="post" action="${pageContext.request.contextPath}/guardas">
      <input type="hidden" name="action" value="${empty guarda ? 'crear' : 'actualizar'}">
      <c:if test="${not empty guarda}"><input type="hidden" name="usuarioRolId" value="${guarda.usuarioRolId}"></c:if>
      <c:if test="${empty guarda}">
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
        <label class="form-label">Área Asignada <span class="text-danger">*</span></label>
        <input type="text" name="areaAsignada" class="form-control" value="${guarda.areaAsignada}" required>
      </div>
      <div class="mb-3">
        <label class="form-label">Supervisor</label>
        <select name="supervisorId" class="form-select">
          <option value="">Sin supervisor</option>
          <c:forEach var="s" items="${supervisores}">
            <option value="${s.usuarioRolId}" ${guarda.supervisorId == s.usuarioRolId ? 'selected' : ''}>${s.nombreCompleto}</option>
          </c:forEach>
        </select>
      </div>
      <div class="d-flex gap-2 justify-content-end">
        <a href="${pageContext.request.contextPath}/guardas" class="btn btn-outline-secondary">Cancelar</a>
        <button type="submit" class="btn btn-accent"><i class="bi bi-check-lg me-1"></i>Guardar</button>
      </div>
    </form>
  </div>
</div>
</div></div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
