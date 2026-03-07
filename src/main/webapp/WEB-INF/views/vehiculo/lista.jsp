<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="vehiculos" scope="request"/>
<c:set var="pageTitle"   value="Vehículos" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="card">
  <div class="card-header d-flex align-items-center">
    <span class="fw-semibold me-auto"><i class="bi bi-truck me-2"></i>Vehículos</span>
    <a href="${pageContext.request.contextPath}/vehiculos?action=nuevo" class="btn btn-accent btn-sm"><i class="bi bi-plus-lg me-1"></i>Nuevo</a>
  </div>
  <div class="card-body p-0">
    <table class="table table-hover table-striped mb-0">
      <thead><tr><th>Placa</th><th>Conductor</th><th>DNI Conductor</th><th>Licencia</th><th>Acciones</th></tr></thead>
      <tbody>
      <c:forEach var="v" items="${vehiculos}">
        <tr>
          <td><span class="badge bg-secondary fs-6">${v.placa}</span></td>
          <td>${not empty v.conductor ? v.conductor.persona.nombre.concat(' ').concat(v.conductor.persona.apellido) : '—'}</td>
          <td class="small text-secondary">${not empty v.conductor ? v.conductor.persona.dni : '—'}</td>
          <td class="small">${not empty v.conductor ? v.conductor.licencia : '—'}</td>
          <td>
            <a href="${pageContext.request.contextPath}/vehiculos?action=editar&id=${v.idVehiculo}" class="btn btn-sm btn-outline-warning py-0"><i class="bi bi-pencil"></i></a>
            <form method="post" action="${pageContext.request.contextPath}/vehiculos" class="d-inline">
              <input type="hidden" name="action" value="eliminar"><input type="hidden" name="id" value="${v.idVehiculo}">
              <button type="submit" class="btn btn-sm btn-outline-danger py-0" onclick="return confirm('¿Eliminar vehículo?')"><i class="bi bi-trash"></i></button>
            </form>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty vehiculos}"><tr><td colspan="5" class="text-center text-secondary py-4">Sin vehículos</td></tr></c:if>
      </tbody>
    </table>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
