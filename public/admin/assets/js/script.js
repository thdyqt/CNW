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

        let files = null;
        const elementImageDefault = filepondImage.closest("[image-default]");
        if (elementImageDefault) {
            const imageDefault = elementImageDefault.getAttribute("image-default");
            if (imageDefault) {
                files = [
                    {
                        source: imageDefault
                    }
                ]
            }
        }

        filePond[filepondImage.name] = FilePond.create(filepondImage, {
            labelIdle: "+",
            imagePreviewHeight: 150, 
            stylePanelLayout: 'compact',
            files: files
        })
    })
}

// Filepond Image Multi
const listFilepondImageMulti = document.querySelectorAll("[filepond-image-multi]");
let filePondMulti = {};
if (listFilepondImageMulti) {
    listFilepondImageMulti.forEach(filepondImage => {
        FilePond.registerPlugin(FilePondPluginImagePreview, FilePondPluginFileValidateType);

        let files = null;
        const elementListImageDefault = filepondImage.closest("[list-image-default]");
        if (elementListImageDefault) {
            let listImageDefault = elementListImageDefault.getAttribute("list-image-default");
            if (listImageDefault) {
                listImageDefault = JSON.parse(listImageDefault);
                files = [];

                listImageDefault.forEach(image => {
                    files.push({
                        source: image
                    })
                })

            }
        }

        filePondMulti[filepondImage.name] = FilePond.create(filepondImage, {
            labelIdle: "+",
            files: files,
            imagePreviewHeight: 150
        })
    })
}

// Vẽ biểu đồ doanh thu
const revenueChart = document.querySelector("#revenue-chart");

