<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es" data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seguridad Nacional</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        :root { --sidebar-w: 250px; --topbar-h: 60px; --accent: #2583ff; }
        body { background: #0d1117; color: #e6edf3; margin: 0; }

        /* SIDEBAR */
        #sidebar { width: var(--sidebar-w); height: 100vh; position: fixed; top: 0; left: 0;
                   background: #161b22; border-right: 1px solid #30363d; z-index: 1000;
                   display: flex; flex-direction: column; transition: transform .3s; }
        #sidebar .brand { padding: 1.2rem 1.5rem; border-bottom: 1px solid #30363d; }
        #sidebar .brand i { color: var(--accent); font-size: 1.5rem; }
        #sidebar .brand span { font-weight: 700; font-size: 1rem; color: #e6edf3; }
        #sidebar .nav-section { font-size: .65rem; text-transform: uppercase; letter-spacing: .08em;
                                color: #8b949e; padding: .8rem 1.5rem .3rem; }
        #sidebar .nav-link { color: #8b949e; padding: .55rem 1.5rem; border-radius: 6px; margin: 1px .5rem;
                             display: flex; align-items: center; gap: .6rem; font-size: .875rem; transition: all .2s; }
        #sidebar .nav-link:hover, #sidebar .nav-link.active { color: #e6edf3; background: #21262d; }
        #sidebar .nav-link.active { color: var(--accent); border-left: 3px solid var(--accent); padding-left: calc(1.5rem - 3px); }
        #sidebar .nav-link i { font-size: 1rem; width: 1.2rem; text-align: center; }
        #sidebar .sidebar-footer { margin-top: auto; padding: 1rem 1.5rem; border-top: 1px solid #30363d; }

        /* TOPBAR */
        #topbar { height: var(--topbar-h); position: fixed; top: 0; left: var(--sidebar-w); right: 0;
                  background: #161b22; border-bottom: 1px solid #30363d; z-index: 999;
                  display: flex; align-items: center; padding: 0 1.5rem; gap: 1rem; }
        #topbar .page-title { font-size: 1rem; font-weight: 600; flex: 1; }

        /* MAIN */
        #main-content { margin-left: var(--sidebar-w); margin-top: var(--topbar-h); padding: 1.5rem; min-height: calc(100vh - var(--topbar-h)); }

        /* ALERTS */
        .alert-flash { position: fixed; top: calc(var(--topbar-h) + 1rem); right: 1rem; z-index: 9999; min-width: 280px; }

        /* BADGES */
        .badge-accent { background: var(--accent); color: #0d1117; }

        /* CARDS */
        .card { background: #161b22; border: 1px solid #30363d; }
        .card-header { background: #1c2128; border-bottom: 1px solid #30363d; }
        .table { --bs-table-color: #e6edf3; --bs-table-bg: transparent; --bs-table-border-color: #30363d;
                 --bs-table-striped-bg: #1c2128; --bs-table-hover-bg: #21262d; }
        .form-control, .form-select { background: #0d1117; border-color: #30363d; color: #e6edf3; }
        .form-control:focus, .form-select:focus { background: #0d1117; border-color: var(--accent);
            box-shadow: 0 0 0 .25rem rgba(230,168,23,.15); color: #e6edf3; }
        .btn-accent { background: var(--accent); border: none; color: #0d1117; font-weight: 600; }
        .btn-accent:hover { background: #2583ff; color: #0d1117; }
        select option { background: #0d1117; }
    </style>
</head>
<body>

<!-- SIDEBAR -->
<nav id="sidebar">
    <div class="brand d-flex align-items-center gap-2">
        <i class="bi bi-shield-lock-fill"></i>
        <span>Seguridad Nacional</span>
    </div>

    <div class="nav-section">Principal</div>
    <a href="${pageContext.request.contextPath}/dashboard" class="nav-link ${currentPage == 'dashboard' ? 'active' : ''}">
        <i class="bi bi-grid-1x2"></i> Dashboard
    </a>
    <a href="${pageContext.request.contextPath}/reportes" class="nav-link ${currentPage == 'reportes' ? 'active' : ''}">
        <i class="bi bi-file-earmark-text"></i> Reportes
    </a>
    <a href="${pageContext.request.contextPath}/alertas" class="nav-link ${currentPage == 'alertas' ? 'active' : ''}">
        <i class="bi bi-bell"></i> Alertas
        <c:if test="${not empty alertasNoLeidas}">
            <span class="badge badge-accent ms-auto">${alertasNoLeidas.size()}</span>
        </c:if>
    </a>

    <div class="nav-section">Personal</div>
    <a href="${pageContext.request.contextPath}/guardas" class="nav-link ${currentPage == 'guardas' ? 'active' : ''}">
        <i class="bi bi-person-badge"></i> Guardas
    </a>
    <a href="${pageContext.request.contextPath}/supervisores" class="nav-link ${currentPage == 'supervisores' ? 'active' : ''}">
        <i class="bi bi-person-check"></i> Supervisores
    </a>
    <a href="${pageContext.request.contextPath}/personal" class="nav-link ${currentPage == 'personal' ? 'active' : ''}">
        <i class="bi bi-briefcase"></i> Personal Admin.
    </a>

    <div class="nav-section">Configuración</div>
    <a href="${pageContext.request.contextPath}/personas" class="nav-link ${currentPage == 'personas' ? 'active' : ''}">
        <i class="bi bi-people"></i> Personas
    </a>
    <a href="${pageContext.request.contextPath}/usuarios" class="nav-link ${currentPage == 'usuarios' ? 'active' : ''}">
        <i class="bi bi-person-gear"></i> Usuarios
    </a>
    <a href="${pageContext.request.contextPath}/vehiculos" class="nav-link ${currentPage == 'vehiculos' ? 'active' : ''}">
        <i class="bi bi-truck"></i> Vehículos
    </a>

    <div class="sidebar-footer">
        <div class="small text-secondary mb-2">
            <i class="bi bi-person-circle me-1"></i>
            <strong class="text-white">${sessionScope.usuarioSesion.nombreUsuario}</strong><br>
            <span class="ms-3">${sessionScope.rolPrincipal}</span>
        </div>
        <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn btn-sm btn-outline-secondary w-100">
            <i class="bi bi-box-arrow-left me-1"></i>Cerrar sesión
        </a>
    </div>
</nav>

<!-- TOPBAR -->
<div id="topbar">
    <span class="page-title">${pageTitle}</span>
</div>

<!-- MENSAJES FLASH -->
<div class="alert-flash">
    <c:if test="${not empty sessionScope.mensaje}">
        <div class="alert alert-success alert-dismissible fade show shadow" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i>${sessionScope.mensaje}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="mensaje" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger alert-dismissible fade show shadow" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>${sessionScope.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="error" scope="session"/>
    </c:if>
</div>

<div id="main-content">
