// Context Path Support for Apache Tomcat / Root deployments
const CONTEXT_PATH = window.location.pathname.startsWith('/project') ? '/project' : '';

// Menu Mobile
const buttonMenuMobile = document.querySelector(".header .inner-menu-mobile");
if (buttonMenuMobile) {
    // Click vào button mở menu
    const menu = document.querySelector(".header .inner-menu");
    buttonMenuMobile.addEventListener("click", () => {
        menu.classList.add("active");
    });

    // Click vào overlay đóng menu
    const overlay = document.querySelector("header .inner-menu .inner-overlay");
    if (overlay) {
        overlay.addEventListener("click", () => {
            menu.classList.remove("active");
        });
    }

    // Click vào icon down mở menu
    const listButtonSubMenu = menu.querySelectorAll("ul > li > i");
    listButtonSubMenu.forEach(button => {
        button.addEventListener("click", () => {
            button.parentNode.classList.toggle("active");
        })
    });
}

// Box Location Section 1
const boxLocationSection1 = document.querySelector(".section-1 .inner-location");
if (boxLocationSection1) {
    // Ẩn hiện box suggest
    const input = boxLocationSection1.querySelector(".inner-input");
    input.addEventListener("focus", () => {
        boxLocationSection1.classList.add("active");
    });

    document.addEventListener("click", (event) => {
        if (!boxLocationSection1.contains(event.target)) {
            boxLocationSection1.classList.remove("active");
        }
    });

    // Sự kiện click vào từng item
    const listItem = boxLocationSection1.querySelectorAll(".inner-suggest-list .inner-item");
    listItem.forEach(item => {
        item.addEventListener("mousedown", (e) => {
            e.preventDefault();
            const title = item.querySelector(".inner-item-title").innerHTML;
            input.value = title.trim();
            boxLocationSection1.classList.remove("active");
            input.blur();
        });
    });
}

// Box Quantity Section 1
const boxUserSection1 = document.querySelector(".section-1 .inner-user");
if (boxUserSection1) {
    // Ẩn hiện box quantity
    const input = boxUserSection1.querySelector(".inner-input")
    input.addEventListener("focus", () => {
        boxUserSection1.classList.add("active");
    });

    document.addEventListener("click", (event) => {
        if (!boxUserSection1.contains(event.target)) {
            boxUserSection1.classList.remove("active");
        }
    });

    // Thêm số lượng vào ô input
    const updateQuantityInput = () => {
        const listBoxNumber = boxUserSection1.querySelectorAll(".inner-quantity .inner-number");
        const listNumber = [];
        listBoxNumber.forEach(boxNumber => {
            const number = parseInt(boxNumber.innerHTML);
            listNumber.push(number);
        })
        input.value = `NL: ${listNumber[0]}, TE: ${listNumber[1]}, EB: ${listNumber[2]}`;
    }

    // Bắt sự kiện nút up, down
    const listButtonUp = boxUserSection1.querySelectorAll(".inner-up");
    listButtonUp.forEach(button => {
        button.addEventListener("click", () => {
            const parent = button.parentNode;
            const boxNumber = parent.querySelector(".inner-number");
            const number = parseInt(boxNumber.innerHTML);
            const numberUpdate = number + 1;
            boxNumber.innerHTML = numberUpdate;
            updateQuantityInput();
        })
    });

    const listButtonDown = boxUserSection1.querySelectorAll(".inner-down");
    listButtonDown.forEach(button => {
        button.addEventListener("click", () => {
            const parent = button.parentNode;
            const boxNumber = parent.querySelector(".inner-number");
            const number = parseInt(boxNumber.innerHTML);
            if (number > 0) {
                const numberUpdate = number - 1;
                boxNumber.innerHTML = numberUpdate;
                updateQuantityInput();
            }
        })
    });
}

// Clock Expire
const clockExpire = document.querySelector("[clock-expire]");
if (clockExpire) {
    const expireDateTimeString = clockExpire.getAttribute("clock-expire");
    const expireDateTime = new Date(expireDateTimeString);

    updateClock = () => {
        const now = new Date();
        const remainingTime = expireDateTime - now; //milliseconds
        if (remainingTime > 0) {
            const days = Math.floor(remainingTime / (24 * 60 * 60 * 1000));
            const hours = Math.floor((remainingTime / (60 * 60 * 1000)) % 24);
            const minutes = Math.floor((remainingTime / (60 * 1000)) % 60);
            const seconds = Math.floor((remainingTime / 1000) % 60);

            const listBoxNumber = clockExpire.querySelectorAll(".inner-time");
            listBoxNumber[0].innerHTML = `${days}`.padStart(2, "0");
            listBoxNumber[1].innerHTML = `${hours}`.padStart(2, "0");
            listBoxNumber[2].innerHTML = `${minutes}`.padStart(2, "0");
            listBoxNumber[3].innerHTML = `${seconds}`.padStart(2, "0");
        }
        else clearInterval(intervalClock);
    };

    updateClock();
    const intervalClock = setInterval(updateClock, 1000);
}

// Box Filter
const buttonFilterMobile = document.querySelector(".section-9 .inner-filter-mobile");
if (buttonFilterMobile) {
    buttonFilterMobile.addEventListener("click", () => {
        const boxLeft = document.querySelector(".section-9 .inner-left");
        boxLeft.classList.toggle("active");

        const overlay = document.querySelector(".section-9 .inner-left .inner-overlay");
        if (overlay) {
            overlay.addEventListener("click", () => {
                boxLeft.classList.remove("active");
            });
        }
    });
}

// Box Tour Info
const boxTourInfo = document.querySelector(".section-10 .box-tour-info");
if (boxTourInfo) {
    const buttonReadMore = boxTourInfo.querySelector(".section-10 .inner-read-more button");
    buttonReadMore.addEventListener("click", () => {
        boxTourInfo.classList.toggle("active");

        if (boxTourInfo.classList.contains("active")) buttonReadMore.innerHTML = "Ẩn bớt";
        else buttonReadMore.innerHTML = "Xem tất cả";
    });

    new Viewer(boxTourInfo);
}

// Khởi tạo AOS
AOS.init();

// Swiper Section 2
const swiperSection2 = document.querySelector(".swiper-section-2");
if (swiperSection2) {
    new Swiper('.swiper-section-2', {
        slidesPerView: 1,
        spaceBetween: 20,
        loop: true,
        autoplay: {
            delay: 4000,
            disableOnInteraction: false,
        },
        breakpoints: {
            992: {
                slidesPerView: 2
            },
            1200: {
                slidesPerView: 3
            }
        }
    });
}

// Swiper Section 3
const swiperSection3 = document.querySelector(".swiper-section-3");
if (swiperSection3) {
    new Swiper('.swiper-section-3', {
        slidesPerView: 1,
        spaceBetween: 20,
        loop: true,
        autoplay: {
            delay: 4000,
            disableOnInteraction: false,
        },
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
        breakpoints: {
            576: {
                slidesPerView: 2
            },
            992: {
                slidesPerView: 3
            }
        }
    });
}

// Swiper Box Image
const boxImages = document.querySelector(".box-images");
if (boxImages) {
    const swiperBoxImageThumb = new Swiper('.swiper-box-image-thumb', {
        spaceBetween: 4.62,
        slidesPerView: 4,
        loop: true,
        autoplay: {
            delay: 4000,
            disableOnInteraction: false,
        },
        breakpoints: {
            576: {
                spaceBetween: 9.37
            }
        }
    });
    new Swiper('.swiper-box-image-main', {
        spaceBetween: 0,
        thumbs: {
            swiper: swiperBoxImageThumb,
        },
    });
}

