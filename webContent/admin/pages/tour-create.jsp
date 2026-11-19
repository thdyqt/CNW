<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Tạo tour</h1>

<div class="section-8">
    <form id="tour-create-form">
        <div class="inner-group">
            <label class="inner-label" for="name">Tên tour</label>
            <input type="text" id="name" name="name" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="category">Danh mục</label>
            <select id="category" name="category">
                <option value="">-- Chọn danh mục --</option>
                <jsp:include page="../mixins/options.jsp" />
            </select>
        </div>
        <div class="inner-group">
            <label class="inner-label" for="position">Vị trí</label>
            <input type="number" id="position" name="position" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="status">Trạng thái</label>
            <select id="status" name="status">
                <option value="active" selected>Hoạt động</option>
                <option value="inactive">Dừng hoạt động</option>
            </select>
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="avatar">Ảnh đại diện</label>
            <div class="inner-input-image">
                <input type="file" id="avatar" name="avatar" accept="image/*" filepond-image />
            </div>
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="images">Danh sách ảnh</label>
            <div class="inner-input-image-multi">
                <input type="file" id="images" name="images" accept="image/*" filepond-image-multi multiple data-allow-reorder="true" />
            </div>
        </div>
        <div class="inner-group">
            <label class="inner-label">Giá cũ</label>
            <div class="inner-input-list">
                <div class="inner-input-item">
                    <label for="priceAdult">Người lớn</label>
                    <input type="number" id="priceAdult" name="priceAdult" />
                </div>
                <div class="inner-input-item">
                    <label for="priceChildren">Trẻ em</label>
                    <input type="number" id="priceChildren" name="priceChildren" />
                </div>
                <div class="inner-input-item">
                    <label for="priceBaby">Em bé</label>
                    <input type="number" id="priceBaby" name="priceBaby" />
                </div>
            </div>
        </div>
        <div class="inner-group">
            <label class="inner-label">Giá mới</label>
            <div class="inner-input-list">
                <div class="inner-input-item">
                    <label for="priceNewAdult">Người lớn</label>
                    <input type="number" id="priceNewAdult" name="priceNewAdult" />
                </div>
                <div class="inner-input-item">
                    <label for="priceNewChildren">Trẻ em</label>
                    <input type="number" id="priceNewChildren" name="priceNewChildren" />
                </div>
                <div class="inner-input-item">
                    <label for="priceNewBaby">Em bé</label>
                    <input type="number" id="priceNewBaby" name="priceNewBaby" />
                </div>
            </div>
        </div>
        <div class="inner-group">
            <label class="inner-label">Còn lại</label>
            <div class="inner-input-list">
                <div class="inner-input-item">
                    <label for="stockAdult">Người lớn</label>
                    <input type="number" id="stockAdult" name="stockAdult" />
                </div>
                <div class="inner-input-item">
                    <label for="stockChildren">Trẻ em</label>
                    <input type="number" id="stockChildren" name="stockChildren" />
                </div>
                <div class="inner-input-item">
                    <label for="stockBaby">Em bé</label>
                    <input type="number" id="stockBaby" name="stockBaby" />
                </div>
            </div>
        </div>
        <div class="inner-group">
            <label class="inner-label">Những địa điểm có tour
                <div class="inner-checkbox-list">
                    <c:forEach var="item" items="${cityList}">
                        <div class="inner-checkbox-item">
                            <input type="checkbox" id="${item.name}" name="locations" value="${item.id}" />
                            <label for="${item.name}">${item.name}</label>
                        </div>
                    </c:forEach>
                </div>
            </label>
        </div>
        <div class="inner-group">
            <label class="inner-label" for="time">Thời gian</label>
            <input type="text" id="time" name="time" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="vehicle">Phương tiện</label>
            <input type="text" id="vehicle" name="vehicle" />
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="departureDate">Ngày khởi hành</label>
            <input type="date" id="departureDate" name="departureDate" />
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="information">Thông tin tour</label>
            <textarea id="information" name="information" textarea-mce></textarea>
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label">Lịch trình tour
                <div class="inner-schedule">
                    <div class="inner-schedule-list">
                        <div class="inner-schedule-item">
                            <div class="inner-schedule-head">
                                <span class="inner-schedule-button inner-move">
                                    <i class="fa-solid fa-arrows-up-down-left-right"></i>
                                </span>
                                <input type="text" />
                                <span class="inner-schedule-button inner-remove">
                                    <i class="fa-regular fa-trash-can"></i>
                                </span>
                                <span class="inner-schedule-button inner-more">
                                    <i class="fa-solid fa-angle-down"></i>
                                </span>
                            </div>
                            <div class="inner-schedule-body">
                                <textarea id="mce_schedule_0" textarea-mce></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="inner-schedule-add">+ Thêm lịch trình</div>
                </div>
            </label>
        </div>
        <div class="inner-button inner-two-col">
            <button type="submit">Tạo mới</button>
        </div>
    </form>
    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/tour/list">Quay lại danh sách</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
