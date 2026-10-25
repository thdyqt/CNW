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
    const input = boxLocationSection1.querySelector(".inner-input")
    input.addEventListener("focus", () => {
        boxLocationSection1.classList.add("active");
    });

    input.addEventListener("blur", () => {
        boxLocationSection1.classList.remove("active");
    })

    // Sự kiện click vào từng item
    const listItem = boxLocationSection1.querySelectorAll(".inner-suggest-list .inner-item");
    listItem.forEach(item => {
        item.addEventListener("mousedown", () => {
            const title = item.querySelector(".inner-item-title").innerHTML;
            input.value = title.trim();
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
            const email = event.target.email.value;

            const dataFinal = {
                email: email,
            };

            fetch(`/contact/create`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dataFinal)
            })
                .then(res => res.json())
                .then(data => {
                    if (data.code == "error") {
                        alert(data.message);
                    }
                    else if (data.code == "success") {
                        window.location.reload();
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
        .onSuccess(event => {
            const fullName = event.target.fullName.value;
            const phone = event.target.phone.value;
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
                    note: note,
                    paymentMethod: method,
                    items: cart
                };

            fetch(`/order/create`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dataFinal)
            })
                .then(res => res.json())
                .then(data => {
                    if (data.code == "error") {
                        alert(data.message);
                    }
                    else if (data.code == "success") {
                        // Cập nhật lại giỏ hàng
                        let cart = JSON.parse(localStorage.getItem("cart"));
                        cart = cart.filter(item => item.checked == false);
                        localStorage.setItem("cart", JSON.stringify(cart));

                        // Chuyển sang trang đặt hàng thành công
                        window.location.href = `/order/success?orderCode=${data.orderCode}&phone=${phone}`;
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
const alertTime = document.querySelector("[alert-time]");
if (alertTime) {
    let time = alertTime.getAttribute("alert-time");
    time = time ? parseInt(time) : 4000;
    setTimeout(() => {
        alertTime.remove();
    }, time)
}

// Box Filter
const boxFilter = document.querySelector(".box-filter");
if (boxFilter) {
    const url = new URL(`${window.location.origin}/search`);
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
    const url = new URL(`${window.location.origin}/search`);

    formSearch.addEventListener("submit", (event) => {
        event.preventDefault();

        const locationTo = formSearch.locationTo.value;
        if (locationTo) url.searchParams.set("locationTo", locationTo);
        else url.searchParams.delete("locationTo");

        const stockAdult = parseInt(formSearch.querySelector("[stock-adult]").innerHTML);
        if (stockAdult > 0) url.searchParams.set("stockAdult", stockAdult);
        else url.searchParams.delete("stockAdult");

        const stockChildren = parseInt(formSearch.querySelector("[stock-children]").innerHTML);
        if (stockChildren > 0) url.searchParams.set("stockChildren", stockChildren);
        else url.searchParams.delete("stockChildren");

        const stockBaby = parseInt(formSearch.querySelector("[stock-baby]").innerHTML);
        if (stockBaby > 0) url.searchParams.set("stockBaby", stockBaby);
        else url.searchParams.delete("stockBaby");

        const departureDate = formSearch.departureDate.value;
        if (departureDate) url.searchParams.set("departureDate", departureDate);
        else url.searchParams.delete("departureDate");

        window.location.href = url.href;
    })
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
    buttonAddToCart.addEventListener("click", () => {
        const tourId = buttonAddToCart.getAttribute("tour-id");
        const quantityAdult = parseInt(inputStockAdult.value);
        const quantityChildren = parseInt(inputStockChildren.value);
        const quantityBaby = parseInt(inputStockBaby.value);
        const locationFrom = boxTourDetail.querySelector("[location-from]").value;

        if (quantityAdult > 0 || quantityChildren > 0 || quantityBaby > 0) {
            const cartItem = {
                tourId: tourId,
                quantityAdult: quantityAdult,
                quantityChildren: quantityChildren,
                quantityBaby: quantityBaby,
                locationFrom: locationFrom,
                checked: true
            };

            const cart = JSON.parse(localStorage.getItem("cart"));
            const indexItemExist = cart.findIndex(item => item.tourId == tourId);
            if (indexItemExist != -1) {
                cart[indexItemExist] = cartItem;
            } else {
                cart.push(cartItem);
            }
            localStorage.setItem("cart", JSON.stringify(cart));
            window.location.href = "/cart";
        }
    })
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
const drawCart = () => {
    const cart = localStorage.getItem("cart");

    fetch('/cart/detail', {
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
                                <a href="/tour/detail/${item.slug}">
                                    <img alt="${item.name}" src="${item.avatar}">
                                </a>
                            </div>
                            <div class="inner-content" bis_skin_checked="1">
                                <div class="inner-title" bis_skin_checked="1">
                                    <a href="/tour/detail/${item.slug}">${item.name}</a>
                                </div>
                                <div class="inner-meta" bis_skin_checked="1">
                                    <div class="inner-meta-item" bis_skin_checked="1">Mã Tour: <b>123456789</b></div>
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
                    
                const discount = 0
                const totalPrice = subTotalPrice - discount;

                const elementCartSubTotal = document.querySelector("[cart-sub-total]");
                const elementDiscount = document.querySelector("[cart-discount]");
                const elementCartTotal = document.querySelector("[cart-total]");

                elementCartSubTotal.innerHTML = subTotalPrice.toLocaleString("vi-VN")
                elementDiscount.innerHTML = discount.toLocaleString("vi-VN")
                elementCartTotal.innerHTML = totalPrice.toLocaleString("vi-VN")

                // Cập nhật số lượng
                const listInputQuantity = document.querySelectorAll("[input-quantity]");
                listInputQuantity.forEach(input => {
                    input.addEventListener("change", () => {
                        const tourId = input.getAttribute("tour-id");
                        const name = input.getAttribute("name");
                        const quantity = parseInt(input.value);
                        const cart = JSON.parse(localStorage.getItem("cart"));
                        const itemUpdate = cart.find(item => item.tourId == tourId);
                        itemUpdate[name] = quantity;
                        localStorage.setItem("cart", JSON.stringify(cart));
                        drawCart();
                    })
                })

                // Xóa tour khỏi giỏ hàng
                const listButtonDelete = document.querySelectorAll("[button-delete]");
                listButtonDelete.forEach(button => {
                    button.addEventListener("click", () => {
                        const tourId = button.getAttribute("tour-id");
                        const cart = JSON.parse(localStorage.getItem("cart"));
                        const indexItem = cart.findIndex(tour => tour.tourId == tourId);
                        cart.splice(indexItem, 1);
                        localStorage.setItem("cart", JSON.stringify(cart));
                        drawCart();
                    })
                })

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
}