// Zoom Box Image Main
const boxImageMain = document.querySelector(".box-images .inner-image-main");
if (boxImageMain) {
    new Viewer(boxImageMain);
}

// Zoom Box Tour Schedule
const boxTourSchedule = document.querySelector(".box-tour-schedule");
if (boxTourSchedule) {
    new Viewer(boxTourSchedule);
}

// Email Form
const emailForm = document.querySelector("#email-form");
if (emailForm) {
    const validator = new JustValidate("#email-form");

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
            const emailInput = document.querySelector("#email-input");
            const email = emailInput ? emailInput.value.trim() : (event.target.email ? event.target.email.value.trim() : "");

            const dataFinal = {
                email: email,
            };

            fetch(`${CONTEXT_PATH}/contact/create`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dataFinal)
            })
                .then(res => res.json())
                .then(data => {
                    if (data.code === "success") {
                        if (typeof Swal !== "undefined") {
                            Swal.fire({
                                icon: "success",
                                title: "Đăng ký thành công!",
                                text: data.message || "Cảm ơn bạn đã đăng ký nhận thông tin từ DUT Travel. Chúng tôi sẽ gửi những chương trình hấp dẫn nhất đến bạn!",
                                confirmButtonColor: "#2563EB",
                                confirmButtonText: "Tuyệt vời",
                                customClass: {
                                    popup: "client-swal-modal"
                                }
                            });
                        } else {
                            showAlert(data.message || "Đăng ký thành công!", "success");
                        }
                        if (emailInput) {
                            emailInput.value = "";
                        }
                    } else if (data.code === "exist" || data.code === "warning" || (data.message && data.message.includes("đã được đăng ký"))) {
                        if (typeof Swal !== "undefined") {
                            Swal.fire({
                                icon: "info",
                                title: "Email đã tồn tại!",
                                text: data.message || "Email của bạn đã được đăng ký nhận thông tin trước đó rồi. Đừng quên kiểm tra hộp thư nhé!",
                                confirmButtonColor: "#2563EB",
                                confirmButtonText: "Đã hiểu",
                                customClass: {
                                    popup: "client-swal-modal"
                                }
                            });
                        } else {
                            showAlert(data.message, "warning");
                        }
                    } else {
                        if (typeof Swal !== "undefined") {
                            Swal.fire({
                                icon: "error",
                                title: "Chưa thể đăng ký!",
                                text: data.message || "Có lỗi xảy ra, vui lòng thử lại sau.",
                                confirmButtonColor: "#2563EB",
                                confirmButtonText: "Đóng",
                                customClass: {
                                    popup: "client-swal-modal"
                                }
                            });
                        } else {
                            showAlert(data.message || "Đã xảy ra lỗi!", "error");
                        }
                    }
                })
                .catch(err => {
                    console.error("Lỗi gửi email liên hệ:", err);
                    if (typeof Swal !== "undefined") {
                        Swal.fire({
                            icon: "error",
                            title: "Lỗi kết nối!",
                            text: "Không thể gửi thông tin lúc này. Vui lòng kiểm tra lại mạng hoặc thử lại sau!",
                            confirmButtonColor: "#2563EB",
                            confirmButtonText: "Đóng",
                            customClass: {
                                popup: "client-swal-modal"
                            }
                        });
                    } else {
                        showAlert("Không thể gửi thông tin lúc này. Vui lòng thử lại sau!", "error");
                    }
                });
        })
}

// Coupon Form
const couponForm = document.querySelector("#coupon-form");
if (couponForm) {
    const validator = new JustValidate("#coupon-form");

    validator
        .addField("#coupon-input", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập mã coupon!"
            },
        ])
        .onSuccess(event => {
            const coupon = event.target.coupon.value;
        })
}

// Order Form
const orderForm = document.querySelector("#order-form");
if (orderForm) {
    const validator = new JustValidate("#order-form");

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
        .addField("#phone-input", [
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
        .addField("#email-input", [
            {
                rule: "required",
                errorMessage: "Vui lòng nhập email nhận lịch trình tour!"
            },
            {
                rule: "email",
                errorMessage: "Địa chỉ email không đúng định dạng!"
            }
        ])
        .onSuccess(event => {
            const fullName = event.target.fullName.value;
            const phone = event.target.phone.value;
            const email = event.target.email ? event.target.email.value.trim() : "";
            const note = event.target.note.value;
            const method = event.target.method.value;

            let cart = JSON.parse(localStorage.getItem("cart"));
            cart = cart.filter(item => item.checked == true && item.quantityAdult + item.quantityChildren + item.quantityBaby > 0);

            cart = cart.map(item => {
                return {
                    tourId: item.tourId,
                    quantityAdult: item.quantityAdult,
                    quantityChildren: item.quantityChildren,
                    quantityBaby: item.quantityBaby,
                    locationFrom: item.locationFrom
                }
            })

            if (cart.length > 0) {
                const dataFinal = {
                    fullName: fullName,
                    phone: phone,
                    email: email,
                    note: note,
                    paymentMethod: method,
                    couponCode: currentCoupon ? currentCoupon.code : "",
                    discount: currentCoupon ? currentCoupon.discount : 0,
                    items: cart
                };

            fetch(`${CONTEXT_PATH}/order/create`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dataFinal)
            })
                .then(res => res.json())
                .then(data => {
                    if (data.code == "error") {
                        if (typeof Swal !== "undefined") {
                            Swal.fire({
                                icon: "error",
                                title: "Thông báo",
                                text: data.message || "Đã xảy ra lỗi khi tạo đơn hàng!",
                                confirmButtonColor: "#2563EB",
                                confirmButtonText: "Đóng"
                            });
                        } else {
                            showAlert(data.message || "Đã xảy ra lỗi!", "error");
                        }
                    }
                    else if (data.code == "success") {
                        // Xóa mã giảm giá đã áp dụng
                        try {
                            sessionStorage.removeItem("appliedCoupon");
                        } catch(e) {}
                        currentCoupon = null;

                        // Cập nhật lại giỏ hàng
                        let cart = JSON.parse(localStorage.getItem("cart"));
                        cart = cart.filter(item => item.checked == false);
                        localStorage.setItem("cart", JSON.stringify(cart));

                        switch (method) {
                            case "money":
                            case "bank":
                                // Chuyển sang trang đặt hàng thành công
                                window.location.href = `${CONTEXT_PATH}/order/success?orderCode=${data.orderCode}&phone=${phone}`;
                                break;
                            case "vnpay":
                                // Chuyển sang trang thanh toán bằng VNPay
                                window.location.href = `${CONTEXT_PATH}/order/payment-vnpay?orderCode=${data.orderCode}`;
                                break;

                        }
                    }
                });

                
            }
        })

    // List Input Method
    const listInputMethod = orderForm.querySelectorAll("input[name='method']");
    const elementInfoBank = orderForm.querySelector(".inner-info-bank");

    listInputMethod.forEach(inputMethod => {
        inputMethod.addEventListener("change", () => {
            if (inputMethod.value == "bank") elementInfoBank.classList.add("active");
            else elementInfoBank.classList.remove("active");
        })
    })
}