let currentChart = null;
const drawChart = (now) => {
    // Lấy tháng và năm hiện tại
    const currentMonth = now.getMonth() + 1; // Lấy từ 0 - 11
    const currentYear = now.getFullYear();

    // Lấy tháng trước
    const previousMonthDate = new Date(currentYear, now.getMonth() - 1, 1);
    const previousMonth = previousMonthDate.getMonth() + 1; // Lấy từ 0 - 11
    const previousYear = previousMonthDate.getFullYear();

    // Lấy tổng số ngày
    const daysInMonthCurrent = new Date(currentYear, currentMonth, 0).getDate();
    const daysInMonthPrevious = new Date(previousYear, previousMonth, 0).getDate();
    const days = daysInMonthCurrent > daysInMonthPrevious ? daysInMonthCurrent : daysInMonthPrevious;
    const arrayDay = [];
    for (let i = 1; i <= days; i++) {
        arrayDay.push(i);
    }

    const dataFinal = {
        currentMonth: currentMonth,
        currentYear: currentYear,
        previousMonth: previousMonth,
        previousYear: previousYear,
        arrayDay: arrayDay
    };

    fetch(`/${pathAdmin}/dashboard/revenue-chart`, {
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
                if (currentChart) {
                    currentChart.destroy();
                }

                currentChart = new Chart(revenueChart, {
                    type: 'line',
                    data: {
                        labels: arrayDay,
                        datasets: [
                            {
                            label: `Tháng ${currentMonth}/${currentYear}`,
                            data: data.dataCurrentMonth,
                            borderColor: "#36A1EA",
                            borderWidth: 1.5
                            },
                            {
                            label: `Tháng ${previousMonth}/${previousYear}`,
                            data: data.dataPreviousMonth,
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
        });
}

if (revenueChart) {
    // Lấy ngày hôm nay
    const now = new Date();
    drawChart(now);

    const input = document.querySelector(".inner-filler input");
    if (input) {
        input.addEventListener("change", () => {
            const value = input.value;
            const date = new Date(value);
            drawChart(date);
        })
    }
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

            const formData = new FormData();
            formData.append("name", name);
            formData.append("parent", parent);
            formData.append("position", position);
            formData.append("status", status);
            formData.append("avatar", avatar);
            formData.append("description", description);

            fetch(`/${pathAdmin}/category/create`, {
                method: "POST",
                body: formData
            })
                .then (res => res.json())
                .then (data => {
                    if(data.code == "error") {
                        alert(data.message);
                    }
                    else if (data.code == "success") {
                        window.location.href = `/${pathAdmin}/category/list`
                    }
                })
        })
}

// Category Edit
const categoryEditForm = document.querySelector("#category-edit-form");
if (categoryEditForm) {
    const validator = new JustValidate("#category-edit-form");

    validator
        .addField("#name", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập tên danh mục!"
            },
        ])
        .onSuccess(event => {
            const id = event.target.id.value;
            const name = event.target.name.value;
            const parent = event.target.parent.value;
            const position = event.target.position.value;
            const status = event.target.status.value;

            const avatars = filePond.avatar.getFiles();
            let avatar = null;
            if (avatars.length > 0) {
                avatar = avatars[0].file;
                const elementImageDefault = event.target.avatar.closest("[image-default]");
                if (elementImageDefault) {
                    const imageDefault = elementImageDefault.getAttribute("image-default");
                    if (imageDefault.includes(avatar.name)) {
                        avatar = "keep"; 
                    }
                }
            } else {
                avatar = "deleted"; 
            }
            
            const description = tinymce.get("description").getContent();

            const formData = new FormData();
            formData.append("name", name);
            formData.append("parent", parent);
            formData.append("position", position);
            formData.append("status", status);
            formData.append("avatar", avatar);
            formData.append("description", description);

            fetch(`/${pathAdmin}/category/edit/${id}`, {
                method: "PATCH",
                body: formData
            })
                .then (res => res.json())
                .then (data => {
                    if(data.code == "error") {
                        alert(data.message);
                    }
                    else if (data.code == "success") {
                        window.location.href = `/${pathAdmin}/category/edit/${id}`
                    }
                })
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

            const priceAdult = event.target.priceAdult.value;
            const priceChildren = event.target.priceChildren.value;
            const priceBaby = event.target.priceBaby.value;
            const priceNewAdult = event.target.priceNewAdult.value;
            const priceNewChildren = event.target.priceNewChildren.value;
            const priceNewBaby = event.target.priceNewBaby.value;
            const stockAdult = event.target.stockAdult.value;
            const stockChildren = event.target.stockChildren.value;
            const stockBaby = event.target.stockBaby.value;

            const locations = [];
            const listElementLocation = document.querySelectorAll('input[name="locations"]:checked');
            listElementLocation.forEach(input => {
                locations.push(input.value);
            })

            const time = event.target.time.value;
            const vehicle = event.target.vehicle.value;
            const departureDate = event.target.departureDate.value;
            const information = tinymce.get("information").getContent();

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

            const formData = new FormData();
            formData.append("name", name);
            formData.append("category", category);
            formData.append("position", position);
            formData.append("status", status);
            formData.append("avatar", avatar);
            formData.append("priceAdult", priceAdult);
            formData.append("priceChildren", priceChildren);
            formData.append("priceBaby", priceBaby);
            formData.append("priceNewAdult", priceNewAdult);
            formData.append("priceNewChildren", priceNewChildren);
            formData.append("priceNewBaby", priceNewBaby);
            formData.append("stockAdult", stockAdult);
            formData.append("stockChildren", stockChildren);
            formData.append("stockBaby", stockBaby);
            formData.append("locations", JSON.stringify(locations));
            formData.append("time", time);
            formData.append("vehicle", vehicle);
            formData.append("departureDate", departureDate);
            formData.append("information", information);
            formData.append("schedules", JSON.stringify(schedules));

            if (filePondMulti.images.getFiles().length > 0) {
                filePondMulti.images.getFiles().forEach(item => {
                    formData.append("images", item.file);
                })
            }

            fetch(`/${pathAdmin}/tour/create`, {
                method: "POST",
                body: formData
            })
                .then (res => res.json())
                .then (data => {
                    if(data.code == "error") {
                        alert(data.message);
                    }
                    else if (data.code == "success") {
                        window.location.href = `/${pathAdmin}/tour/list`
                    }
                })
        })
}

// Tour Edit
const tourEditForm = document.querySelector("#tour-edit-form");
if (tourEditForm) {
    const validator = new JustValidate("#tour-edit-form");

    validator
        .addField("#name", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập tên tour!"
            },
        ])
        .onSuccess(event => {
            const id = event.target.id.value;
            const name = event.target.name.value;
            const category = event.target.category.value;
            const position = event.target.position.value;
            const status = event.target.status.value;

            const avatars = filePond.avatar.getFiles();
            let avatar = null;
            if (avatars.length > 0) {
                avatar = avatars[0].file;
                const elementImageDefault = event.target.avatar.closest("[image-default]");
                if (elementImageDefault) {
                    const imageDefault = elementImageDefault.getAttribute("image-default");
                    if (imageDefault.includes(avatar.name)) {
                        avatar = "keep"; 
                    }
                }
            } else {
                avatar = "deleted"; 
            }
            
            const priceAdult = event.target.priceAdult.value;
            const priceChildren = event.target.priceChildren.value;
            const priceBaby = event.target.priceBaby.value;
            const priceNewAdult = event.target.priceNewAdult.value;
            const priceNewChildren = event.target.priceNewChildren.value;
            const priceNewBaby = event.target.priceNewBaby.value;
            const stockAdult = event.target.stockAdult.value;
            const stockChildren = event.target.stockChildren.value;
            const stockBaby = event.target.stockBaby.value;

            const locations = [];
            const listElementLocation = document.querySelectorAll('input[name="locations"]:checked');
            listElementLocation.forEach(input => {
                locations.push(input.value);
            })

            const time = event.target.time.value;
            const vehicle = event.target.vehicle.value;
            const departureDate = event.target.departureDate.value;
            const information = tinymce.get("information").getContent();

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

            const formData = new FormData();
            formData.append("name", name);
            formData.append("category", category);
            formData.append("position", position);
            formData.append("status", status);
            formData.append("avatar", avatar);
            formData.append("priceAdult", priceAdult);
            formData.append("priceChildren", priceChildren);
            formData.append("priceBaby", priceBaby);
            formData.append("priceNewAdult", priceNewAdult);
            formData.append("priceNewChildren", priceNewChildren);
            formData.append("priceNewBaby", priceNewBaby);
            formData.append("stockAdult", stockAdult);
            formData.append("stockChildren", stockChildren);
            formData.append("stockBaby", stockBaby);
            formData.append("locations", JSON.stringify(locations));
            formData.append("time", time);
            formData.append("vehicle", vehicle);
            formData.append("departureDate", departureDate);
            formData.append("information", information);
            formData.append("schedules", JSON.stringify(schedules));

            if (filePondMulti.images.getFiles().length > 0) {
                filePondMulti.images.getFiles().forEach(item => {
                    if (item.file instanceof File) {
                        formData.append("images", item.file);
                    } else {
                        formData.append("images", item.source); 
                    }
                })
            }

            fetch(`/${pathAdmin}/tour/edit/${id}`, {
                method: "PATCH",
                body: formData
            })
                .then (res => res.json())
                .then (data => {
                    if(data.code == "error") {
                        alert(data.message);
                    }
                    else if (data.code == "success") {
                        window.location.href = `/${pathAdmin}/tour/edit/${id}`
                    }
                })
        })
}

