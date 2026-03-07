<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle"   value="Detalle Guarda" scope="request"/>
<c:set var="currentPage" value="guardas"        scope="request"/>
<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div class="page-header d-flex align-items-end justify-content-between">
    <div>
        <div class="page-subtitle">Guardas / Detalle</div>
        <h1 class="page-title">Guarda #${guarda.idGuarda}</h1>
    </div>
    <div class="d-flex gap-2">
        <a href="${pageContext.request.contextPath}/guardas?action=editar&id=${guarda.idGuarda}"
           class="btn btn-primary btn-sm"><i class="bi bi-pencil me-1"></i>Editar</a>
        <a href="${pageContext.request.contextPath}/guardas" class="btn btn-outline-secondary btn-sm">
            <i class="bi bi-arrow-left me-1"></i>Volver
        </a>
    </div>
</div>

<div class="row g-3">
    <div class="col-md-6">
        <div class="card h-100">
            <div class="card-header-sn"><i class="bi bi-person me-2"></i>Datos Personales</div>
            <div class="card-body p-4">
                <div class="row g-3">
                    <div class="col-6"><div class="form-label">Nombre</div><p class="text-light">${guarda.persona.nombre}</p></div>
                    <div class="col-6"><div class="form-label">Apellido</div><p class="text-light">${guarda.persona.apellido}</p></div>
                    <div class="col-12"><div class="form-label">Correo</div><p class="text-mono text-light">${guarda.persona.correo}</p></div>
                    <div class="col-12"><div class="form-label">Teléfono</div><p class="text-mono text-light">${guarda.persona.telefono}</p></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card h-100">
            <div class="card-header-sn"><i class="bi bi-building me-2"></i>Asignación</div>
            <div class="card-body p-4">
                <div class="row g-3">
                    <div class="col-12">
                        <div class="form-label">Área</div>
                        <span class="badge bg-info-soft fs-6">${guarda.areas.nombreArea}</span>
                    </div>
                    <div class="col-12">
                        <div class="form-label">Supervisor</div>
                        <p class="text-light">${guarda.supervisor.persona.nombre} ${guarda.supervisor.persona.apellido}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>
