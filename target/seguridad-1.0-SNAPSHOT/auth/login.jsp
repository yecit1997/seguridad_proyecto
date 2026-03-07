<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es" data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seguridad Nacional — Iniciar Sesión</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        body { background: #0d1117; min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .login-card { background: #161b22; border: 1px solid #30363d; border-radius: 12px; width: 100%; max-width: 400px; padding: 2.5rem; }
        .brand-icon { font-size: 3rem; color: #2583ff; }
        .form-control { background: #0d1117; border-color: #30363d; color: #e6edf3; }
        .form-control:focus { background: #0d1117; border-color: #2583ff; box-shadow: 0 0 0 0.25rem rgba(230,168,23,.15); color: #e6edf3; }
        .btn-login { background: #2583ff; border: none; color: #0d1117; font-weight: 600; }
        .btn-login:hover { background: #2583ff; color: #0d1117; }
    </style>
</head>
<body>
<div class="login-card">
    <div class="text-center mb-4">
        <i class="bi bi-shield-lock-fill brand-icon"></i>
        <h4 class="mt-2 text-white fw-bold">Seguridad Nacional</h4>
        <p class="text-secondary small">Sistema de Gestión de Reportes</p>
    </div>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger py-2 small">
        <i class="bi bi-exclamation-triangle-fill me-2"></i>${error}
    </div>
    <% } %>

    <form action="${pageContext.request.contextPath}/auth" method="post">
        <div class="mb-3">
            <label class="form-label text-secondary small">Usuario</label>
            <div class="input-group">
                <span class="input-group-text bg-dark border-secondary text-secondary"><i class="bi bi-person"></i></span>
                <input type="text" name="nombreUsuario" class="form-control" placeholder="Ingresa tu usuario" required autofocus>
            </div>
        </div>
        <div class="mb-4">
            <label class="form-label text-secondary small">Contraseña</label>
            <div class="input-group">
                <span class="input-group-text bg-dark border-secondary text-secondary"><i class="bi bi-lock"></i></span>
                <input type="password" name="contrasena" class="form-control" placeholder="••••••••" required>
            </div>
        </div>
        <button type="submit" class="btn btn-login w-100 py-2">
            <i class="bi bi-box-arrow-in-right me-2"></i>Iniciar Sesión
        </button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
