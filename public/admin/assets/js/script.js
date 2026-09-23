// Menu Mobile
const buttonMenu = document.querySelector(".header .inner-button-menu");
if (buttonMenu) {
    const sider = document.querySelector(".sider");
    const siderOverlay = document.querySelector(".sider-overlay");
    
    buttonMenu.addEventListener("click", () => {
        sider.classList.toggle("active");
        siderOverlay.classList.toggle("active");
    });

    siderOverlay.addEventListener("click", () => {
        sider.classList.remove("active");
        siderOverlay.classList.remove("active");
    });
}

// Schedule Section 8
const scheduleSection8 = document.querySelector(".section-8 .inner-schedule");
if (scheduleSection8) {
    const buttonCreate = scheduleSection8.querySelector(".inner-schedule-add");
    const listItem = scheduleSection8.querySelector(".section-8 .inner-schedule-list");

    // Tạo mới
    buttonCreate.addEventListener("click", () => {
        const firstItem = scheduleSection8.querySelector(".inner-schedule-item");
        const cloneItem = firstItem.cloneNode(true);
        cloneItem.querySelector("input").value = "";

        const body = cloneItem.querySelector(".inner-schedule-body");
        const id = `mce_${Date.now()}`
        body.innerHTML = `<textarea textarea-mce id = "${id}"></textarea>`;

        cloneItem.classList.remove("hidden");
        listItem.appendChild(cloneItem);
        initTinyMCE(`#${id}`);
    })

    listItem.addEventListener("click", event => {
        // Đóng mở item
        if (event.target.closest(".inner-more")) {
            const parentItem = event.target.closest(".inner-schedule-item");
            if (parentItem) {
                parentItem.classList.toggle("hidden");
            }
        }

        // Xóa item
        if (event.target.closest(".inner-remove")) {
            const parentItem = event.target.closest(".inner-schedule-item");
            const totalItem = listItem.querySelectorAll(".inner-schedule-item").length;
            if (parentItem && totalItem > 1) {
                parentItem.remove();
            }
        }
    })

    // Sắp xếp
    new Sortable(listItem, {
        handle: '.inner-move',
        animation: 150,
        onStart: event => {
            const textarea = event.item.querySelector("[textarea-mce]");
            const id = textarea.id;
            tinymce.get(id).remove();
        },
        onEnd: event => {
            const textarea = event.item.querySelector("[textarea-mce]");
            const id = textarea.id;
            initTinyMCE(`#${id}`);
        }
    });
}

// Filepond Image
const listFilepondImage = document.querySelectorAll("[filepond-image]");
let filePond = {};
if (listFilepondImage) {
    listFilepondImage.forEach(filepondImage => {
        FilePond.registerPlugin(FilePondPluginImagePreview, FilePondPluginFileValidateType);

        filePond[filepondImage.name] = FilePond.create(filepondImage, {
            labelIdle: "+",
            imagePreviewHeight: 150, 
            stylePanelLayout: 'compact'
        })
    })
}

// Biểu đồ doanh thu
const revenueChart = document.querySelector("#revenue-chart");
if (revenueChart) {
    new Chart(revenueChart, {
        type: 'line',
        data: {
            labels: ["01", "02", "03", "04", "05"],
            datasets: [
                {
                label: 'Tháng 09/2026',
                data: [1200000, 2500000, 3000000, 1000000, 1200000],
                borderColor: "#36A1EA",
                borderWidth: 1.5
                },
                {
                label: 'Tháng 08/2026',
                data: [1400000, 2200000, 2300000, 1700000, 2300000],
                borderColor: " #FE6383",
                borderWidth: 1.5
                }
            ]
        },
        options: {
            plugins: {
                legend: {
                    position: 'bottom',
                },
            },
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Ngày'
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Doanh thu (VND)'
                    }
                }
            },
            maintainAspectRatio: false
        }
    })
}

// Category Create
const categoryCreateForm = document.querySelector("#category-create-form");
if (categoryCreateForm) {
    const validator = new JustValidate("#category-create-form");

    validator
        .addField("#name", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập tên danh mục!"
            },
        ])
        .onSuccess(event => {
            const name = event.target.name.value;
            const parent = event.target.parent.value;
            const position = event.target.position.value;
            const status = event.target.status.value;

            const avatars = filePond.avatar.getFiles();
            let avatar = null;
            if (avatars.length > 0) {
                avatar = avatars[0].file;
            }
            
            const description = tinymce.get("description").getContent();
        })
}

// Tour Create
const tourCreateForm = document.querySelector("#tour-create-form");
if (tourCreateForm) {
    const validator = new JustValidate("#tour-create-form");

    validator
        .addField("#name", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập tên tour!"
            },
        ])
        .onSuccess(event => {
            const name = event.target.name.value;
            const category = event.target.category.value;
            const position = event.target.position.value;
            const status = event.target.status.value;

            const avatars = filePond.avatar.getFiles();
            let avatar = null;
            if (avatars.length > 0) {
                avatar = avatars[0].file;
            }

            const oldAdultPrice = event.target.oldAdultPrice.value;
            const oldKidPrice = event.target.oldKidPrice.value;
            const oldBabyPrice = event.target.oldBabyPrice.value;
            const newAdultPrice = event.target.newAdultPrice.value;
            const newKidPrice = event.target.newKidPrice.value;
            const newBabyPrice = event.target.newBabyPrice.value;
            const stockAdult = event.target.stockAdult.value;
            const stockKid = event.target.stockKid.value;
            const stockBaby = event.target.stockBaby.value;

            const locations = [];
            const listElementLocation = document.querySelectorAll('input[name="locations"]:checked');
            listElementLocation.forEach(input => {
                locations.push(input.value);
            })

            const time = event.target.time.value;
            const vehicle = event.target.vehicle.value;
            const date = event.target.date.value;
            const info = tinymce.get("info").getContent();

            const schedules = [];
            const listElementScheduleItem = document.querySelectorAll(".inner-schedule-item");
            listElementScheduleItem.forEach(scheduleItem => {
                const input = scheduleItem.querySelector("input");
                const title = input.value;
                const textarea = scheduleItem.querySelector("textarea");
                const idTextarea = textarea.id;
                const description = tinymce.get(idTextarea).getContent();

                schedules.push({
                    title: title,
                    description: description
                })
            })
        })
}

