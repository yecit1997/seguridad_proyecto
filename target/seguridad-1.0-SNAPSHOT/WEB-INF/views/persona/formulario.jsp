<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="personas" scope="request"/>
<c:set var="pageTitle"   value="${empty persona ? 'Nueva Persona' : 'Editar Persona'}" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="row justify-content-center"><div class="col-lg-6">
<div class="card">
  <div class="card-header fw-semibold"><i class="bi bi-person-plus me-2"></i>${empty persona ? 'Registrar Persona' : 'Editar Persona'}</div>
  <div class="card-body">
    <form method="post" action="${pageContext.request.contextPath}/personas">
      <input type="hidden" name="action" value="${empty persona ? 'crear' : 'actualizar'}">
      <c:if test="${not empty persona}"><input type="hidden" name="id" value="${persona.idPersona}"></c:if>
      <div class="row g-3">
        <div class="col-md-6">
          <label class="form-label">DNI <span class="text-danger">*</span></label>
          <input type="text" name="dni" class="form-control" value="${persona.dni}" required>
        </div>
        <div class="col-md-6">
          <label class="form-label">Nombre <span class="text-danger">*</span></label>
          <input type="text" name="nombre" class="form-control" value="${persona.nombre}" required>
        </div>
        <div class="col-md-6">
          <label class="form-label">Apellido</label>
          <input type="text" name="apellido" class="form-control" value="${persona.apellido}">
        </div>
        <div class="col-md-6">
          <label class="form-label">Correo</label>
          <input type="email" name="correo" class="form-control" value="${persona.correo}">
        </div>
        <div class="col-md-6">
          <label class="form-label">Teléfono</label>
          <input type="text" name="telefono" class="form-control" value="${persona.telefono}">
        </div>
      </div>
      <div class="d-flex gap-2 justify-content-end mt-3">
        <a href="${pageContext.request.contextPath}/personas" class="btn btn-outline-secondary">Cancelar</a>
        <button type="submit" class="btn btn-accent"><i class="bi bi-check-lg me-1"></i>Guardar</button>
      </div>
    </form>
  </div>
</div>
</div></div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
