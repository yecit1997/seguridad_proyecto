<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="alertas" scope="request"/>
<c:set var="pageTitle"   value="Mis Alertas" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="card">
  <div class="card-header fw-semibold"><i class="bi bi-bell me-2"></i>Mis Alertas</div>
  <div class="card-body p-0">
    <table class="table table-hover mb-0">
      <thead><tr><th>Fecha/Hora</th><th>Mensaje</th><th>Reporte</th><th>Estado</th><th>Acciones</th></tr></thead>
      <tbody>
      <c:forEach var="a" items="${alertas}">
        <tr class="${a.leida ? '' : 'table-warning bg-opacity-10'}">
          <td class="small">${a.fechaHora}</td>
          <td>${a.mensaje}</td>
          <td class="small text-secondary">${not empty a.reporte ? '#'.concat(a.reporte.idReporte) : '—'}</td>
          <td><span class="badge ${a.leida ? 'bg-secondary' : 'bg-warning text-dark'}">${a.leida ? 'Leída' : 'Nueva'}</span></td>
          <td class="d-flex gap-1">
            <c:if test="${not a.leida}">
              <form method="post" action="${pageContext.request.contextPath}/alertas" class="d-inline">
                <input type="hidden" name="action" value="marcarLeida"><input type="hidden" name="id" value="${a.idAlerta}">
                <button type="submit" class="btn btn-sm btn-outline-success py-0"><i class="bi bi-check2"></i></button>
              </form>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/alertas" class="d-inline">
              <input type="hidden" name="action" value="eliminar"><input type="hidden" name="id" value="${a.idAlerta}">
              <button type="submit" class="btn btn-sm btn-outline-danger py-0" onclick="return confirm('¿Eliminar alerta?')"><i class="bi bi-trash"></i></button>
            </form>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty alertas}"><tr><td colspan="5" class="text-center text-secondary py-4">Sin alertas</td></tr></c:if>
      </tbody>
    </table>
  </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
