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
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="inner-group">
                            <label class="inner-label" for="locationTo">Điểm đến</label>
                            <select id="locationTo" name="locationTo">
                                <option value="">-- Chọn điểm đến --</option>
                                <c:forEach var="item" items="${cityList}">
                                    <option value="${item.name}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="inner-group">
                            <label class="inner-label" for="departureDate">Ngày khởi hành</label>
                            <input id="departureDate" name="departureDate" type="date" />
                        </div>
                        <div class="inner-group">
                            <label class="inner-label" for="stock">Số Lượng Hành Khách</label>
                            <div class="inner-input-list">
                                <div class="inner-input-item">
                                    <span>Người lớn:</span>
                                    <input id="stock" value="0" name="stockAdult" type="number" />
                                </div>
                                <div class="inner-input-item">
                                    <span>Trẻ em:</span>
                                    <input value="0" name="stockChildren" type="number" />
                                </div>
                                <div class="inner-input-item">
                                    <span>Em bé:</span>
                                    <input value="0" name="stockBaby" type="number" />
                                </div>
                            </div>
                        </div>
                        <div class="inner-group">
                            <label class="inner-label" for="price">Mức giá</label>
                            <select id="price" name="price">
                                <option value="">-- Chọn khoảng giá --</option>
                                <option value="0-999999">Dưới 1 triệu</option>
                                <option value="1000000-3000000">1 triệu đến 3 triệu</option>
                                <option value="3000000-6000000">3 triệu đến 6 triệu</option>
                                <option value="6000000-10000000">6 triệu đến 10 triệu</option>
                                <option value="10000000-10000000000">Trên 10 triệu</option>
                            </select>
                        </div>
                        <div class="inner-group">
                            <button class="inner-button" type="button">Áp Dụng</button>
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
                            <button type="button">
                                Giá tăng dần
                                <i class="fa-solid fa-square-caret-up"></i>
                            </button>
                            <button type="button">
                                Giá giảm dần
                                <i class="fa-solid fa-square-caret-down"></i>
                            </button>
                            <button class="active" type="button">
                                Khuyến Mãi Hot
                                <i class="fa-solid fa-tag"></i>
                            </button>
                            <button type="button">
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
                    <c:forEach var="item" items="${tourListSection9}">
                        <jsp:include page="../mixins/product-item.jsp" />
                    </c:forEach>
                </div>
                <div class="box-pagination">
                    <button type="button"><i class="fa-solid fa-angles-left"></i></button>
                    <button class="active" type="button">1</button>
                    <button type="button">2</button>
                    <button type="button">3</button>
                    <button type="button">4</button>
                    <button type="button"><i class="fa-solid fa-angles-right"></i></button>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