// Alert
function showAlert(message, type = "success", duration = 4000) {
    if (!message) return;
    const existing = document.querySelector(".alert");
    if (existing) existing.remove();

    const div = document.createElement("div");
    div.className = `alert alert-${type}`;
    div.setAttribute("alert-time", duration);
    let iconClass = "fa-circle-check";
    if (type === "error" || type === "danger") iconClass = "fa-circle-xmark";
    else if (type === "warning") iconClass = "fa-triangle-exclamation";
    else if (type === "info") iconClass = "fa-circle-info";

    div.innerHTML = `
        <i class="fa-solid ${iconClass}"></i>
        <span>${message}</span>
        <span style="cursor:pointer;margin-left:8px;font-size:18px;opacity:0.7" onclick="this.parentElement.remove()">&times;</span>
    `;
    document.body.appendChild(div);
    setTimeout(() => {
        if (div.parentElement) div.remove();
    }, duration);
}

const alertTime = document.querySelector("[alert-time]");
if (alertTime) {
    let time = alertTime.getAttribute("alert-time");
    time = time ? parseInt(time) : 4000;
    setTimeout(() => {
        alertTime.remove();
    }, time);
}

// Box Filter
const boxFilter = document.querySelector(".box-filter");
if (boxFilter) {
    const url = new URL(`${window.location.origin}${CONTEXT_PATH}/search`);
    const buttonApply = boxFilter.querySelector(".inner-button");

    buttonApply.addEventListener("click", () => {
        const filterList = [
            "locationFrom",
            "locationTo",
            "departureDate",
            "stockAdult",
            "stockChildren",
            "stockBaby",
            "price"
        ]

        filterList.forEach(item => {
            const value = boxFilter.querySelector(`[name=${item}]`).value;
            if (value) {
                url.searchParams.set(item, value);
            } else {
                url.searchParams.delete(item);
            }
        })

        window.location.href = url.href;
    })
}

// Form Search
const formSearch = document.querySelector("[form-search]");
if (formSearch) {
    formSearch.addEventListener("submit", (event) => {
        event.preventDefault();

        const url = new URL(`${window.location.origin}${CONTEXT_PATH}/search`);

        const locationTo = formSearch.locationTo ? formSearch.locationTo.value.trim() : "";
        if (locationTo) url.searchParams.set("locationTo", locationTo);

        const stockAdultEl = formSearch.querySelector("[stock-adult]");
        const stockAdult = stockAdultEl ? parseInt(stockAdultEl.innerHTML) : 0;
        if (stockAdult > 0) url.searchParams.set("stockAdult", stockAdult);

        const stockChildrenEl = formSearch.querySelector("[stock-children]");
        const stockChildren = stockChildrenEl ? parseInt(stockChildrenEl.innerHTML) : 0;
        if (stockChildren > 0) url.searchParams.set("stockChildren", stockChildren);

        const stockBabyEl = formSearch.querySelector("[stock-baby]");
        const stockBaby = stockBabyEl ? parseInt(stockBabyEl.innerHTML) : 0;
        if (stockBaby > 0) url.searchParams.set("stockBaby", stockBaby);

        const departureDate = formSearch.departureDate ? formSearch.departureDate.value : "";
        if (departureDate) url.searchParams.set("departureDate", departureDate);

        window.location.href = url.href;
    });
}

// Box Tour Detail
const boxTourDetail = document.querySelector(".box-tour-detail");
if (boxTourDetail) {
    const inputStockAdult = document.querySelector("[input-stock-adult]");
    const inputStockChildren = document.querySelector("[input-stock-children]");
    const inputStockBaby = document.querySelector("[input-stock-baby]");

    const drawBoxDetail = () => {
        const quantityAdult = parseInt(inputStockAdult.value);
        const quantityChildren = parseInt(inputStockChildren.value);
        const quantityBaby = parseInt(inputStockBaby.value);

        const stockAdult = boxTourDetail.querySelector("[stock-adult]");
        const stockChildren = boxTourDetail.querySelector("[stock-children]");
        const stockBaby = boxTourDetail.querySelector("[stock-baby]");
        stockAdult.innerHTML = quantityAdult + " ";
        stockChildren.innerHTML = quantityChildren + " ";
        stockBaby.innerHTML = quantityBaby + " ";

        const priceAdult = parseInt(inputStockAdult.getAttribute("price"));
        const priceChildren = parseInt(inputStockChildren.getAttribute("price"));
        const priceBaby = parseInt(inputStockBaby.getAttribute("price"));
        const totalPrice = priceAdult * quantityAdult + priceChildren * quantityChildren + priceBaby * quantityBaby;

        const elementTotalPrice = boxTourDetail.querySelector("[total-price]");
        elementTotalPrice.innerHTML = totalPrice.toLocaleString("vi-VN") + "đ";
    };

    inputStockAdult.addEventListener("change", drawBoxDetail);
    inputStockChildren.addEventListener("change", drawBoxDetail);
    inputStockBaby.addEventListener("change", drawBoxDetail);

    const buttonAddToCart = boxTourDetail.querySelector(".inner-button-add-cart");
    if (buttonAddToCart) {
        buttonAddToCart.addEventListener("click", () => {
            if (buttonAddToCart.disabled || buttonAddToCart.classList.contains("disabled")) {
                if (typeof Swal !== "undefined") {
                    Swal.fire({
                        icon: "warning",
                        title: "Thông báo",
                        text: "Tour này hiện đã hết chỗ. Quý khách vui lòng chọn ngày khởi hành khác hoặc liên hệ hotline để được hỗ trợ!",
                        confirmButtonColor: "#2563EB",
                        confirmButtonText: "Đã hiểu"
                    });
                }
                return;
            }
            const tourId = buttonAddToCart.getAttribute("tour-id");
            const quantityAdult = parseInt(inputStockAdult.value) || 0;
            const quantityChildren = parseInt(inputStockChildren.value) || 0;
            const quantityBaby = parseInt(inputStockBaby.value) || 0;
            const locationFrom = boxTourDetail.querySelector("[location-from]") ? boxTourDetail.querySelector("[location-from]").value : 1;

            if (quantityAdult > 0 || quantityChildren > 0 || quantityBaby > 0) {
                const cartItem = {
                    tourId: tourId,
                    quantityAdult: quantityAdult,
                    quantityChildren: quantityChildren,
                    quantityBaby: quantityBaby,
                    locationFrom: locationFrom,
                    checked: true
                };

                const cart = JSON.parse(localStorage.getItem("cart")) || [];
                const indexItemExist = cart.findIndex(item => item.tourId == tourId);
                if (indexItemExist != -1) {
                    cart[indexItemExist] = cartItem;
                } else {
                    cart.push(cartItem);
                }
                localStorage.setItem("cart", JSON.stringify(cart));
                window.location.href = `${CONTEXT_PATH}/cart`;
            } else {
                if (typeof Swal !== "undefined") {
                    Swal.fire({
                        icon: "info",
                        title: "Thông báo",
                        text: "Vui lòng chọn số lượng hành khách ít nhất 1 người trước khi thêm vào giỏ hàng!",
                        confirmButtonColor: "#2563EB",
                        confirmButtonText: "Đã hiểu"
                    });
                }
            }
        });
    }
}

// Initial cart
const cart = localStorage.getItem("cart");
if (!cart || cart === "undefined" || cart === "null") {
    localStorage.setItem("cart", JSON.stringify([]));
}

// Mini Cart
const miniCart = document.querySelector("[mini-cart]");
if (miniCart) {
    const cart = JSON.parse(localStorage.getItem("cart"));
    miniCart.innerHTML = cart.length;
}

// Page Cart
let currentCoupon = null;
try {
    const savedCoupon = sessionStorage.getItem("appliedCoupon");
    if (savedCoupon) {
        currentCoupon = JSON.parse(savedCoupon);
    }
} catch (e) {}