// Order Edit
const orderEditForm = document.querySelector("#order-edit-form");
if (orderEditForm) {
    const validator = new JustValidate("#order-edit-form");

    validator
        .addField("#fullName", [
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
            const id = event.target.id.value;
            const fullName = event.target.fullName.value;
            const phone = event.target.phone.value;
            const note = event.target.note.value;
            const paymentMethod = event.target.paymentMethod.value;
            const paymentStatus = event.target.paymentStatus.value;
            const status = event.target.status.value;

            const dataFinal = {
                fullName: fullName,
                phone: phone,
                note: note,
                paymentMethod: paymentMethod, 
                paymentStatus: paymentStatus,
                status: status
            }

            fetch(`/${pathAdmin}/order/edit/${id}`, {
                method: "PATCH",
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
                        window.location.reload();
                    }
                })
        })
}

// Website Info
const websiteForm = document.querySelector("#website-form");
if (websiteForm) {
    const validator = new JustValidate("#website-form");

    validator
        .addField("#websiteName", [
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
            const websiteName = event.target.websiteName.value;
            const phone = event.target.phone.value;
            const email = event.target.email.value;
            const address = event.target.address.value;

            const logos = filePond.logo.getFiles();
            let logo = null;
            if (logos.length > 0) {
                logo = logos[0].file;
                const elementImageDefault = event.target.logo.closest("[image-default]");
                if (elementImageDefault) {
                    const imageDefault = elementImageDefault.getAttribute("image-default");
                    if (imageDefault.includes(logo.name)) {
                        logo = "keep"; 
                    }
                }
            } else {
                logo = "deleted"; 
            }

            const favicons = filePond.favicon.getFiles();
            let favicon = null;
            if (favicons.length > 0) {
                favicon = favicons[0].file;
                const elementImageDefault = event.target.favicon.closest("[image-default]");
                if (elementImageDefault) {
                    const imageDefault = elementImageDefault.getAttribute("image-default");
                    if (imageDefault.includes(favicon.name)) {
                        favicon = "keep"; 
                    }
                }
            } else {
                favicon = "deleted"; 
            }

            const formData = new FormData();
            formData.append("websiteName", websiteName);
            formData.append("phone", phone);
            formData.append("email", email);
            formData.append("address", address);
            formData.append("logo", logo);
            formData.append("favicon", favicon);

            fetch(`/${pathAdmin}/setting/website-info`, {
                method: "PATCH",
                body: formData
            })
                .then (res => res.json())
                .then (data => {
                    if(data.code == "error") {
                        alert(data.message);
                    }
                    else if (data.code == "success") {
                        window.location.reload();
                    }
                })
        })
}

