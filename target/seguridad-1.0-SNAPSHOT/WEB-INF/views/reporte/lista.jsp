<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="reportes" scope="request"/>
<c:set var="pageTitle"   value="Reportes"  scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="card">
    <div class="card-header d-flex flex-wrap gap-2 align-items-center">
        <span class="fw-semibold me-auto"><i class="bi bi-file-earmark-text me-2"></i>Listado de Reportes</span>
        <!-- Filtros -->
        <form method="get" action="${pageContext.request.contextPath}/reportes" class="d-flex gap-2">
            <select name="tipo" class="form-select form-select-sm" style="width:150px">
                <option value="">Todos los tipos</option>
                <c:forEach var="t" items="${tipos}"><option value="${t.idTipoReporte}">${t.nombre}</option></c:forEach>
            </select>
            <select name="status" class="form-select form-select-sm" style="width:150px">
                <option value="">Todos los estados</option>
                <c:forEach var="s" items="${statuses}"><option value="${s.idStatusReporte}">${s.nombre}</option></c:forEach>
            </select>
            <button type="submit" class="btn btn-sm btn-outline-secondary"><i class="bi bi-funnel"></i></button>
        </form>
        <a href="${pageContext.request.contextPath}/reportes?action=nuevo" class="btn btn-accent btn-sm">
            <i class="bi bi-plus-lg me-1"></i>Nuevo Reporte
        </a>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead><tr><th>#</th><th>Fecha</th><th>Descripción</th><th>Tipo</th><th>Estado</th><th>Generado por</th><th>Acciones</th></tr></thead>
                <tbody>
                <c:forEach var="r" items="${reportes}">
                    <tr>
                        <td class="text-secondary">${r.idReporte}</td>
                        <td class="small">${r.fechaCreacion}</td>
                        <td>${r.descripcion}</td>
                        <td><span class="badge bg-secondary">${r.tipoReporte.nombre}</span></td>
                        <td><span class="badge bg-primary">${r.statusReporte.nombre}</span></td>
                        <td class="small">${r.usuarioGenerador.persona.nombre} ${r.usuarioGenerador.persona.apellido}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/reportes?action=detalle&id=${r.idReporte}" class="btn btn-sm btn-outline-info py-0"><i class="bi bi-eye"></i></a>
                            <a href="${pageContext.request.contextPath}/reportes?action=editar&id=${r.idReporte}" class="btn btn-sm btn-outline-warning py-0"><i class="bi bi-pencil"></i></a>
                            <form method="post" action="${pageContext.request.contextPath}/reportes" class="d-inline">
                                <input type="hidden" name="action" value="eliminar">
                                <input type="hidden" name="id" value="${r.idReporte}">
                                <button type="submit" class="btn btn-sm btn-outline-danger py-0" onclick="return confirm('¿Eliminar reporte?')"><i class="bi bi-trash"></i></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty reportes}">
                    <tr><td colspan="7" class="text-center text-secondary py-4">No hay reportes</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
