</div><!-- /main-content -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Auto-dismiss flash messages after 4s
    setTimeout(() => {
        document.querySelectorAll('.alert-flash .alert').forEach(a => {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(a);
            bsAlert.close();
        });
    }, 4000);
</script>
</body>
</html>