// Admin Create
const adminCreateForm = document.querySelector("#admin-create-form");
if (adminCreateForm) {
    const validator = new JustValidate("#admin-create-form");

    validator
        .addField("#fullName", [
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
            const fullName = event.target.fullName.value;
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

            const formData = new FormData();
            formData.append("fullName", fullName);
            formData.append("email", email);
            formData.append("phone", phone);
            formData.append("role", role);
            formData.append("position", position);
            formData.append("status", status);
            formData.append("password", password);
            formData.append("avatar", avatar);

            fetch(`/${pathAdmin}/setting/account-admin/create`, {
                method: "POST",
                body: formData
            })
            .then(res => res.json())
            .then(data => {
                if (data.code == "error") {
                    alert(data.message);
                }

                if (data.code == "success") {
                    window.location.href = `/${pathAdmin}/setting/account-admin/list`;
                }           
            });
        })
}

// Admin Edit
const adminEditForm = document.querySelector("#admin-edit-form");
if (adminEditForm) {
    const validator = new JustValidate("#admin-edit-form");

    validator
        .addField("#fullName", [
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
                validator: value => value ? /^(?=.*[A-Za-z])(?=.*\d).{8,}$/.test(value) : true,
                errorMessage: "Mật khẩu tối thiểu 8 kí tự, ít nhất một chữ cái và một chữ số."
            }
        ])
        .onSuccess(event => {
            const id = event.target.id.value;
            const fullName = event.target.fullName.value;
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
                const elementImageDefault = event.target.avatar.closest("[image-default]");
                if (elementImageDefault && elementImageDefault.getAttribute("image-default").includes(avatar.name)) {
                    avatar = "keep"; 
                }
            } else {
                avatar = "deleted";
            }
            
            const formData = new FormData();
            formData.append("fullName", fullName);
            formData.append("email", email);
            formData.append("phone", phone);
            formData.append("role", role);
            formData.append("position", position);
            formData.append("status", status);
            formData.append("password", password);
            formData.append("avatar", avatar);

            fetch(`/${pathAdmin}/setting/account-admin/edit/${id}`, {
                method: "PATCH",
                body: formData
            })
            .then(res => res.json())
            .then(data => {
                if(data.code == "error") {
                        alert(data.message);
                }
                else if (data.code == "success") {
                    window.location.reload();
                }
            });
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
            const description = event.target.description.value;
            
            const permissions = [];
            const listElementPermission = document.querySelectorAll('input[name="permissions"]:checked');
            listElementPermission.forEach(input => {
                permissions.push(input.value);
            })

            const dataFinal = {
                name: name,
                description: description,
                permissions: permissions
            }

            fetch(`/${pathAdmin}/setting/role/create`, {
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
                            window.location.href = `/${pathAdmin}/setting/role/list`
                        }
                    })
        })
}

