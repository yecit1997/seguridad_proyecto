<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="vehiculos" scope="request"/>
<c:set var="pageTitle"   value="${empty vehiculo ? 'Nuevo Vehículo' : 'Editar Vehículo'}" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="row justify-content-center"><div class="col-lg-5">
<div class="card">
  <div class="card-header fw-semibold"><i class="bi bi-truck me-2"></i>${empty vehiculo ? 'Registrar Vehículo' : 'Editar Vehículo'}</div>
  <div class="card-body">
    <form method="post" action="${pageContext.request.contextPath}/vehiculos">
      <input type="hidden" name="action" value="${empty vehiculo ? 'crear' : 'actualizar'}">
      <c:if test="${not empty vehiculo}"><input type="hidden" name="id" value="${vehiculo.idVehiculo}"></c:if>
      <div class="mb-3">
        <label class="form-label">Placa <span class="text-danger">*</span></label>
        <input type="text" name="placa" class="form-control text-uppercase" value="${vehiculo.placa}" required maxlength="10">
      </div>
      <div class="mb-3">
        <label class="form-label">Conductor</label>
        <select name="conductorId" class="form-select">
          <option value="">Sin conductor asignado</option>
          <c:forEach var="c" items="${conductores}">
            <option value="${c.idFkPersona}" ${vehiculo.conductor.idFkPersona == c.idFkPersona ? 'selected' : ''}>${c.persona.nombre} ${c.persona.apellido} — ${c.licencia}</option>
          </c:forEach>
        </select>
      </div>
      <div class="d-flex gap-2 justify-content-end">
        <a href="${pageContext.request.contextPath}/vehiculos" class="btn btn-outline-secondary">Cancelar</a>
        <button type="submit" class="btn btn-accent"><i class="bi bi-check-lg me-1"></i>Guardar</button>
      </div>
    </form>
  </div>
</div>
</div></div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
