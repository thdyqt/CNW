// Login
const loginForm = document.querySelector("#login-form");
if (loginForm) {
    const validator = new JustValidate("#login-form");

    validator
        .addField("#email-input", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập Email của bạn!"
            },
            {
                rule: "email",
                errorMessage: "Email không đúng định dạng!"
            },
        ])
        .addField("#password-input", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập mật khẩu của bạn!"
            },
        ])
        .onSuccess(event => {
            const email = event.target.email.value;
            const password = event.target.password.value;
            const rememberPassword = event.target.rememberPassword.checked;

            const dataFinal = {
                email: email,
                password: password,
                rememberPassword: rememberPassword
            };

            fetch(`/${pathAdmin}/account/login`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dataFinal)
            })
                .then (res => res.json())
                .then (data => {
                    if(data.code == "error") {
                        alert(data.message);
                    }
                    else if (data.code == "success") {
                        window.location.href = `/${pathAdmin}/dashboard`
                    }
                })
            
        })
}

// Register
const registerForm = document.querySelector("#register-form");
if (registerForm) {
    const validator = new JustValidate("#register-form");

    validator
        .addField("#full-name-input", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập họ tên đầy đủ!"
            },
            {
                rule: "minLength",
                value: 5,
                errorMessage: "Họ tên phải có ít nhất 5 kí tự!"
            },
            {
                rule: "maxLength",
                value: 50,
                errorMessage: "Họ tên không được vượt quá 50 kí tự!"
            },
        ])
        .addField("#email-input", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập Email của bạn!"
            },
            {
                rule: "email",
                errorMessage: "Email không đúng định dạng!"
            },
        ])
        .addField("#password-input", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập mật khẩu của bạn!"
            },
            {
                rule: "password",
                errorMessage: "Mật khẩu tối thiểu 8 kí tự, ít nhất một chữ cái và một chữ số."
            }
        ])
        .addField("#term-agreement", [
            {
                rule: "required",
                errorMessage: "Bạn phải đồng ý với các điều khoản và điều kiện!"
            }
        ])
        .onSuccess(event => {
            const fullName = event.target.fullName.value;
            const email = event.target.email.value;
            const password = event.target.password.value;
            const agree = event.target.termAgreement.checked;

            if (agree) {
                const dataFinal = {
                    fullName: fullName,
                    email: email,
                    password: password
                };

                fetch(`/${pathAdmin}/account/register`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(dataFinal)
                })
                    .then (res => res.json())
                    .then (data => {
                        if(data.code == "error") {
                            alert(data.message);
                        }
                        else if (data.code == "success") {
                            window.location.href = `/${pathAdmin}/account/register-initial`
                        }
                    })
            }
        })
}

// Forgot Password
const forgotPasswordForm = document.querySelector("#forgot-password-form");
if (forgotPasswordForm) {
    const validator = new JustValidate("#forgot-password-form");

    validator
        .addField("#email-input", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập Email của bạn!"
            },
            {
                rule: "email",
                errorMessage: "Email không đúng định dạng!"
            },
        ])
        .onSuccess(event => {
            const email = event.target.email.value;

            const dataFinal = {
                email: email
            }

            fetch(`/${pathAdmin}/account/forgot-password`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(dataFinal)
                })
                    .then (res => res.json())
                    .then (data => {
                        if(data.code == "error") {
                            alert(data.message);
                        }
                        else if (data.code == "success") {
                            window.location.href = `/${pathAdmin}/account/otp-password?email=${email}`
                        }
                    })
        })
}

// OTP
const otpForm = document.querySelector("#otp-form");
if (otpForm) {
    const validator = new JustValidate("#otp-form");

    validator
        .addField("#otp-input", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập mã OTP!"
            },
            {
                validator: value => value.length == 6,
                errorMessage: "Mã OTP phải có 6 kí tự!"
            },
        ])
        .onSuccess(event => {
            const otp = event.target.otp.value;
            const urlParams = new URLSearchParams(window.location.search);
            const email = urlParams.get("email");
            
            const dataFinal = {
                email: email,
                otp: otp
            }

            fetch(`/${pathAdmin}/account/otp-password`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(dataFinal)
                })
                    .then (res => res.json())
                    .then (data => {
                        if(data.code == "error") {
                            alert(data.message);
                        }
                        else if (data.code == "success") {
                            window.location.href = `/${pathAdmin}/account/reset-password`
                        }
                    })
        })
}

// Change Password
const changePasswordForm = document.querySelector("#change-password-form");
if (changePasswordForm) {
    const validator = new JustValidate("#change-password-form");

    validator
        .addField("#new-password-input", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập mật khẩu của bạn!"
            },
            {
                rule: "password",
                errorMessage: "Mật khẩu tối thiểu 8 kí tự, ít nhất một chữ cái và một chữ số."
            }
        ])
        .addField("#confirm-password-input", [
            {
                rule: "required",
                errorMessage: "Vui lòng xác nhận mật khẩu của bạn!"
            },
            {
                validator: (value, fields) => value == fields["#new-password-input"].elem.value,
                errorMessage: "Mật khẩu xác nhận không trùng khớp!"
            }
        ])
        .onSuccess(event => {
            const newPassword = event.target.newPassword.value;

            const dataFinal = {
                password: newPassword
            }

            fetch(`/${pathAdmin}/account/reset-password`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(dataFinal)
                })
                    .then (res => res.json())
                    .then (data => {
                        if(data.code == "error") {
                            alert(data.message);
                        }
                        else if (data.code == "success") {
                            window.location.href = `/${pathAdmin}/dashboard`
                        }
                    })
        })
}

// Search
const inputSearch = document.querySelector("[search]");
if (inputSearch) {
    const url = new URL(window.location.href);

    // Lắng nghe bấm enter thì tìm kiếm
    inputSearch.addEventListener("keyup", event => {
        if(event.code == "Enter") {
            const value = inputSearch.value;
        }
    })
}