// Role Edit
const roleEditForm = document.querySelector("#role-edit-form");
if (roleEditForm) {
    const validator = new JustValidate("#role-edit-form");

    validator
        .addField("#name", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập tên nhóm quyền!"
            },
        ])
        .onSuccess(event => {
            const id = event.target.id.value;
            const name = event.target.name.value;
            const description = event.target.description.value;
            const permissions = [];
            const listElementPermission = document.querySelectorAll('input[name="permissions"]:checked');
            listElementPermission.forEach(input => {
                permissions.push(input.value);
            })

            const dataFinal = {
                name: name,
                description: description,
                permissions: permissions
            }

            fetch(`/${pathAdmin}/setting/role/edit/${id}`, {
                    method: "PATCH",
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
                            window.location.href = `/${pathAdmin}/setting/role/edit/${id}`
                        }
                    })
        })
}

// Profile Edit
const profileEditForm = document.querySelector("#profile-edit-form");
if (profileEditForm) {
    const validator = new JustValidate("#profile-edit-form");

    validator
        .addField("#fullName", [
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
            const id = event.target.id.value;
            const fullName = event.target.fullName.value;
            const email = event.target.email.value;
            const phone = event.target.phone.value;

            const avatars = filePond.avatar.getFiles();
            let avatar = null;

            if (avatars.length > 0) {
                avatar = avatars[0].file;
                const elementImageDefault = event.target.avatar.closest("[image-default]");
                if (elementImageDefault && elementImageDefault.getAttribute("image-default").includes(avatar.name)) {
                    avatar = "keep"; 
                }
            } else {
                avatar = "deleted";
            }

            const formData = new FormData();
            formData.append("fullName", fullName);
            formData.append("email", email);
            formData.append("phone", phone);
            formData.append("avatar", avatar);

            fetch(`/${pathAdmin}/profile/edit/${id}`, {
                method: "PATCH",
                body: formData
            })
            .then(res => res.json())
            .then(data => {
                if(data.code == "error") {
                        alert(data.message);
                }
                else if (data.code == "success") {
                    window.location.reload();
                }
            });
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

            const dataFinal = {
                password: password
            }

            fetch(`/${pathAdmin}/profile/change-password`, {
                    method: "PATCH",
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
                            window.location.reload();
                        }
                    })
        })
}