// Order Modify
const orderModifyForm = document.querySelector("#order-modify-form");
if (orderModifyForm) {
    const validator = new JustValidate("#order-modify-form");

    validator
        .addField("#name", [
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
        .addField("#phone", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập số điện thoại!"
            },
            {
                rule: "customRegexp",
                value: /(84|0[3|5|7|8|9])+([0-9]{8})\b/g,
                errorMessage: "Số điện thoại không đúng định dạng!"
            },
        ])
        .onSuccess(event => {
            const name = event.target.name.value;
            const phone = event.target.phone.value;
            const note = event.target.note.value;
            const paymentMethod = event.target.paymentMethod.value;
            const paymentStatus = event.target.paymentStatus.value;
            const status = event.target.status.value;
        })
}

// Website Info
const websiteForm = document.querySelector("#website-form");
if (websiteForm) {
    const validator = new JustValidate("#website-form");

    validator
        .addField("#name", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập tên Website!"
            }
        ])
        .addField("#email", [
            {
                rule: "email",
                errorMessage: "Email không đúng định dạng"
            }
        ])
        .onSuccess(event => {
            const name = event.target.name.value;
            const phone = event.target.phone.value;
            const email = event.target.email.value;
            const address = event.target.address.value;

            const logos = filePond.logo.getFiles();
            let logo = null;
            if (logos.length > 0) {
                logo = logos[0].file;
            }

            const favicons = filePond.favicon.getFiles();
            let favicon = null;
            if (favicons.length > 0) {
                favicon = favicons[0].file;
            }
        })
}

// Admin Create
const adminCreateForm = document.querySelector("#admin-create-form");
if (adminCreateForm) {
    const validator = new JustValidate("#admin-create-form");

    validator
        .addField("#name", [
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
        .addField("#email", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập Email!"
            },
            {
                rule: "email",
                errorMessage: "Email không đúng định dạng"
            }
        ])
        .addField("#phone", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập số điện thoại!"
            },
            {
                rule: "customRegexp",
                value: /(84|0[3|5|7|8|9])+([0-9]{8})\b/g,
                errorMessage: "Số điện thoại không đúng định dạng!"
            },
        ])
        .addField("#position", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập chức vụ!"
            }
        ])
        .addField("#password", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập mật khẩu!"
            },
            {
                rule: "password",
                errorMessage: "Mật khẩu tối thiểu 8 kí tự, ít nhất một chữ cái và một chữ số."
            }
        ])
        .onSuccess(event => {
            const name = event.target.name.value;
            const email = event.target.email.value;
            const phone = event.target.phone.value;
            const role = event.target.role.value;
            const position = event.target.position.value;
            const status = event.target.status.value;
            const password = event.target.password.value;

            const avatars = filePond.avatar.getFiles();
            let avatar = null;
            if (avatars.length > 0) {
                avatar = avatars[0].file;
            }
        })
}

// Role Create
const roleCreateForm = document.querySelector("#role-create-form");
if (roleCreateForm) {
    const validator = new JustValidate("#role-create-form");

    validator
        .addField("#name", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập tên nhóm quyền!"
            }
        ])
        .onSuccess(event => {
            const name = event.target.name.value;
            const desc = event.target.desc.value;
            
            const permissions = [];
            const listElementPermission = document.querySelectorAll('input[name="perms"]:checked');
            listElementPermission.forEach(input => {
                permissions.push(input.value);
            })
        })
}

// Admin Create
const profileEditForm = document.querySelector("#profile-edit-form");
if (profileEditForm) {
    const validator = new JustValidate("#profile-edit-form");

    validator
        .addField("#name", [
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
        .addField("#email", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập Email!"
            },
            {
                rule: "email",
                errorMessage: "Email không đúng định dạng"
            }
        ])
        .addField("#phone", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập số điện thoại!"
            },
            {
                rule: "customRegexp",
                value: /(84|0[3|5|7|8|9])+([0-9]{8})\b/g,
                errorMessage: "Số điện thoại không đúng định dạng!"
            },
        ])
        .onSuccess(event => {
            const name = event.target.name.value;
            const email = event.target.email.value;
            const phone = event.target.phone.value;

            const avatars = filePond.avatar.getFiles();
            let avatar = null;
            if (avatars.length > 0) {
                avatar = avatars[0].file;
            }
        })
}

// Profile Change Password
const profileChangePasswordForm = document.querySelector("#profile-change-password-form");
if (profileChangePasswordForm) {
    const validator = new JustValidate("#profile-change-password-form");

    validator
        .addField("#password", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập mật khẩu của bạn!"
            },
            {
                rule: "password",
                errorMessage: "Mật khẩu tối thiểu 8 kí tự, ít nhất một chữ cái và một chữ số."
            }
        ])
        .addField("#confirmPassword", [
            {
                rule: "required",
                errorMessage: "Vui lòng xác nhận mật khẩu của bạn!"
            },
            {
                validator: (value, fields) => value == fields["#password"].elem.value,
                errorMessage: "Mật khẩu xác nhận không trùng khớp!"
            }
        ])
        .onSuccess(event => {
            const password = event.target.password.value;
        })
}