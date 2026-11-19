<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../layouts/header.jsp" />

<jsp:include page="../mixins/box-breadcrumb.jsp" />

<div class="section-9">
    <div class="container">
        <div class="inner-wrap">
            <div class="inner-left">
                <div class="box-filter">
                    <div class="inner-head">
                        <div class="inner-title">Bộ Lọc</div>
                        <i class="fa-solid fa-filter"></i>
                    </div>
                    <div class="inner-body">
                        <div class="inner-group">
                            <label class="inner-label" for="locationFrom">Điểm đi</label>
                            <select id="locationFrom" name="locationFrom">
                                <option value="">-- Chọn điểm đi --</option>
                                <c:forEach var="item" items="${cityList}">
                                    <option value="${item.id}" ${param.locationFrom == item.id ? 'selected' : ''}>${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="inner-group">
                            <label class="inner-label" for="locationTo">Điểm đến</label>
                            <select id="locationTo" name="locationTo">
                                <option value="">-- Chọn điểm đến --</option>
                                <c:forEach var="item" items="${cityList}">
                                    <option value="${item.name}" ${param.locationTo == item.name ? 'selected' : ''}>${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="inner-group">
                            <label class="inner-label" for="departureDate">Ngày khởi hành</label>
                            <input id="departureDate" name="departureDate" type="date" value="${param.departureDate}" />
                        </div>
                        <div class="inner-group">
                            <label class="inner-label" for="stock">Số Lượng Hành Khách</label>
                            <div class="inner-input-list">
                                <div class="inner-input-item">
                                    <span>Người lớn:</span>
                                    <input id="stock" value="${not empty param.stockAdult ? param.stockAdult : 0}" name="stockAdult" type="number" min="0" />
                                </div>
                                <div class="inner-input-item">
                                    <span>Trẻ em:</span>
                                    <input value="${not empty param.stockChildren ? param.stockChildren : 0}" name="stockChildren" type="number" min="0" />
                                </div>
                                <div class="inner-input-item">
                                    <span>Em bé:</span>
                                    <input value="${not empty param.stockBaby ? param.stockBaby : 0}" name="stockBaby" type="number" min="0" />
                                </div>
                            </div>
                        </div>
                        <div class="inner-group">
                            <label class="inner-label" for="price">Mức giá</label>
                            <select id="price" name="price">
                                <option value="">-- Chọn khoảng giá --</option>
                                <option value="0-999999" ${param.price == '0-999999' ? 'selected' : ''}>Dưới 1 triệu</option>
                                <option value="1000000-3000000" ${param.price == '1000000-3000000' ? 'selected' : ''}>1 triệu đến 3 triệu</option>
                                <option value="3000000-6000000" ${param.price == '3000000-6000000' ? 'selected' : ''}>3 triệu đến 6 triệu</option>
                                <option value="6000000-10000000" ${param.price == '6000000-10000000' ? 'selected' : ''}>6 triệu đến 10 triệu</option>
                                <option value="10000000-10000000000" ${param.price == '10000000-10000000000' ? 'selected' : ''}>Trên 10 triệu</option>
                            </select>
                        </div>
                        <div class="inner-group" style="display: flex; gap: 8px;">
                            <button class="inner-button" type="button" style="flex: 1;">Áp Dụng</button>
                            <c:if test="${not empty param.locationFrom || not empty param.locationTo || not empty param.departureDate || not empty param.price || (not empty param.stockAdult && param.stockAdult > 0) || (not empty param.stockChildren && param.stockChildren > 0) || (not empty param.stockBaby && param.stockBaby > 0)}">
                                <a href="${pageContext.request.contextPath}/category/${category.slug}" class="inner-button-reset" title="Đặt lại bộ lọc" style="width: 44px; display: flex; align-items: center; justify-content: center; background: #F1F5F9; color: #64748B; border-radius: 8px; border: 1px solid #CBD5E1; text-decoration: none;">
                                    <i class="fa-solid fa-rotate-left"></i>
                                </a>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="inner-overlay"></div>
            </div>
            <div class="inner-right">
                <div class="inner-info">
                    <h2 class="inner-title">${category.name}</h2>
                    <div class="inner-desc">
                        ${category.description}
                    </div>
                </div>
                <div class="inner-info-2">
                    <div class="inner-sort">
                        <div class="inner-label">Sắp xếp:</div>
                        <div class="inner-list">
                            <button type="button" button-sort="price-asc" class="${param.sortKey == 'price' && param.sortValue == 'asc' ? 'active' : ''}">
                                Giá tăng dần
                                <i class="fa-solid fa-square-caret-up"></i>
                            </button>
                            <button type="button" button-sort="price-desc" class="${param.sortKey == 'price' && param.sortValue == 'desc' ? 'active' : ''}">
                                Giá giảm dần
                                <i class="fa-solid fa-square-caret-down"></i>
                            </button>
                            <button type="button" button-sort="discount-desc" class="${empty param.sortKey || (param.sortKey == 'discount' && param.sortValue == 'desc') ? 'active' : ''}">
                                Khuyến Mãi Hot
                                <i class="fa-solid fa-tag"></i>
                            </button>
                            <button type="button" button-sort="view-desc" class="${param.sortKey == 'view' ? 'active' : ''}">
                                Xem Nhiều
                                <i class="fa-solid fa-eye"></i>
                            </button>
                        </div>
                    </div>
                    <div class="inner-count-record">
                        Tất cả: <b>${fn:length(tourListSection9)}</b> Tour
                    </div>
                    <button class="inner-filter-mobile" type="button">
                        <span class="inner-title">Bộ Lọc</span>
                        <i class="fa-solid fa-filter"></i>
                    </button>
                </div>
                <div class="inner-list-tour">
                    <c:choose>
                        <c:when test="${not empty tourListSection9}">
                            <c:forEach var="item" items="${tourListSection9}">
                                <c:set var="item" value="${item}" scope="request" />
                                <jsp:include page="../mixins/product-item.jsp" />
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div style="grid-column: 1 / -1; text-align: center; padding: 50px 20px; background: white; border-radius: 20px; border: 1px solid #E2E8F0; box-shadow: 0 4px 15px rgba(0,0,0,0.03);">
                                <i class="fa-solid fa-filter-circle-xmark" style="font-size: 42px; color: #94A3B8; margin-bottom: 14px;"></i>
                                <h3 style="font-size: 18px; font-weight: 700; color: #1E293B; margin-bottom: 6px;">Không tìm thấy tour phù hợp</h3>
                                <p style="color: #64748B; font-size: 14px; margin-bottom: 18px;">Rất tiếc, không có tour nào khớp với bộ lọc bạn đã chọn. Hãy thử xóa hoặc thay đổi tiêu chí lọc nhé!</p>
                                <a href="${pageContext.request.contextPath}/category/${category.slug}" class="button-outline" style="padding: 10px 24px; font-size: 13px;">
                                    <i class="fa-solid fa-rotate-left"></i>
                                    <span>Đặt lại bộ lọc</span>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:if test="${fn:length(tourListSection9) > 12}">
                    <div class="box-pagination">
                        <button type="button"><i class="fa-solid fa-angles-left"></i></button>
                        <button class="active" type="button">1</button>
                        <button type="button">2</button>
                        <button type="button"><i class="fa-solid fa-angles-right"></i></button>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
