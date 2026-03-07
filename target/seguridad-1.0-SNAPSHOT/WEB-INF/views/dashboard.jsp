<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPage" value="dashboard" scope="request"/>
<c:set var="pageTitle"   value="Dashboard"  scope="request"/>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="row g-3 mb-4">
    <div class="col-sm-6 col-xl-3">
        <div class="card h-100">
            <div class="card-body d-flex align-items-center gap-3">
                <div class="rounded-3 p-3" style="background:rgba(230,168,23,.15)">
                    <i class="bi bi-file-earmark-text fs-3" style="color:#e6a817"></i>
                </div>
                <div>
                    <div class="fs-4 fw-bold">${totalReportes}</div>
                    <div class="text-secondary small">Reportes totales</div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-xl-3">
        <div class="card h-100">
            <div class="card-body d-flex align-items-center gap-3">
                <div class="rounded-3 p-3" style="background:rgba(33,181,119,.15)">
                    <i class="bi bi-person-badge fs-3" style="color:#21b577"></i>
                </div>
                <div>
                    <div class="fs-4 fw-bold">${totalGuardas}</div>
                    <div class="text-secondary small">Guardas activos</div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-xl-3">
        <div class="card h-100">
            <div class="card-body d-flex align-items-center gap-3">
                <div class="rounded-3 p-3" style="background:rgba(220,53,69,.15)">
                    <i class="bi bi-bell-fill fs-3 text-danger"></i>
                </div>
                <div>
                    <div class="fs-4 fw-bold">${alertasNoLeidas.size()}</div>
                    <div class="text-secondary small">Alertas no leídas</div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row g-3">
    <div class="col-lg-8">
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <span class="fw-semibold"><i class="bi bi-clock-history me-2"></i>Mis últimos reportes</span>
                <a href="${pageContext.request.contextPath}/reportes?action=nuevo" class="btn btn-accent btn-sm">
                    <i class="bi bi-plus-lg me-1"></i>Nuevo
                </a>
            </div>
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover mb-0">
                        <thead><tr><th>Fecha</th><th>Descripción</th><th>Tipo</th><th>Estado</th></tr></thead>
                        <tbody>
                        <c:forEach var="r" items="${ultimosReportes}">
                            <tr>
                                <td class="small text-secondary">${r.fechaCreacion}</td>
                                <td>${r.descripcion}</td>
                                <td><span class="badge bg-secondary">${r.tipoReporte.nombre}</span></td>
                                <td><span class="badge bg-primary">${r.statusReporte.nombre}</span></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty ultimosReportes}">
                            <tr><td colspan="4" class="text-center text-secondary py-4">Sin reportes aún</td></tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="card">
            <div class="card-header fw-semibold"><i class="bi bi-bell me-2"></i>Alertas recientes</div>
            <div class="card-body p-0">
                <c:forEach var="a" items="${alertasNoLeidas}">
                    <div class="p-3 border-bottom border-secondary-subtle d-flex gap-2 align-items-start">
                        <i class="bi bi-dot text-warning fs-4 mt-n1"></i>
                        <div>
                            <div class="small">${a.mensaje}</div>
                            <div class="text-secondary" style="font-size:.75rem">${a.fechaHora}</div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${empty alertasNoLeidas}">
                    <div class="p-3 text-center text-secondary small">Sin alertas nuevas</div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>
