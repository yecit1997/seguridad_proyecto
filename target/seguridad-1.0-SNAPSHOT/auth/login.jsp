<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seguridad Nacional — Acceso</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        body {
            background: #0d1117;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-card {
            background: #161b22;
            border: 1px solid #30363d;
            border-top: 3px solid #0070f0;
            border-radius: 0;
            width: 100%;
            max-width: 400px;
        }
        .brand-icon { font-size: 2.8rem; color: #0070f0; }
        .brand-title { color: #0070f0; font-weight: 800; letter-spacing: .06em; }
        .brand-title span { color: #e6edf3; }
        .brand-sub { font-size: .68rem; letter-spacing: .18em; color: #8b949e; }
        .form-control, .form-select {
            background: #0d1117;
            border: 1px solid #30363d;
            color: #e6edf3;
            border-radius: 0;
        }
        .form-control:focus {
            background: #0d1117;
            border-color: #0070f0;
            color: #e6edf3;
            box-shadow: 0 0 0 3px rgba(240,165,0,.15);
        }
        .form-control::placeholder { color: #484f58; }
        .form-label { font-size: .75rem; letter-spacing: .1em; color: #8b949e; text-transform: uppercase; }
        .btn-login {
            background: #0070f0;
            border: none;
            border-radius: 0;
            color: #000;
            font-weight: 700;
            letter-spacing: .06em;
            text-transform: uppercase;
            padding: .65rem;
            transition: background .15s;
        }
        .btn-login:hover { background: #0070f0; }
        .alert-danger { border-radius: 0; border-left: 3px solid #f85149; background: rgba(248,81,73,.1); color: #ffa198; border-top: 0; border-right: 0; border-bottom: 0; }
        .footer-note { font-size: .65rem; letter-spacing: .12em; color: #484f58; }
    </style>
</head>
<body>
<div class="px-3" style="width:100%;max-width:420px">

    <div class="text-center mb-4">
        <i class="bi bi-shield-fill brand-icon"></i>
        <h1 class="brand-title mt-2 fs-4">SEGURIDAD <span>NACIONAL</span></h1>
        <p class="brand-sub mt-1">SISTEMA DE GESTIÓN OPERATIVA</p>
    </div>

    <div class="login-card p-4">
        <c:if test="${not empty error}">
            <div class="alert alert-danger mb-3 py-2 px-3">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>${error}
            </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/auth">
            <div class="mb-3">
                <label class="form-label" for="nombreUsuario">Usuario</label>
                <input type="text" class="form-control" id="nombreUsuario"
                       name="nombreUsuario" autocomplete="username" autofocus
                       value="${param.nombreUsuario}" placeholder="Nombre de usuario">
            </div>
            <div class="mb-4">
                <label class="form-label" for="contrasena">Contraseña</label>
                <input type="password" class="form-control" id="contrasena"
                       name="contrasena" autocomplete="current-password"
                       placeholder="••••••••">
            </div>
            <button type="submit" class="btn btn-login w-100">
                <i class="bi bi-box-arrow-in-right me-2"></i>Ingresar
            </button>
        </form>
    </div>

    <p class="text-center footer-note mt-3">ACCESO RESTRINGIDO — SOLO PERSONAL AUTORIZADO</p>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
