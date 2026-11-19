// ==================== NOTIFICATION SYSTEM ====================
function showAlert(message, type = "success", duration = 3000) {
    if (!message) return;
    const isError = type === "error" || type === "danger";
    if (typeof Swal !== "undefined") {
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: duration,
            timerProgressBar: true,
            background: isError ? '#dc3545' : '#28a745',
            color: '#ffffff',
            iconColor: '#ffffff',
            customClass: {
                popup: isError ? 'swal2-toast-error' : 'swal2-toast-success',
                icon: 'swal2-toast-icon-clean'
            },
            didOpen: (toast) => {
                const container = toast.closest(".swal2-container");
                if (container) {
                    container.style.top = "85px";
                    container.style.right = "25px";
                    container.style.zIndex = "999999";
                }
                toast.onmouseenter = Swal.stopTimer;
                toast.onmouseleave = Swal.resumeTimer;
            }
        });
        Toast.fire({
            iconHtml: isError 
                ? '<i class="fa-solid fa-circle-xmark"></i>' 
                : '<i class="fa-solid fa-circle-check"></i>',
            title: message
        });
    } else {
        let existing = document.querySelector(".alert");
        if (existing) existing.remove();

        const div = document.createElement("div");
        div.className = `alert ${isError ? 'alert-danger' : 'alert-success'}`;
        div.setAttribute("alert-time", duration);
        div.innerHTML = `
            <i class="fa-solid ${isError ? 'fa-circle-xmark' : 'fa-circle-check'}"></i>
            <span>${message}</span>
            <span class="close-alert" onclick="this.parentElement.remove()">&times;</span>
        `;
        document.body.appendChild(div);
        setTimeout(() => {
            if (div.parentElement) div.remove();
        }, duration);
    }
}

function setFlashAlert(message, type = "success") {
    if (!message) return;
    sessionStorage.setItem("flashAlert", JSON.stringify({ message, type }));
}

function checkFlashAlert() {
    const flashAlert = sessionStorage.getItem("flashAlert");
    if (flashAlert) {
        try {
            const data = JSON.parse(flashAlert);
            sessionStorage.removeItem("flashAlert");
            if (data && data.message) {
                showAlert(data.message, data.type || "success");
            }
        } catch (e) {
            sessionStorage.removeItem("flashAlert");
        }
    }
}

if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", checkFlashAlert);
} else {
    checkFlashAlert();
}
