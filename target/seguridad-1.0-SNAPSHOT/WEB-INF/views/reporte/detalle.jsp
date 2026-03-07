<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="reportes" scope="request"/>
<c:set var="pageTitle"   value="Detalle del Reporte" scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="row justify-content-center">
<div class="col-lg-7">
<div class="card">
    <div class="card-header d-flex align-items-center gap-2">
        <i class="bi bi-file-earmark-text"></i>
        <span class="fw-semibold">Reporte #${reporte.idReporte}</span>
        <span class="badge bg-primary ms-auto">${reporte.statusReporte.nombre}</span>
    </div>
    <div class="card-body">
        <dl class="row mb-0">
            <dt class="col-sm-4 text-secondary">Fecha creación</dt>
            <dd class="col-sm-8">${reporte.fechaCreacion}</dd>
            <dt class="col-sm-4 text-secondary">Tipo</dt>
            <dd class="col-sm-8"><span class="badge bg-secondary">${reporte.tipoReporte.nombre}</span></dd>
            <dt class="col-sm-4 text-secondary">Descripción</dt>
            <dd class="col-sm-8">${reporte.descripcion}</dd>
            <dt class="col-sm-4 text-secondary">Generado por</dt>
            <dd class="col-sm-8">${reporte.usuarioGenerador.persona.nombre} ${reporte.usuarioGenerador.persona.apellido}</dd>
        </dl>
    </div>
    <div class="card-footer d-flex gap-2">
        <a href="${pageContext.request.contextPath}/reportes" class="btn btn-outline-secondary btn-sm">
            <i class="bi bi-arrow-left me-1"></i>Volver
        </a>
        <a href="${pageContext.request.contextPath}/reportes?action=editar&id=${reporte.idReporte}" class="btn btn-outline-warning btn-sm ms-auto">
            <i class="bi bi-pencil me-1"></i>Editar
        </a>
    </div>
</div>
</div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