const drawCart = () => {
    const cart = localStorage.getItem("cart");

    fetch(`${CONTEXT_PATH}/cart/detail`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: cart
    })
        .then(res => res.json())
        .then(data => {
            if (data.code == "success") {
                const htmlCart = data.cart.map(item => `
                    <div class="inner-tour-item" bis_skin_checked="1">
                        <div class="inner-actions" bis_skin_checked="1">
                            <button class="inner-delete" button-delete tour-id="${item.tourId}">
                                <i class="fa-solid fa-xmark"></i>
                            </button>
                            <input class="inner-check" type="checkbox" input-check tour-id="${item.tourId}" ${item.checked ? 'checked' : ''}>
                        </div>
                        <div class="inner-product" bis_skin_checked="1">
                            <div class="inner-image" bis_skin_checked="1">
                                <a href="${CONTEXT_PATH}/tour/detail/${item.slug}">
                                    <img alt="${item.name}" src="${item.avatar}">
                                </a>
                            </div>
                            <div class="inner-content" bis_skin_checked="1">
                                <div class="inner-title" bis_skin_checked="1">
                                    <a href="${CONTEXT_PATH}/tour/detail/${item.slug}">${item.name}</a>
                                </div>
                                <div class="inner-meta" bis_skin_checked="1">
                                    <div class="inner-meta-item" bis_skin_checked="1">Mã Tour: <b>${item.code || ('DUT000' + item.tourId)}</b></div>
                                    <div class="inner-meta-item" bis_skin_checked="1">Ngày Khởi Hành: <b>${item.departureDateFormatted}</b></div>
                                    <div class="inner-meta-item" bis_skin_checked="1">Khởi Hành Tại: <b>${item.locationFromName}</b></div>
                                </div>
                            </div>
                        </div>
                        <div class="inner-quantity" bis_skin_checked="1">
                            <div class="inner-label" bis_skin_checked="1">Số Lượng Hành Khách</div>
                            <div class="inner-list" bis_skin_checked="1">
                                <div class="inner-item" bis_skin_checked="1">
                                    <label class="inner-item-label" for="quantityAdult">Người lớn:</label>
                                    <div class="inner-item-input" bis_skin_checked="1">
                                        <input id="quantityAdult" min="0" max="${item.stockAdult}" value="${item.quantityAdult}" name="quantityAdult" type="number" input-quantity tour-id=${item.tourId}>
                                    </div>
                                    <div class="inner-price" bis_skin_checked="1">
                                        <span>${item.quantityAdult} </span>
                                        <span>x </span>
                                        <span class="inner-highlight">${item.priceNewAdult.toLocaleString("vi-VN")}</span>
                                    </div>
                                </div>
                                <div class="inner-item" bis_skin_checked="1">
                                    <label class="inner-item-label" for="quantityChildren">Trẻ em:</label>
                                        <div class="inner-item-input" bis_skin_checked="1">
                                        <input id="quantityChildren" min="0" max="${item.stockChildren}" value="${item.quantityChildren}" name="quantityChildren" type="number" input-quantity tour-id="${item.tourId}">
                                    </div>
                                    <div class="inner-price" bis_skin_checked="1">
                                        <span>${item.quantityChildren} </span>
                                        <span>x </span>
                                        <span class="inner-highlight">${item.priceNewChildren.toLocaleString("vi-VN")}</span>
                                    </div>
                                </div>
                                <div class="inner-item" bis_skin_checked="1">
                                    <label class="inner-item-label" for="quantityBaby">Em bé:</label>
                                    <div class="inner-item-input" bis_skin_checked="1">
                                        <input id="quantityBaby" min="0" max="${item.stockBaby}" value="${item.quantityBaby}" name="quantityBaby" type="number" input-quantity tour-id=${item.tourId}>
                                    </div>
                                    <div class="inner-price" bis_skin_checked="1">
                                        <span>${item.quantityBaby} </span>
                                        <span>x </span>
                                        <span class="inner-highlight">${item.priceNewBaby.toLocaleString("vi-VN")}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                `)

                const cartList = document.querySelector("[cart-list]");
                cartList.innerHTML = htmlCart.join("");

                // Cập nhật lại Mini Cart
                miniCart.innerHTML = data.cart.length;

                // Cập nhật lại cart trong localStorage
                localStorage.setItem("cart", JSON.stringify(data.cart));

                // Tính tổng tiền
                const subTotalPrice = data.cart.reduce((sum, item) => {
                    if (item.checked) {
                        return sum + item.priceNewAdult * item.quantityAdult + item.priceNewChildren * item.quantityChildren + item.priceNewBaby * item.quantityBaby;
                    } else {
                        return sum;
                    }
                }, 0);
                    
                let discount = 0;
                if (currentCoupon) {
                    if (subTotalPrice <= 0) {
                        currentCoupon = null;
                        try { sessionStorage.removeItem("appliedCoupon"); } catch(e) {}
                    } else if (currentCoupon.minOrderValue > 0 && subTotalPrice < currentCoupon.minOrderValue) {
                        showAlert(`Đơn hàng không còn đủ giá trị tối thiểu (${currentCoupon.minOrderValue.toLocaleString("vi-VN")}đ) để áp dụng mã ${currentCoupon.code}!`, "warning");
                        currentCoupon = null;
                        try { sessionStorage.removeItem("appliedCoupon"); } catch(e) {}
                    } else {
                        if (currentCoupon.discountType === "percentage") {
                            let disc = subTotalPrice * (currentCoupon.discountValue / 100);
                            if (currentCoupon.maxDiscountValue > 0 && disc > currentCoupon.maxDiscountValue) {
                                disc = currentCoupon.maxDiscountValue;
                            }
                            discount = Math.min(disc, subTotalPrice);
                        } else {
                            discount = Math.min(currentCoupon.discountValue, subTotalPrice);
                        }
                        currentCoupon.discount = discount;
                    }
                }
                const totalPrice = Math.max(0, subTotalPrice - discount);

                const elementCartSubTotal = document.querySelector("[cart-sub-total]");
                const elementDiscount = document.querySelector("[cart-discount]");
                const elementCartTotal = document.querySelector("[cart-total]");

                if (elementCartSubTotal) elementCartSubTotal.innerHTML = subTotalPrice.toLocaleString("vi-VN");
                if (elementDiscount) elementDiscount.innerHTML = discount.toLocaleString("vi-VN");
                if (elementCartTotal) elementCartTotal.innerHTML = totalPrice.toLocaleString("vi-VN");

                // Cập nhật hiển thị coupon
                const couponFormEl = document.querySelector("#coupon-form");
                const couponAppliedBox = document.querySelector("#coupon-applied-box");
                const appliedCodeEl = document.querySelector("#applied-coupon-code");
                const appliedDescEl = document.querySelector("#applied-coupon-desc");

                if (couponFormEl && couponAppliedBox) {
                    if (currentCoupon && discount > 0) {
                        couponFormEl.style.display = "none";
                        couponAppliedBox.style.display = "flex";
                        if (appliedCodeEl) appliedCodeEl.innerText = currentCoupon.code;
                        if (appliedDescEl) appliedDescEl.innerText = `(-${discount.toLocaleString("vi-VN")}đ)`;
                    } else {
                        couponFormEl.style.display = "flex";
                        couponAppliedBox.style.display = "none";
                    }
                }

                // Cập nhật trạng thái và sắp xếp mã giảm giá: Dùng được / Đang dùng lên trước, chưa đủ điều kiện ra sau
                const couponsGrid = document.querySelector(".inner-coupons-grid");
                if (couponsGrid) {
                    const listPills = Array.from(couponsGrid.querySelectorAll("[coupon-pill]"));
                    listPills.forEach(pill => {
                        const pillCode = pill.getAttribute("coupon-pill");
                        const minOrder = parseFloat(pill.getAttribute("data-min")) || 0;
                        const btn = pill.querySelector(".btn-use-coupon");
                        const isEligible = subTotalPrice >= minOrder && subTotalPrice > 0;
                        const isActive = currentCoupon && currentCoupon.code === pillCode;

                        if (!isEligible) {
                            pill.classList.add("unmet");
                            pill.classList.remove("active");
                            pill.dataset.priority = "3";
                            if (btn) {
                                btn.innerHTML = 'Áp dụng';
                                const diff = minOrder - subTotalPrice;
                                if (diff > 0) {
                                    btn.setAttribute("title", `Đơn hàng cần đạt tối thiểu ${minOrder.toLocaleString("vi-VN")}đ (còn thiếu ${diff.toLocaleString("vi-VN")}đ)`);
                                } else {
                                    btn.setAttribute("title", "Chọn ít nhất một tour trong giỏ hàng để sử dụng mã này");
                                }
                            }
                        } else {
                            pill.classList.remove("unmet");
                            if (isActive) {
                                pill.classList.add("active");
                                pill.dataset.priority = "1";
                                if (btn) {
                                    btn.innerHTML = '<i class="fa-solid fa-check"></i> Đang dùng';
                                    btn.removeAttribute("title");
                                }
                            } else {
                                pill.classList.remove("active");
                                pill.dataset.priority = "2";
                                if (btn) {
                                    btn.innerHTML = 'Áp dụng';
                                    btn.removeAttribute("title");
                                }
                            }
                        }
                    });

                    // Sắp xếp các thẻ: Đang dùng (1) -> Dùng được (2) -> Chưa đủ điều kiện (3)
                    listPills.sort((a, b) => {
                        const prioA = parseInt(a.dataset.priority || "2");
                        const prioB = parseInt(b.dataset.priority || "2");
                        if (prioA !== prioB) return prioA - prioB;
                        const minA = parseFloat(a.getAttribute("data-min")) || 0;
                        const minB = parseFloat(b.getAttribute("data-min")) || 0;
                        return minA - minB;
                    });

                    // Đưa các phần tử đã sắp xếp lại vào DOM
                    listPills.forEach(pill => couponsGrid.appendChild(pill));
                }

                // Cập nhật số lượng hành khách
                const listInputQuantity = document.querySelectorAll("[input-quantity]");
                listInputQuantity.forEach(input => {
                    input.addEventListener("change", () => {
                        const tourId = input.getAttribute("tour-id");
                        const name = input.getAttribute("name");
                        let quantity = parseInt(input.value);
                        if (isNaN(quantity) || quantity < 0) quantity = 0;

                        const cart = JSON.parse(localStorage.getItem("cart")) || [];
                        const itemUpdate = cart.find(item => item.tourId == tourId);
                        if (!itemUpdate) return;

                        // Tính toán tổng số lượng hành khách sau khi cập nhật
                        const qAdult = name === 'quantityAdult' ? quantity : (itemUpdate.quantityAdult || 0);
                        const qChildren = name === 'quantityChildren' ? quantity : (itemUpdate.quantityChildren || 0);
                        const qBaby = name === 'quantityBaby' ? quantity : (itemUpdate.quantityBaby || 0);
                        const totalPassengers = qAdult + qChildren + qBaby;

                        // Nếu số lượng về 0 (tổng khách = 0 hoặc người lớn = 0)
                        if (totalPassengers === 0 || (qAdult === 0 && (qChildren > 0 || qBaby > 0))) {
                            const tourTitle = itemUpdate.name ? `"${itemUpdate.name}"` : "tour này";
                            const confirmMsg = totalPassengers === 0 
                                ? `Số lượng hành khách bằng 0. Bạn có chắc chắn muốn xóa ${tourTitle} khỏi giỏ hàng không?`
                                : `Tour cần ít nhất 1 người lớn đi kèm. Bạn có muốn xóa ${tourTitle} khỏi giỏ hàng không?`;

                            if (typeof Swal !== "undefined") {
                                Swal.fire({
                                    title: "Xóa tour khỏi giỏ hàng?",
                                    text: confirmMsg,
                                    icon: "warning",
                                    showCancelButton: true,
                                    confirmButtonColor: "#EF4444",
                                    cancelButtonColor: "#94a3b8",
                                    confirmButtonText: "Đồng ý xóa",
                                    cancelButtonText: "Hủy bỏ"
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        const indexItem = cart.findIndex(t => t.tourId == tourId);
                                        if (indexItem !== -1) {
                                            cart.splice(indexItem, 1);
                                            localStorage.setItem("cart", JSON.stringify(cart));
                                        }
                                        drawCart();
                                        showAlert("Đã xóa tour khỏi giỏ hàng!", "info");
                                    } else {
                                        // Khôi phục lại số lượng (1 người lớn)
                                        input.value = name === 'quantityAdult' ? 1 : 0;
                                        itemUpdate[name] = parseInt(input.value);
                                        localStorage.setItem("cart", JSON.stringify(cart));
                                        drawCart();
                                    }
                                });
                            } else {
                                if (confirm(confirmMsg)) {
                                    const indexItem = cart.findIndex(t => t.tourId == tourId);
                                    if (indexItem !== -1) {
                                        cart.splice(indexItem, 1);
                                        localStorage.setItem("cart", JSON.stringify(cart));
                                    }
                                    drawCart();
                                } else {
                                    input.value = name === 'quantityAdult' ? 1 : 0;
                                    itemUpdate[name] = parseInt(input.value);
                                    localStorage.setItem("cart", JSON.stringify(cart));
                                    drawCart();
                                }
                            }
                            return;
                        }

                        itemUpdate[name] = quantity;
                        localStorage.setItem("cart", JSON.stringify(cart));
                        drawCart();
                    });
                });

                // Xóa tour khỏi giỏ hàng qua nút bấm
                const listButtonDelete = document.querySelectorAll("[button-delete]");
                listButtonDelete.forEach(button => {
                    button.addEventListener("click", () => {
                        const tourId = button.getAttribute("tour-id");
                        const cart = JSON.parse(localStorage.getItem("cart")) || [];
                        const item = cart.find(tour => tour.tourId == tourId);
                        const tourTitle = item && item.name ? `"${item.name}"` : "tour này";

                        if (typeof Swal !== "undefined") {
                            Swal.fire({
                                title: "Xóa tour khỏi giỏ hàng?",
                                text: `Bạn có chắc chắn muốn xóa ${tourTitle} khỏi giỏ hàng không?`,
                                icon: "warning",
                                showCancelButton: true,
                                confirmButtonColor: "#EF4444",
                                cancelButtonColor: "#94a3b8",
                                confirmButtonText: "Đồng ý xóa",
                                cancelButtonText: "Hủy bỏ"
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    const indexItem = cart.findIndex(tour => tour.tourId == tourId);
                                    if (indexItem !== -1) {
                                        cart.splice(indexItem, 1);
                                        localStorage.setItem("cart", JSON.stringify(cart));
                                    }
                                    drawCart();
                                    showAlert("Đã xóa tour khỏi giỏ hàng!", "info");
                                }
                            });
                        } else {
                            if (confirm(`Bạn có chắc chắn muốn xóa ${tourTitle} khỏi giỏ hàng không?`)) {
                                const indexItem = cart.findIndex(tour => tour.tourId == tourId);
                                if (indexItem !== -1) {
                                    cart.splice(indexItem, 1);
                                    localStorage.setItem("cart", JSON.stringify(cart));
                                }
                                drawCart();
                            }
                        }
                    });
                });

                // Sự kiện check
                const listInputCheck = document.querySelectorAll("[input-check]");
                listInputCheck.forEach(input => {
                    input.addEventListener("change", () => {
                        const tourId = input.getAttribute("tour-id");
                        const checked = input.checked;
                        const cart = JSON.parse(localStorage.getItem("cart"));
                        const itemUpdate = cart.find(tour => tour.tourId == tourId);
                        itemUpdate.checked = checked;
                        localStorage.setItem("cart", JSON.stringify(cart));
                        drawCart();
                    })
                })
            }
        })
}