// Sider
const sider = document.querySelector(".sider");
if (sider) {
    const pathNameCurrent = window.location.pathname;
    const splitPathNameCurrent = pathNameCurrent.split("/");
    const menuList = sider.querySelectorAll("a");
    menuList.forEach(item => {
        const pathName = new URL(item.href).pathname;
        const splitPathName = pathName.split("/");
        if (splitPathName[1] == splitPathNameCurrent[1] && splitPathName[2] == splitPathNameCurrent[2]) {
            item.classList.add("active");
        }
    })
}

// Logout
const buttonLogout = document.querySelector(".sider .inner-logout");
if (buttonLogout) {
    buttonLogout.addEventListener("click", () => {
        fetch (`/${pathAdmin}/account/logout`, {
            method: "POST"
        })
            .then (res => res.json())
            .then (data => {
                if (data.code == "success") {
                    window.location.href = `/${pathAdmin}/account/login`
                }
            })
    })
}

// Alert
const alertTime = document.querySelector("[alert-time]");
if (alertTime) {
    let time = alertTime.getAttribute("alert-time");
    time = time ? parseInt(time) : 4000;
    setTimeout(() => {
        alertTime.remove();
    }, time)
}

// Button Delete
const listButtonDelete = document.querySelectorAll("[button-delete]");
if (listButtonDelete.length > 0) {
    listButtonDelete.forEach(button => {
        button.addEventListener("click", () => {
            const dataApi = button.getAttribute("data-api");
            fetch(dataApi, {
                    method: "PATCH",
                })
                    .then (res => res.json())
                    .then (data => {
                        if(data.code == "error") {
                            alert(data.message);
                        }
                        else if (data.code == "success") {
                            window.location.reload();
                        }
                    })
        })
    })
}

// Button Undo
const listButtonUndo = document.querySelectorAll("[button-undo]");
if (listButtonUndo.length > 0) {
    listButtonUndo.forEach(button => {
        button.addEventListener("click", () => {
            const dataApi = button.getAttribute("data-api");
            fetch(dataApi, {
                    method: "PATCH",
                })
                    .then (res => res.json())
                    .then (data => {
                        if(data.code == "error") {
                            alert(data.message);
                        }
                        else if (data.code == "success") {
                            window.location.reload();
                        }
                    })
        })
    })
}

// Button Delete Destroy
const listButtonDeleteDestroy = document.querySelectorAll("[button-delete-destroy]");
if (listButtonDeleteDestroy.length > 0) {
    listButtonDeleteDestroy.forEach(button => {
        button.addEventListener("click", () => {
            const dataApi = button.getAttribute("data-api");
            fetch(dataApi, {
                    method: "PATCH",
                })
                    .then (res => res.json())
                    .then (data => {
                        if(data.code == "error") {
                            alert(data.message);
                        }
                        else if (data.code == "success") {
                            window.location.reload();
                        }
                    })
        })
    })
}

// Filter Status
const filterStatus = document.querySelector("[filter-status]");
if (filterStatus) {
    const url = new URL(window.location.href);

    filterStatus.addEventListener("change", () => {
        const value = filterStatus.value;
        if (value) {
            url.searchParams.set("status", value);
        }
        else {
            url.searchParams.delete("status");
        }
        window.location.href = url.href;
    })

    // Hiển thị lựa chọn mặc định
    const valueCurrent = url.searchParams.get("status");
    if (valueCurrent) {
        filterStatus.value = valueCurrent;
    }
}

// Filter Created By
const filterCreatedBy = document.querySelector("[filter-created-by]");
if (filterCreatedBy) {
    const url = new URL(window.location.href);

    filterCreatedBy.addEventListener("change", () => {
        const value = filterCreatedBy.value;
        if (value) {
            url.searchParams.set("createdBy", value);
        }
        else {
            url.searchParams.delete("createdBy");
        }
        window.location.href = url.href;
    })

    // Hiển thị lựa chọn mặc định
    const valueCurrent = url.searchParams.get("createdBy");
    if (valueCurrent) {
        filterCreatedBy.value = valueCurrent;
    }
}

