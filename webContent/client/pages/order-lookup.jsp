<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<jsp:include page="../mixins/box-breadcrumb.jsp" />

<div class="section-order-lookup">
    <div class="container">
        <!-- Form Tra cứu -->
        <div class="box-order-lookup-card">
            <div class="inner-header">
                <div class="inner-icon-main">
                    <i class="fa-solid fa-file-invoice-dollar"></i>
                </div>
                <h2 class="inner-title">Tra Cứu Thông Tin Đơn Hàng</h2>
                <p class="inner-desc">
                    Quý khách vui lòng nhập <strong>Mã đơn hàng</strong> và <strong>Số điện thoại</strong> đã đăng ký khi đặt tour để kiểm tra trạng thái duyệt đơn, tiến độ thanh toán và lịch trình chi tiết chuyến đi.
                </p>
            </div>

            <form action="${pageContext.request.contextPath}/order/lookup" method="GET" class="inner-lookup-form">
                <div class="inner-form-row">
                    <div class="inner-form-group">
                        <label for="orderCode">
                            <i class="fa-solid fa-receipt"></i> Mã đơn hàng <span class="text-danger" style="color: #ef4444;">*</span>
                        </label>
                        <input type="text" id="orderCode" name="orderCode" value="${searchedCode}" placeholder="Ví dụ: OD1039460021" required autofocus />
                    </div>
                    <div class="inner-form-group">
                        <label for="phone">
                            <i class="fa-solid fa-phone"></i> Số điện thoại đặt tour <span class="text-danger" style="color: #ef4444;">*</span>
                        </label>
                        <input type="tel" id="phone" name="phone" value="${searchedPhone}" placeholder="Ví dụ: 0905383132" required />
                    </div>
                </div>
                <div class="inner-form-action">
                    <button type="submit" class="btn-lookup-submit">
                        <i class="fa-solid fa-magnifying-glass"></i> Tra Cứu Đơn Hàng
                    </button>
                </div>
                <div class="inner-form-hint">
                    <i class="fa-solid fa-circle-info"></i> Mã đơn hàng đã được hệ thống gửi tự động vào hòm thư Email của quý khách hoặc hiển thị ngay sau khi đặt tour thành công.
                </div>
            </form>
        </div>

        <!-- Trường hợp tìm kiếm nhưng không thấy đơn -->
        <c:if test="${searched && empty orderDetail}">
            <div class="lookup-alert-error">
                <i class="fa-solid fa-triangle-exclamation"></i>
                <div>
                    <div class="alert-title">Không tìm thấy thông tin đơn hàng!</div>
                    <div class="alert-desc">
                        Hệ thống không tìm thấy đơn hàng nào khớp với mã: <strong>${searchedCode}</strong> và số điện thoại: <strong>${searchedPhone}</strong>.<br>
                        Quý khách vui lòng kiểm tra lại tính chính xác của Mã đơn hàng (bắt đầu bằng <em>OD...</em>) hoặc liên hệ ngay hotline 
                        <a href="tel:${settingWebsiteInfo.cleanPhone}" style="color: #dc2626; font-weight: 700; text-decoration: underline;">${not empty settingWebsiteInfo.phone ? settingWebsiteInfo.phone : '0905 383 132'}</a> 
                        để được chuyên viên chăm sóc khách hàng hỗ trợ kiểm tra trực tiếp.
                    </div>
                </div>
            </div>
        </c:if>

        <!-- Trường hợp tìm thấy đơn hàng -->
        <c:if test="${not empty orderDetail}">
            <!-- Thông tin tổng quan đơn hàng -->
            <div class="section-11" style="margin-top: 10px;">
                <div class="inner-wrap">
                    <div class="inner-head" style="border-bottom: 2px solid #f1f5f9; padding-bottom: 15px; margin-bottom: 20px;">
                        <div class="inner-title" style="display: flex; align-items: center; gap: 10px; font-size: 22px;">
                            <i class="fa-solid fa-circle-check" style="color: #10b981;"></i>
                            <span>Chi Tiết Đơn Hàng: <strong style="color: #2563eb;">#${orderDetail.code}</strong></span>
                        </div>
                        <div style="font-size: 13px; color: #64748b;">
                            Ngày đặt: <b>${orderDetail.createdAtFormatted}</b>
                        </div>
                    </div>

                    <!-- Hàng thẻ trạng thái trực quan -->
                    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 15px; margin-bottom: 25px;">
                        <!-- Trạng thái đơn hàng -->
                        <div style="background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 12px; padding: 14px 18px;">
                            <div style="font-size: 13px; color: #64748b; margin-bottom: 6px;">Trạng thái đơn:</div>
                            <div>
                                <c:choose>
                                    <c:when test="${orderDetail.status == 'initial'}">
                                        <span class="status-badge badge-warning"><i class="fa-solid fa-clock"></i> Chờ duyệt</span>
                                    </c:when>
                                    <c:when test="${orderDetail.status == 'processing'}">
                                        <span class="status-badge badge-info"><i class="fa-solid fa-spinner fa-spin"></i> Đang xử lý</span>
                                    </c:when>
                                    <c:when test="${orderDetail.status == 'success' || orderDetail.status == 'done'}">
                                        <span class="status-badge badge-success"><i class="fa-solid fa-circle-check"></i> Đã duyệt thành công</span>
                                    </c:when>
                                    <c:when test="${orderDetail.status == 'cancel'}">
                                        <span class="status-badge badge-danger"><i class="fa-solid fa-circle-xmark"></i> Đã hủy</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-badge badge-info">${orderDetail.statusName}</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <!-- Trạng thái thanh toán -->
                        <div style="background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 12px; padding: 14px 18px;">
                            <div style="font-size: 13px; color: #64748b; margin-bottom: 6px;">Trạng thái thanh toán:</div>
                            <div>
                                <c:choose>
                                    <c:when test="${orderDetail.paymentStatus == 'paid'}">
                                        <span class="status-badge badge-success"><i class="fa-solid fa-check-double"></i> Đã thanh toán</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-badge badge-warning"><i class="fa-solid fa-hourglass-half"></i> Chưa thanh toán</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <!-- Phương thức thanh toán -->
                        <div style="background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 12px; padding: 14px 18px;">
                            <div style="font-size: 13px; color: #64748b; margin-bottom: 6px;">Phương thức thanh toán:</div>
                            <div style="font-weight: 700; color: #1e293b; font-size: 14px;">
                                <c:choose>
                                    <c:when test="${orderDetail.paymentMethod == 'vnpay'}">
                                        <span style="color: #0066cc;"><i class="fa-solid fa-credit-card"></i> VNPay trực tuyến</span>
                                    </c:when>
                                    <c:when test="${orderDetail.paymentMethod == 'bank'}">
                                        <span style="color: #0284c7;"><i class="fa-solid fa-building-columns"></i> Chuyển khoản ngân hàng</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #16a34a;"><i class="fa-solid fa-money-bill-wave"></i> Tiền mặt khi đi tour</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <!-- Thông tin khách hàng -->
                    <div style="background: #ffffff; border: 1px solid #e2e8f0; border-radius: 12px; padding: 18px 22px; margin-bottom: 25px;">
                        <div style="font-size: 16px; font-weight: 700; color: #1e40af; margin-bottom: 12px; display: flex; align-items: center; gap: 8px;">
                            <i class="fa-solid fa-user-check"></i> Thông tin khách hàng đăng ký
                        </div>
                        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(260px, 1fr)); gap: 10px; font-size: 14px;">
                            <div>Họ và tên: <strong style="color: #0f172a;">${orderDetail.fullName}</strong></div>
                            <div>Số điện thoại: <strong style="color: #0f172a;">${orderDetail.phone}</strong></div>
                            <c:if test="${not empty orderDetail.email}">
                                <div>Email: <strong style="color: #2563eb;">${orderDetail.email}</strong></div>
                            </c:if>
                            <c:if test="${not empty orderDetail.couponCode}">
                                <div>Mã giảm giá áp dụng: <span style="background: #eff6ff; color: #2563eb; font-weight: 700; padding: 2px 8px; border-radius: 6px; border: 1px dashed #3b82f6;">${orderDetail.couponCode}</span></div>
                            </c:if>
                            <c:if test="${not empty orderDetail.note}">
                                <div style="grid-column: 1 / -1;">Ghi chú yêu cầu: <span style="color: #475569; font-style: italic;">${orderDetail.note}</span></div>
                            </c:if>
                        </div>
                    </div>

                    <!-- Hướng dẫn chuyển khoản nếu thanh toán chuyển khoản và chưa thanh toán -->
                    <c:if test="${orderDetail.paymentMethod == 'bank' && orderDetail.paymentStatus != 'paid'}">
                        <div style="background: #eff6ff; border: 1.5px solid #60a5fa; border-radius: 12px; padding: 18px 22px; margin-bottom: 25px;">
                            <div style="font-size: 15px; font-weight: 700; color: #1d4ed8; margin-bottom: 8px; display: flex; align-items: center; gap: 8px;">
                                <i class="fa-solid fa-building-columns"></i> HƯỚNG DẪN CHUYỂN KHOẢN THANH TOÁN
                            </div>
                            <div style="font-size: 14px; color: #334155; line-height: 1.7;">
                                • Ngân hàng: <strong>MBBank (Ngân Hàng Quân Đội)</strong><br>
                                • Số tài khoản: <strong style="font-family: monospace; font-size: 16px; color: #1d4ed8;">000002907</strong><br>
                                • Chủ tài khoản: <strong>PHAN THANH DUY</strong><br>
                                • Số tiền cần thanh toán: <strong style="color: #dc2626; font-size: 16px;"><fmt:formatNumber value="${orderDetail.total}" pattern="#,###" />đ</strong><br>
                                • Cú pháp chuyển khoản: <span style="background: #dbeafe; padding: 2px 8px; border-radius: 4px; font-family: monospace; font-weight: 700; color: #1e40af;">DUT ${orderDetail.code}</span>
                            </div>
                        </div>
                    </c:if>

                    <!-- Danh sách tour đã đặt -->
                    <div class="inner-head" style="margin-top: 15px;">
                        <div class="inner-title" style="font-size: 18px; color: #0f172a;">
                            <i class="fa-solid fa-map-location-dot" style="color: #2563eb;"></i> Danh sách tour trong đơn
                        </div>
                    </div>

                    <div class="inner-tour-list">
                        <c:forEach var="item" items="${orderDetail.items}">
                            <div class="inner-tour-item">
                                <div class="inner-product">
                                    <div class="inner-image">
                                        <a href="${pageContext.request.contextPath}/tour/detail/${item.slug}">
                                            <img alt="${item.name}" src="${item.avatar}" />
                                        </a>
                                    </div>
                                    <div class="inner-content">
                                        <div class="inner-title">
                                            <a href="${pageContext.request.contextPath}/tour/detail/${item.slug}">${item.name}</a>
                                        </div>
                                        <div class="inner-meta">
                                            <div class="inner-meta-item">
                                                📅 Ngày Khởi Hành: <b>${item.departureDateFormatted}</b>
                                            </div>
                                            <div class="inner-meta-item">
                                                📍 Khởi Hành Tại: <b>${item.locationFromName}</b>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="inner-quantity">
                                    <div class="inner-label">Số Lượng Hành Khách</div>
                                    <div class="inner-list">
                                        <c:if test="${item.quantityAdult > 0}">
                                            <div class="inner-item">
                                                <div class="inner-item-label">Người lớn:</div>
                                                <div class="inner-price">
                                                    <span>${item.quantityAdult}</span>
                                                    <span> x </span>
                                                    <span class="inner-highlight"><fmt:formatNumber value="${item.priceNewAdult}" pattern="#,###" /></span>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${item.quantityChildren > 0}">
                                            <div class="inner-item">
                                                <div class="inner-item-label">Trẻ em:</div>
                                                <div class="inner-price">
                                                    <span>${item.quantityChildren}</span>
                                                    <span> x </span>
                                                    <span class="inner-highlight"><fmt:formatNumber value="${item.priceNewChildren}" pattern="#,###" /></span>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${item.quantityBaby > 0}">
                                            <div class="inner-item">
                                                <div class="inner-item-label">Em bé:</div>
                                                <div class="inner-price">
                                                    <span>${item.quantityBaby}</span>
                                                    <span> x </span>
                                                    <span class="inner-highlight"><fmt:formatNumber value="${item.priceNewBaby}" pattern="#,###" /></span>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- Bảng tính tiền -->
                    <div class="inner-list-price">
                        <div class="inner-item">
                            <div class="inner-label">Tạm tính:</div>
                            <div class="inner-price"><fmt:formatNumber value="${orderDetail.subTotal}" pattern="#,###" />đ</div>
                        </div>
                        <div class="inner-item">
                            <div class="inner-label">Giảm giá voucher:</div>
                            <div class="inner-price" style="color: ${orderDetail.discount > 0 ? '#ef4444' : 'inherit'}; font-weight: ${orderDetail.discount > 0 ? '700' : 'normal'};">
                                <c:choose>
                                    <c:when test="${orderDetail.discount > 0}">
                                        -<fmt:formatNumber value="${orderDetail.discount}" pattern="#,###" />đ
                                    </c:when>
                                    <c:otherwise>0đ</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="inner-item">
                            <div class="inner-label">Tổng thanh toán:</div>
                            <div class="inner-total-price"><fmt:formatNumber value="${orderDetail.total}" pattern="#,###" />đ</div>
                        </div>
                    </div>

                </div>
            </div>
        </c:if>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