const pageCart = document.querySelector("[page-cart]");
if (pageCart) {
    drawCart();

    // Hàm áp dụng mã giảm giá chuyên dụng, đáng tin cậy
    const applyCoupon = (code) => {
        if (!code) {
            if (typeof Swal !== "undefined") {
                Swal.fire({
                    icon: "warning",
                    title: "Chưa nhập mã",
                    text: "Vui lòng nhập mã giảm giá!",
                    confirmButtonColor: "#2563EB"
                });
            } else {
                showAlert("Vui lòng nhập mã giảm giá!", "warning");
            }
            return;
        }

        const cart = JSON.parse(localStorage.getItem("cart")) || [];
        let subTotal = 0;
        cart.forEach(item => {
            if (item.checked) {
                subTotal += (item.priceNewAdult || 0) * (item.quantityAdult || 0)
                          + (item.priceNewChildren || 0) * (item.quantityChildren || 0)
                          + (item.priceNewBaby || 0) * (item.quantityBaby || 0);
            }
        });

        if (subTotal <= 0) {
            if (typeof Swal !== "undefined") {
                Swal.fire({
                    icon: "info",
                    title: "Chưa chọn tour",
                    text: "Vui lòng chọn ít nhất một tour trong giỏ hàng để áp dụng mã giảm giá!",
                    confirmButtonColor: "#2563EB"
                });
            } else {
                showAlert("Vui lòng chọn ít nhất một tour trong giỏ hàng để áp dụng mã giảm giá!", "warning");
            }
            return;
        }

        // Kiểm tra nhanh điều kiện đơn hàng tối thiểu nếu mã có sẵn trong danh sách
        const targetPill = document.querySelector(`[coupon-pill="${code}"]`);
        if (targetPill) {
            const minOrder = parseFloat(targetPill.getAttribute("data-min")) || 0;
            if (minOrder > 0 && subTotal < minOrder) {
                const diff = minOrder - subTotal;
                if (typeof Swal !== "undefined") {
                    Swal.fire({
                        icon: "info",
                        title: "Chưa đủ điều kiện",
                        html: `Mã giảm giá <b>${code}</b> áp dụng cho đơn hàng từ <b>${minOrder.toLocaleString("vi-VN")}đ</b> trở lên.<br>Tạm tính hiện tại của bạn: <b>${subTotal.toLocaleString("vi-VN")}đ</b> (còn thiếu <b>${diff.toLocaleString("vi-VN")}đ</b>).`,
                        confirmButtonColor: "#2563EB",
                        confirmButtonText: "Đã hiểu"
                    });
                } else {
                    showAlert(`Đơn hàng cần đạt tối thiểu ${minOrder.toLocaleString("vi-VN")}đ để dùng mã này!`, "warning");
                }
                return;
            }
        }

        const btnApply = document.querySelector("#btn-apply-coupon");
        const originalText = btnApply ? btnApply.innerText : "";
        if (btnApply) {
            btnApply.disabled = true;
            btnApply.innerText = "Đang kiểm tra...";
        }

        const targetBtn = document.querySelector(`[button-quick-apply="${code}"]`);
        const originalTargetHtml = targetBtn ? targetBtn.innerHTML : "";
        if (targetBtn) {
            targetBtn.disabled = true;
            targetBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i>';
        }

        fetch(`${CONTEXT_PATH}/cart/apply-coupon`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                couponCode: code,
                subTotal: subTotal
            })
        })
        .then(res => res.json())
        .then(data => {
            if (btnApply) {
                btnApply.disabled = false;
                btnApply.innerText = originalText;
            }
            if (targetBtn) {
                targetBtn.disabled = false;
                targetBtn.innerHTML = originalTargetHtml;
            }

            if (data.code === "success") {
                currentCoupon = {
                    code: data.couponCode,
                    title: data.couponTitle,
                    discount: data.discount,
                    discountType: data.discountType,
                    discountValue: data.discountValue,
                    minOrderValue: data.minOrderValue || 0,
                    maxDiscountValue: data.maxDiscountValue || 0
                };
                try {
                    sessionStorage.setItem("appliedCoupon", JSON.stringify(currentCoupon));
                } catch(e) {}

                if (typeof Swal !== "undefined") {
                    Swal.fire({
                        icon: "success",
                        title: "Áp dụng thành công!",
                        text: `Mã ${data.couponCode} đã giảm ${data.discount.toLocaleString("vi-VN")}đ cho đơn hàng của bạn.`,
                        confirmButtonColor: "#2563EB",
                        confirmButtonText: "Tuyệt vời"
                    });
                } else {
                    showAlert("Áp dụng mã giảm giá thành công!", "success");
                }
                drawCart();
            } else {
                if (typeof Swal !== "undefined") {
                    Swal.fire({
                        icon: "error",
                        title: "Không thể áp dụng",
                        text: data.message || "Mã giảm giá không hợp lệ!",
                        confirmButtonColor: "#EF4444",
                        confirmButtonText: "Đã hiểu"
                    });
                } else {
                    showAlert(data.message || "Mã giảm giá không hợp lệ!", "error");
                }
            }
        })
        .catch(err => {
            if (btnApply) {
                btnApply.disabled = false;
                btnApply.innerText = originalText;
            }
            if (targetBtn) {
                targetBtn.disabled = false;
                targetBtn.innerHTML = originalTargetHtml;
            }
            console.error(err);
            showAlert("Có lỗi khi kiểm tra mã giảm giá!", "error");
        });
    };

    // Xử lý gửi mã giảm giá từ form
    const couponForm = document.querySelector("#coupon-form");
    if (couponForm) {
        couponForm.addEventListener("submit", (e) => {
            e.preventDefault();
            const input = document.querySelector("#coupon-input");
            const code = input ? input.value.trim().toUpperCase() : "";
            applyCoupon(code);
        });
    }

    // Nút gỡ mã giảm giá
    const btnRemoveCoupon = document.querySelector("#btn-remove-coupon");
    if (btnRemoveCoupon) {
        btnRemoveCoupon.addEventListener("click", () => {
            currentCoupon = null;
            try {
                sessionStorage.removeItem("appliedCoupon");
            } catch(e) {}
            const couponInput = document.querySelector("#coupon-input");
            if (couponInput) couponInput.value = "";
            drawCart();
            showAlert("Đã gỡ mã giảm giá!", "info");
        });
    }

    // Nút áp dụng nhanh mã giảm giá từ danh sách có sẵn (sử dụng event delegation để bắt click 100% chính xác)
    document.addEventListener("click", (e) => {
        const quickBtn = e.target.closest("[button-quick-apply]");
        if (quickBtn) {
            e.preventDefault();
            const code = quickBtn.getAttribute("button-quick-apply");
            const couponInput = document.querySelector("#coupon-input");
            if (couponInput) couponInput.value = code;
            applyCoupon(code);
        }
    });

    // Xem chi tiết mã giảm giá khi bấm vào thẻ trong giỏ hàng
    document.addEventListener("click", (e) => {
        if (e.target.closest("[button-quick-apply]")) return;

        const pill = e.target.closest(".inner-coupon-pill");
        if (!pill) return;

        const code = pill.getAttribute("data-code") || pill.getAttribute("coupon-pill");
        const title = pill.getAttribute("data-title") || "Ưu Đãi Đặc Biệt";
        const type = pill.getAttribute("data-type") || "percentage";
        const valueFormatted = pill.getAttribute("data-value-formatted") || "";
        const minOrder = parseFloat(pill.getAttribute("data-min")) || 0;
        const minFormatted = pill.getAttribute("data-min-formatted") || "0đ";
        const maxDiscount = parseFloat(pill.getAttribute("data-max")) || 0;
        const maxFormatted = pill.getAttribute("data-max-formatted") || "";
        const endDate = pill.getAttribute("data-end") || "";

        // Lấy tạm tính giỏ hàng hiện tại
        const cart = JSON.parse(localStorage.getItem("cart")) || [];
        let subTotal = 0;
        cart.forEach(item => {
            if (item.checked) {
                subTotal += (item.priceNewAdult || 0) * (item.quantityAdult || 0)
                          + (item.priceNewChildren || 0) * (item.quantityChildren || 0)
                          + (item.priceNewBaby || 0) * (item.quantityBaby || 0);
            }
        });

        const isEligible = subTotal >= minOrder && subTotal > 0;
        const isApplied = currentCoupon && currentCoupon.code === code;

        const modalHtml = `
            <div class="modal-coupon-card">
                <div class="modal-coupon-header ${type === 'fixed' ? 'fixed' : 'percentage'}">
                    <div class="badge-icon"><i class="fa-solid fa-ticket"></i></div>
                    <div class="header-info">
                        <div class="header-amount">${valueFormatted}</div>
                        <div class="header-code-pill">MÃ: ${code}</div>
                    </div>
                </div>
                <div class="modal-coupon-body">
                    <h3 class="modal-coupon-title">${title}</h3>
                    
                    <div class="modal-coupon-details">
                        <div class="modal-detail-row">
                            <span class="label"><i class="fa-solid fa-tag"></i> Loại ưu đãi:</span>
                            <span class="val">${type === 'percentage' ? 'Giảm theo phần trăm' : 'Khấu trừ tiền mặt trực tiếp'}</span>
                        </div>
                        <div class="modal-detail-row">
                            <span class="label"><i class="fa-solid fa-cart-shopping"></i> Đơn tối thiểu:</span>
                            <span class="val">${minOrder > 0 ? minFormatted : 'Mọi giá trị đơn hàng'}</span>
                        </div>
                        ${maxDiscount > 0 ? `
                        <div class="modal-detail-row">
                            <span class="label"><i class="fa-solid fa-shield-halved"></i> Giảm tối đa:</span>
                            <span class="val">${maxFormatted}</span>
                        </div>` : ''}
                        <div class="modal-detail-row">
                            <span class="label"><i class="fa-regular fa-clock"></i> Hạn sử dụng:</span>
                            <span class="val">${endDate ? endDate : 'Vô thời hạn'}</span>
                        </div>
                    </div>

                    <div class="modal-status-pill ${isEligible ? 'eligible' : 'unmet'}">
                        <i class="fa-solid ${isEligible ? 'fa-circle-check' : 'fa-triangle-exclamation'}"></i>
                        <div class="status-desc">
                            ${isEligible 
                                ? `<b>Đã đủ điều kiện áp dụng!</b><br>Tạm tính đơn hàng của bạn: <b>${subTotal.toLocaleString('vi-VN')}đ</b>.`
                                : `<b>Chưa đủ điều kiện sử dụng</b><br>Tạm tính hiện tại: <b>${subTotal.toLocaleString('vi-VN')}đ</b> (cần thêm <b>${(minOrder - subTotal).toLocaleString('vi-VN')}đ</b> để áp dụng).`
                            }
                        </div>
                    </div>

                    <div class="modal-actions">
                        ${isApplied ? `
                            <button type="button" class="modal-btn-apply applied" disabled>
                                <i class="fa-solid fa-check"></i> Đang sử dụng mã này
                            </button>
                        ` : `
                            <button type="button" class="modal-btn-apply" id="btn-modal-apply-trigger">
                                <i class="fa-solid fa-bolt"></i> Áp dụng mã ngay
                            </button>
                        `}
                    </div>
                </div>
            </div>
        `;

        if (typeof Swal !== "undefined") {
            Swal.fire({
                html: modalHtml,
                showConfirmButton: false,
                showCloseButton: true,
                customClass: {
                    popup: "swal2-coupon-detail-popup"
                },
                didOpen: () => {
                    const btnApplyTrigger = document.querySelector("#btn-modal-apply-trigger");
                    if (btnApplyTrigger) {
                        btnApplyTrigger.addEventListener("click", () => {
                            Swal.close();
                            applyCoupon(code);
                        });
                    }
                }
            });
        }
    });
}

