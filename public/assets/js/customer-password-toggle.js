document.addEventListener("click", function (event) {
    const toggle = event.target.closest("[data-password-toggle]");
    if (!toggle) return;

    const input = document.getElementById(toggle.getAttribute("aria-controls"));
    if (!input) return;

    const showPassword = input.type === "password";
    input.type = showPassword ? "text" : "password";
    toggle.textContent = showPassword ? "Ẩn" : "Hiện";
    toggle.setAttribute("aria-pressed", String(showPassword));
});