// Filter Start Date
const filterStartDate = document.querySelector("[filter-start-date]");
if (filterStartDate) {
    const url = new URL(window.location.href);

    filterStartDate.addEventListener("change", () => {
        const value = filterStartDate.value;
        if (value) {
            url.searchParams.set("startDate", value);
        }
        else {
            url.searchParams.delete("startDate");
        }
        window.location.href = url.href;
    })

    // Hiển thị lựa chọn mặc định
    const valueCurrent = url.searchParams.get("startDate");
    if (valueCurrent) {
        filterStartDate.value = valueCurrent;
    }
}

// Filter End Date
const filterEndDate = document.querySelector("[filter-end-date]");
if (filterEndDate) {
    const url = new URL(window.location.href);

    filterEndDate.addEventListener("change", () => {
        const value = filterEndDate.value;
        if (value) {
            url.searchParams.set("endDate", value);
        }
        else {
            url.searchParams.delete("endDate");
        }
        window.location.href = url.href;
    })

    // Hiển thị lựa chọn mặc định
    const valueCurrent = url.searchParams.get("endDate");
    if (valueCurrent) {
        filterEndDate.value = valueCurrent;
    }
}

// Filter Role
const filterRole = document.querySelector("[filter-role]");
if (filterRole) {
    const url = new URL(window.location.href);

    filterRole.addEventListener("change", () => {
        const value = filterRole.value;
        if (value) {
            url.searchParams.set("role", value);
        }
        else {
            url.searchParams.delete("role");
        }
        window.location.href = url.href;
    })

    // Hiển thị lựa chọn mặc định
    const valueCurrent = url.searchParams.get("role");
    if (valueCurrent) {
        filterRole.value = valueCurrent;
    }
}

// Reset Filter
const filterReset = document.querySelector("[filter-reset]");
if (filterReset) {
    const url = new URL(window.location.href);

    filterReset.addEventListener("click", () => {
        url.search = "";
        window.location.href = url.href;
    })
}

// Check All
const checkAll = document.querySelector("[check-all]");
if (checkAll) {
    checkAll.addEventListener("click", () => {
        const checkItems = document.querySelectorAll("[check-item]");
        checkItems.forEach(item => {
            item.checked = checkAll.checked;
        })
    })
}

// Change Multi
const changeMulti = document.querySelector("[change-multi]");
if (changeMulti) {
    const select = changeMulti.querySelector("select");
    const button = changeMulti.querySelector("button");

    button.addEventListener("click", () => {
        const option = select.value;
        const listInputChecked = document.querySelectorAll("[check-item]:checked");
        if (option && listInputChecked.length > 0) {
            const ids = [];
            listInputChecked.forEach(item => {
                const id = item.getAttribute("check-item");
                ids.push(id);
            })

            const dataFinal = {
                option: option,
                ids: ids
            }

            const dataApi = changeMulti.getAttribute("data-api");

            fetch(dataApi, {
                    method: "PATCH",
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
                            window.location.reload();
                        }
                    })
        }
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
            if (value) {
                url.searchParams.set("keyword", value);
            }
            else {
                url.searchParams.delete("keyword");
            }
            window.location.href = url.href;
        }
    })

    // Hiển thị lựa chọn mặc định
    const valueCurrent = url.searchParams.get("keyword");
    if (valueCurrent) {
        inputSearch.value = valueCurrent;
    }
}

// Pagination
const pagination = document.querySelector("[pagination]");
if (pagination) {
    const url = new URL(window.location.href);

    pagination.addEventListener("change", event => {
        const value = pagination.value;
        if (pagination) {
            url.searchParams.set("page", value);
        }
        else {
            url.searchParams.delete("page");
        }
        window.location.href = url.href;
    })

    // Hiển thị lựa chọn mặc định
    const valueCurrent = url.searchParams.get("page");
    if (valueCurrent) {
        pagination.value = valueCurrent;
    }
}