// Xử lý sao chép mã giảm giá (Dùng cho cả trang chủ và các nơi khác - Bắt sự kiện toàn trang)
const copyToClipboard = (text) => {
    if (navigator.clipboard && window.isSecureContext) {
        return navigator.clipboard.writeText(text).catch(() => {
            return execCommandCopy(text);
        });
    }
    return execCommandCopy(text);
};

const execCommandCopy = (text) => {
    return new Promise((resolve) => {
        const textArea = document.createElement("textarea");
        textArea.value = text;
        textArea.style.position = "fixed";
        textArea.style.left = "-9999px";
        textArea.style.top = "-9999px";
        textArea.setAttribute("readonly", "");
        document.body.appendChild(textArea);
        textArea.focus();
        textArea.select();
        textArea.setSelectionRange(0, 99999);
        try {
            document.execCommand("copy");
        } catch (err) {
            console.error("execCommand copy error:", err);
        }
        document.body.removeChild(textArea);
        resolve();
    });
};

const notifyCopiedSuccess = (btn, code) => {
    if (btn) {
        const originalHtml = btn.innerHTML;
        btn.classList.add("copied");
        btn.innerHTML = '<i class="fa-solid fa-check"></i> <span>Đã sao chép!</span>';
        setTimeout(() => {
            btn.classList.remove("copied");
            btn.innerHTML = originalHtml;
        }, 2500);
    }

    // Hiển thị Toast thông báo hiện đại, tinh gọn ở góc trên bên phải (không chặn màn hình)
    if (typeof Swal !== "undefined") {
        const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 2500,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener("mouseenter", Swal.stopTimer);
                toast.addEventListener("mouseleave", Swal.resumeTimer);
            }
        });

        Toast.fire({
            icon: "success",
            title: `Đã sao chép mã: <span style="color: #2563eb; font-family: monospace; font-size: 1.1em; font-weight: 800; margin-left: 4px;">${code}</span>`
        });
    } else {
        showAlert(`Đã sao chép mã ${code}!`, "success", 2500);
    }
};

