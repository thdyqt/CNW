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
        input.value =  `NL: ${listNumber[0]}, TE: ${listNumber[1]}, EB: ${listNumber[2]}`;
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