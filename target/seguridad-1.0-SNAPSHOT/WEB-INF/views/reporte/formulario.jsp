<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="reportes" scope="request"/>
<c:set var="pageTitle"   value="${empty reporte ? 'Nuevo Reporte' : 'Editar Reporte'}" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="row justify-content-center">
<div class="col-lg-7">
<div class="card">
    <div class="card-header fw-semibold">
        <i class="bi bi-file-earmark-plus me-2"></i>${empty reporte ? 'Crear nuevo reporte' : 'Editar reporte #'.concat(reporte.idReporte)}
    </div>
    <div class="card-body">
        <form method="post" action="${pageContext.request.contextPath}/reportes">
            <input type="hidden" name="action" value="${empty reporte ? 'crear' : 'actualizar'}">
            <c:if test="${not empty reporte}">
                <input type="hidden" name="id" value="${reporte.idReporte}">
            </c:if>

            <div class="mb-3">
                <label class="form-label">Descripción <span class="text-danger">*</span></label>
                <textarea name="descripcion" class="form-control" rows="4" required>${reporte.descripcion}</textarea>
            </div>
            <div class="row g-3 mb-3">
                <div class="col-md-6">
                    <label class="form-label">Tipo de Reporte <span class="text-danger">*</span></label>
                    <select name="tipoId" class="form-select" required>
                        <option value="">Seleccionar...</option>
                        <c:forEach var="t" items="${tipos}">
                            <option value="${t.idTipoReporte}" ${reporte.tipoReporte.idTipoReporte == t.idTipoReporte ? 'selected' : ''}>${t.nombre}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Estado <span class="text-danger">*</span></label>
                    <select name="statusId" class="form-select" required>
                        <option value="">Seleccionar...</option>
                        <c:forEach var="s" items="${statuses}">
                            <option value="${s.idStatusReporte}" ${reporte.statusReporte.idStatusReporte == s.idStatusReporte ? 'selected' : ''}>${s.nombre}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="d-flex gap-2 justify-content-end">
                <a href="${pageContext.request.contextPath}/reportes" class="btn btn-outline-secondary">Cancelar</a>
                <button type="submit" class="btn btn-accent"><i class="bi bi-check-lg me-1"></i>Guardar</button>
            </div>
        </form>
    </div>
</div>
</div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