document.addEventListener("click", (e) => {
    const copyTarget = e.target.closest("[button-copy-coupon]");
    if (copyTarget) {
        e.preventDefault();
        e.stopPropagation();
        const code = copyTarget.getAttribute("button-copy-coupon");
        if (!code) return;

        // Tìm nút button tương ứng trong cùng thẻ card để đổi hiệu ứng
        const card = copyTarget.closest(".inner-voucher-card");
        const btn = card ? card.querySelector(".btn-copy-coupon") : copyTarget;

        copyToClipboard(code).then(() => {
            notifyCopiedSuccess(btn, code);
        }).catch(() => {
            notifyCopiedSuccess(btn, code);
        });
    }
});

// Contact Page FAQ Accordion
const faqQuestions = document.querySelectorAll(".section-13 .inner-faq-question");
if (faqQuestions.length > 0) {
    faqQuestions.forEach(btn => {
        btn.addEventListener("click", () => {
            const item = btn.closest(".inner-faq-item");
            if (item) {
                const wasActive = item.classList.contains("active");
                document.querySelectorAll(".section-13 .inner-faq-item").forEach(other => {
                    other.classList.remove("active");
                });
                if (!wasActive) {
                    item.classList.add("active");
                }
            }
        });
    });
}

// Contact Page Form Submit Handler
const contactPageForm = document.querySelector("#contact-page-form");
if (contactPageForm) {
    contactPageForm.addEventListener("submit", (e) => {
        e.preventDefault();

        const submitBtn = contactPageForm.querySelector("#contact-submit-button");
        const originalBtnHtml = submitBtn ? submitBtn.innerHTML : "";
        if (submitBtn) {
            submitBtn.disabled = true;
            submitBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Đang gửi tin nhắn...';
        }

        const formData = {
            fullName: (contactPageForm.fullName ? contactPageForm.fullName.value : "").trim(),
            phone: (contactPageForm.phone ? contactPageForm.phone.value : "").trim(),
            email: (contactPageForm.email ? contactPageForm.email.value : "").trim(),
            subject: (contactPageForm.subject ? contactPageForm.subject.value : "Tư vấn tour").trim(),
            message: (contactPageForm.message ? contactPageForm.message.value : "").trim()
        };

        fetch(`${window.location.origin}${CONTEXT_PATH}/contact`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(formData)
        })
        .then(res => res.json())
        .then(data => {
            if (submitBtn) {
                submitBtn.disabled = false;
                submitBtn.innerHTML = originalBtnHtml;
            }

            if (data.code === "success") {
                contactPageForm.reset();
                if (typeof Swal !== 'undefined') {
                    Swal.fire({
                        icon: 'success',
                        title: 'Gửi tin nhắn thành công!',
                        text: data.message || 'Cảm ơn bạn! Đội ngũ tư vấn sẽ liên hệ lại trong thời gian sớm nhất.',
                        confirmButtonText: 'Tuyệt vời',
                        confirmButtonColor: '#2563eb',
                        customClass: { popup: 'client-swal-modal' }
                    });
                } else {
                    alert(data.message);
                }
            } else {
                if (typeof Swal !== 'undefined') {
                    Swal.fire({
                        icon: 'error',
                        title: 'Chưa thể gửi tin nhắn',
                        text: data.message || 'Vui lòng kiểm tra lại thông tin và thử lại sau.',
                        confirmButtonText: 'Đã hiểu',
                        confirmButtonColor: '#ef4444',
                        customClass: { popup: 'client-swal-modal' }
                    });
                } else {
                    alert(data.message);
                }
            }
        })
        .catch(err => {
            if (submitBtn) {
                submitBtn.disabled = false;
                submitBtn.innerHTML = originalBtnHtml;
            }
            if (typeof Swal !== 'undefined') {
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi kết nối',
                    text: 'Không thể kết nối đến máy chủ. Vui lòng thử lại sau!',
                    confirmButtonText: 'Đóng',
                    confirmButtonColor: '#ef4444',
                    customClass: { popup: 'client-swal-modal' }
                });
            } else {
                alert('Có lỗi xảy ra, vui lòng thử lại sau!');
            }
        });
    });
}

