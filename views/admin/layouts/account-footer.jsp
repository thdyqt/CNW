<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    </div>

    <script>
        const CONTEXT_PATH = "${pageContext.request.contextPath}";
        const pathAdmin = (CONTEXT_PATH ? CONTEXT_PATH.replace(/^\//, '') + "/" : "") + "${pathAdmin}";
    </script>
    <script src="https://unpkg.com/just-validate@latest/dist/just-validate.production.min.js"></script>
    <script src="${pageContext.request.contextPath}/admin/assets/js/account.js"></script>
</body>
</html>
