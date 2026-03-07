<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="usuarios" scope="request"/>
<c:set var="pageTitle"   value="${empty usuario ? 'Nuevo Usuario' : 'Editar Usuario'}" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="row justify-content-center"><div class="col-lg-6">
<div class="card">
  <div class="card-header fw-semibold"><i class="bi bi-person-gear me-2"></i>${empty usuario ? 'Crear Usuario' : 'Editar Usuario'}</div>
  <div class="card-body">
    <form method="post" action="${pageContext.request.contextPath}/usuarios">
      <input type="hidden" name="action" value="${empty usuario ? 'crear' : 'actualizar'}">
      <c:if test="${not empty usuario}"><input type="hidden" name="id" value="${usuario.idUsuario}"></c:if>
      <div class="mb-3">
        <label class="form-label">Persona vinculada <span class="text-danger">*</span></label>
        <select name="personaId" class="form-select" required>
          <option value="">Seleccionar persona...</option>
          <c:forEach var="p" items="${personas}">
            <option value="${p.idPersona}" ${usuario.persona.idPersona == p.idPersona ? 'selected' : ''}>${p.nombre} ${p.apellido} — ${p.dni}</option>
          </c:forEach>
        </select>
      </div>
      <div class="mb-3">
        <label class="form-label">Nombre de usuario <span class="text-danger">*</span></label>
        <input type="text" name="nombreUsuario" class="form-control" value="${usuario.nombreUsuario}" required>
      </div>
      <div class="mb-3">
        <label class="form-label">Contraseña <span class="text-danger">*</span></label>
        <input type="password" name="contrasena" class="form-control" placeholder="${not empty usuario ? 'Dejar vacío para no cambiar' : ''}" ${empty usuario ? 'required' : ''}>
      </div>
      <div class="mb-3">
        <label class="form-label">Estado</label>
        <select name="status" class="form-select">
          <option value="1" ${usuario.status ? 'selected' : ''}>Activo</option>
          <option value="0" ${not usuario.status ? 'selected' : ''}>Inactivo</option>
        </select>
      </div>
      <div class="d-flex gap-2 justify-content-end">
        <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-outline-secondary">Cancelar</a>
        <button type="submit" class="btn btn-accent"><i class="bi bi-check-lg me-1"></i>Guardar</button>
      </div>
    </form>
  </div>
</div>
</div></div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