// News Sidebar Newsletter Subscription
const newsSidebarBtn = document.querySelector("#news-sidebar-btn");
const newsSidebarEmail = document.querySelector("#news-sidebar-email");
if (newsSidebarBtn && newsSidebarEmail) {
    newsSidebarBtn.addEventListener("click", () => {
        const email = newsSidebarEmail.value.trim();
        if (!email) {
            if (typeof Swal !== 'undefined') {
                Swal.fire({
                    icon: 'warning',
                    title: 'Chưa nhập email',
                    text: 'Vui lòng nhập địa chỉ email của bạn!',
                    confirmButtonText: 'Đã hiểu',
                    confirmButtonColor: '#f59e0b',
                    customClass: { popup: 'client-swal-modal' }
                });
            } else {
                alert('Vui lòng nhập địa chỉ email của bạn!');
            }
            return;
        }

        newsSidebarBtn.disabled = true;
        const origText = newsSidebarBtn.innerText;
        newsSidebarBtn.innerText = 'Đang gửi...';

        fetch(`${window.location.origin}${CONTEXT_PATH}/contact/create`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email: email })
        })
        .then(res => res.json())
        .then(data => {
            newsSidebarBtn.disabled = false;
            newsSidebarBtn.innerText = origText;

            if (data.code === "success") {
                newsSidebarEmail.value = '';
                if (typeof Swal !== 'undefined') {
                    Swal.fire({
                        icon: 'success',
                        title: 'Đăng ký thành công!',
                        text: data.message || 'Cảm ơn bạn! Chúng tôi sẽ gửi những cẩm nang du lịch hữu ích nhất.',
                        confirmButtonText: 'Tuyệt vời',
                        confirmButtonColor: '#2563eb',
                        customClass: { popup: 'client-swal-modal' }
                    });
                } else {
                    alert(data.message);
                }
            } else if (data.code === "exist") {
                if (typeof Swal !== 'undefined') {
                    Swal.fire({
                        icon: 'info',
                        title: 'Email đã tồn tại',
                        text: data.message || 'Email này đã đăng ký nhận tin trước đó rồi!',
                        confirmButtonText: 'Đã hiểu',
                        confirmButtonColor: '#2563eb',
                        customClass: { popup: 'client-swal-modal' }
                    });
                } else {
                    alert(data.message);
                }
            } else {
                if (typeof Swal !== 'undefined') {
                    Swal.fire({
                        icon: 'error',
                        title: 'Chưa thể đăng ký',
                        text: data.message || 'Có lỗi xảy ra, vui lòng thử lại sau!',
                        confirmButtonText: 'Đóng',
                        confirmButtonColor: '#ef4444',
                        customClass: { popup: 'client-swal-modal' }
                    });
                } else {
                    alert(data.message);
                }
            }
        })
        .catch(err => {
            newsSidebarBtn.disabled = false;
            newsSidebarBtn.innerText = origText;
            if (typeof Swal !== 'undefined') {
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi kết nối',
                    text: 'Không thể kết nối đến máy chủ. Vui lòng thử lại sau!',
                    confirmButtonText: 'Đóng',
                    confirmButtonColor: '#ef4444',
                    customClass: { popup: 'client-swal-modal' }
                });
            } else {
                alert('Có lỗi xảy ra, vui lòng thử lại sau!');
            }
        });
    });
}

// Back To Top Floating Action Button
const btnBackToTop = document.getElementById("btn-back-to-top");
if (btnBackToTop) {
    window.addEventListener("scroll", () => {
        if (window.scrollY > 280) {
            btnBackToTop.style.display = "flex";
        } else {
            btnBackToTop.style.display = "none";
        }
    }, { passive: true });

    btnBackToTop.addEventListener("click", () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    });
}
