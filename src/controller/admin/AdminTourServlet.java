package controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.BEAN.*;
import model.BO.*;
import model.DAO.*;
import util.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class AdminTourServlet extends HttpServlet {
    private final TourDAO tourDAO = new TourDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final CityDAO cityDAO = new CityDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();
        if ("PATCH".equalsIgnoreCase(method)) {
            doPost(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        if (path.endsWith("/create")) {
            request.setAttribute("pageTitle", "Tạo tour mới");
            request.setAttribute("categoryList", categoryDAO.getCategoryTree());
            request.setAttribute("cityList", cityDAO.getAllCities());
            request.getRequestDispatcher("/webContent/admin/pages/tour-create.jsp").forward(request, response);
            return;
        }

        if (path.contains("/edit/")) {
            String idStr = path.substring(path.lastIndexOf("/") + 1);
            try {
                int id = Integer.parseInt(idStr);
                Tour tour = tourDAO.getTourById(id);
                if (tour == null) tour = tourDAO.getAllTours().get(0);
                request.setAttribute("tour", tour);
            } catch (Exception e) {
                request.setAttribute("tour", tourDAO.getAllTours().get(0));
            }
            request.setAttribute("pageTitle", "Chỉnh sửa tour");
            request.setAttribute("categoryList", categoryDAO.getCategoryTree());
            request.setAttribute("cityList", cityDAO.getAllCities());
            request.getRequestDispatcher("/webContent/admin/pages/tour-edit.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/trash")) {
            List<Tour> trashList = tourDAO.getTrashTours();
            String keyword = request.getParameter("keyword");
            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = keyword.toLowerCase().trim();
                String kwSlug = kw.replace(" ", "-");
                List<Tour> filteredTrash = new ArrayList<>();
                for (Tour t : trashList) {
                    boolean nameMatch = t.getName() != null && t.getName().toLowerCase().contains(kw);
                    boolean slugMatch = t.getSlug() != null && t.getSlug().toLowerCase().contains(kwSlug);
                    boolean codeMatch = t.getCode() != null && t.getCode().toLowerCase().contains(kw);
                    if (nameMatch || slugMatch || codeMatch) {
                        filteredTrash.add(t);
                    }
                }
                trashList = filteredTrash;
            }

            int page = 1;
            String pageStr = request.getParameter("page");
            if (pageStr != null && !pageStr.trim().isEmpty()) {
                try {
                    page = Math.max(1, Integer.parseInt(pageStr.trim()));
                } catch (Exception ignored) {}
            }

            int limit = 10;
            int total = trashList.size();
            int totalPages = (int) Math.ceil((double) total / limit);
            if (totalPages == 0) totalPages = 1;
            if (page > totalPages) page = totalPages;

            int start = total > 0 ? (page - 1) * limit + 1 : 0;
            int end = Math.min(page * limit, total);

            Map<String, Object> pagination = new HashMap<>();
            pagination.put("currentPage", page);
            pagination.put("totalPages", totalPages);
            pagination.put("total", total);
            pagination.put("start", start);
            pagination.put("end", end);

            int fromIndex = total > 0 ? (page - 1) * limit : 0;
            int toIndex = Math.min(fromIndex + limit, total);
            List<Tour> pagedTrash = trashList.subList(fromIndex, toIndex);

            request.setAttribute("tourTrashList", pagedTrash);
            request.setAttribute("pagination", pagination);
            request.setAttribute("pageTitle", "Thùng rác tour");
            request.getRequestDispatcher("/webContent/admin/pages/tour-trash.jsp").forward(request, response);
            return;
        }

        // Default: /list
        List<Tour> tours = tourDAO.getAllTours();
        String status = request.getParameter("status");
        String keyword = request.getParameter("keyword");
        String createdBy = request.getParameter("createdBy");
        String categoryParam = request.getParameter("category");
        String priceParam = request.getParameter("price");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        if (startDateStr != null && !startDateStr.trim().isEmpty()) {
            try {
                startDate = df.parse(startDateStr.trim());
            } catch (Exception ignored) {}
        }
        if (endDateStr != null && !endDateStr.trim().isEmpty()) {
            try {
                Date parsedEnd = df.parse(endDateStr.trim());
                Calendar cal = Calendar.getInstance();
                cal.setTime(parsedEnd);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                endDate = cal.getTime();
            } catch (Exception ignored) {}
        }

        Integer catFilterId = null;
        if (categoryParam != null && !categoryParam.trim().isEmpty()) {
            try {
                catFilterId = Integer.parseInt(categoryParam.trim());
            } catch (Exception ignored) {}
        }
        List<Integer> allowedCatIds = null;
        if (catFilterId != null && catFilterId > 0) {
            allowedCatIds = categoryDAO.getSubcategoryIds(catFilterId);
        }

        List<Tour> filtered = new ArrayList<>();
        for (Tour t : tours) {
            boolean match = true;
            if (status != null && !status.isEmpty() && !t.getStatus().equalsIgnoreCase(status)) {
                match = false;
            }
            if (allowedCatIds != null && !allowedCatIds.contains(t.getCategoryId())) {
                match = false;
            }
            if (priceParam != null && !priceParam.isEmpty()) {
                double price = (t.getPriceNewAdult() > 0) ? t.getPriceNewAdult() : t.getPriceAdult();
                if ("under_2m".equalsIgnoreCase(priceParam) && price >= 2000000) {
                    match = false;
                } else if ("2m_to_4m".equalsIgnoreCase(priceParam) && (price < 2000000 || price > 4000000)) {
                    match = false;
                } else if ("4m_to_8m".equalsIgnoreCase(priceParam) && (price < 4000000 || price > 8000000)) {
                    match = false;
                } else if ("over_8m".equalsIgnoreCase(priceParam) && price <= 8000000) {
                    match = false;
                }
            }
            if (createdBy != null && !createdBy.isEmpty()) {
                boolean userMatch = false;
                Account selectedAdmin = null;
                try {
                    int aId = Integer.parseInt(createdBy.trim());
                    selectedAdmin = accountDAO.getAccountById(aId);
                } catch (Exception ignored) {}
                String adminName = selectedAdmin != null ? selectedAdmin.getFullName() : null;

                if (t.getCreatedBy() != null) {
                    if (t.getCreatedBy().equalsIgnoreCase(createdBy)) {
                        userMatch = true;
                    } else if (adminName != null && t.getCreatedBy().equalsIgnoreCase(adminName)) {
                        userMatch = true;
                    } else if (adminName != null && t.getCreatedBy().contains(adminName)) {
                        userMatch = true;
                    }
                }
                if (!userMatch) match = false;
            }
            if (startDate != null) {
                boolean dateMatch = false;
                if (t.getCreatedAt() != null && !t.getCreatedAt().before(startDate)) {
                    dateMatch = true;
                }
                if (t.getDepartureDate() != null && !t.getDepartureDate().before(startDate)) {
                    dateMatch = true;
                }
                if (!dateMatch) match = false;
            }
            if (endDate != null) {
                boolean dateMatch = false;
                if (t.getCreatedAt() != null && !t.getCreatedAt().after(endDate)) {
                    dateMatch = true;
                }
                if (t.getDepartureDate() != null && !t.getDepartureDate().after(endDate)) {
                    dateMatch = true;
                }
                if (!dateMatch) match = false;
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = keyword.toLowerCase().trim();
                String kwSlug = kw.replace(" ", "-");
                boolean nameMatch = t.getName() != null && t.getName().toLowerCase().contains(kw);
                boolean slugMatch = t.getSlug() != null && t.getSlug().toLowerCase().contains(kwSlug);
                boolean codeMatch = t.getCode() != null && t.getCode().toLowerCase().contains(kw);
                if (!nameMatch && !slugMatch && !codeMatch) {
                    match = false;
                }
            }
            if (match) filtered.add(t);
        }

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageStr.trim()));
            } catch (Exception ignored) {}
        }

        int limit = 10;
        int total = filtered.size();
        int totalPages = (int) Math.ceil((double) total / limit);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        int start = total > 0 ? (page - 1) * limit + 1 : 0;
        int end = Math.min(page * limit, total);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", page);
        pagination.put("totalPages", totalPages);
        pagination.put("total", total);
        pagination.put("start", start);
        pagination.put("end", end);

        int fromIndex = total > 0 ? (page - 1) * limit : 0;
        int toIndex = Math.min(fromIndex + limit, total);
        List<Tour> pagedTours = filtered.subList(fromIndex, toIndex);

        request.setAttribute("tourList", pagedTours);
        request.setAttribute("pagination", pagination);
        request.setAttribute("categoryList", categoryDAO.getCategoryTree());
        request.setAttribute("adminList", accountDAO.getAccounts(null, null, null, null, null, 1, 100));
        request.setAttribute("pageTitle", "Quản lý tour");
        request.getRequestDispatcher("/webContent/admin/pages/tour-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        if (path.contains("/delete/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                tourDAO.deleteTour(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Thao tác thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        if (path.contains("/undo/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                tourDAO.undoTour(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Thao tác thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        if (path.contains("/delete-destroy/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                tourDAO.deleteDestroyTour(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Thao tác thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        if (path.contains("/change-multi")) {
            try {
                StringBuilder sb = new StringBuilder();
                java.io.BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                if (sb.length() > 0) {
                    com.fasterxml.jackson.databind.JsonNode node = objectMapper.readTree(sb.toString());
                    String option = node.has("option") ? node.get("option").asText() : "";
                    if (node.has("ids") && node.get("ids").isArray()) {
                        for (com.fasterxml.jackson.databind.JsonNode idNode : node.get("ids")) {
                            int id = idNode.asInt();
                            if ("delete".equalsIgnoreCase(option)) {
                                tourDAO.deleteTour(id);
                            } else if ("undo".equalsIgnoreCase(option)) {
                                tourDAO.undoTour(id);
                            } else if ("active".equalsIgnoreCase(option)) {
                                tourDAO.changeStatus(id, "active");
                            } else if ("inactive".equalsIgnoreCase(option)) {
                                tourDAO.changeStatus(id, "inactive");
                            } else if ("delete-destroy".equalsIgnoreCase(option)) {
                                tourDAO.deleteDestroyTour(id);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Thao tác thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        if (path.endsWith("/create")) {
            try {
                String name = request.getParameter("name");
                String categoryStr = request.getParameter("category");
                int categoryId = (categoryStr != null && !categoryStr.isEmpty()) ? Integer.parseInt(categoryStr) : 0;
                String positionStr = request.getParameter("position");
                int position = (positionStr != null && !positionStr.isEmpty()) ? Integer.parseInt(positionStr) : 1;
                String status = request.getParameter("status");

                Part avatarPart = null;
                try {
                    avatarPart = request.getPart("avatar");
                } catch (Exception ignored) {}
                String avatarUrl = "";
                if (avatarPart != null && avatarPart.getSize() > 0) {
                    avatarUrl = CloudinaryUtil.upload(avatarPart);
                }

                List<String> imageUrls = new ArrayList<>();
                try {
                    for (Part part : request.getParts()) {
                        if ("images".equals(part.getName()) && part.getSize() > 0) {
                            if (part.getContentType() != null && part.getContentType().startsWith("image/")) {
                                String upUrl = CloudinaryUtil.upload(part);
                                if (upUrl != null && !upUrl.isEmpty()) {
                                    imageUrls.add(upUrl);
                                }
                            }
                        }
                    }
                } catch (Exception ignored) {}
                String[] paramImages = request.getParameterValues("images");
                if (paramImages != null) {
                    for (String img : paramImages) {
                        if (img != null && !img.trim().isEmpty() && img.startsWith("http")) {
                            if (!imageUrls.contains(img.trim())) {
                                imageUrls.add(img.trim());
                            }
                        }
                    }
                }
                if (imageUrls.isEmpty() && avatarUrl != null && !avatarUrl.isEmpty()) {
                    imageUrls.add(avatarUrl);
                }

                double priceAdult = parseDouble(request.getParameter("priceAdult"), 0);
                double priceChildren = parseDouble(request.getParameter("priceChildren"), 0);
                double priceBaby = parseDouble(request.getParameter("priceBaby"), 0);
                double priceNewAdult = parseDouble(request.getParameter("priceNewAdult"), 0);
                double priceNewChildren = parseDouble(request.getParameter("priceNewChildren"), 0);
                double priceNewBaby = parseDouble(request.getParameter("priceNewBaby"), 0);

                int stockAdult = parseInt(request.getParameter("stockAdult"), 0);
                int stockChildren = parseInt(request.getParameter("stockChildren"), 0);
                int stockBaby = parseInt(request.getParameter("stockBaby"), 0);

                String locationsParam = request.getParameter("locations");
                String formattedLocations = "";
                if (locationsParam != null && !locationsParam.trim().isEmpty()) {
                    try {
                        com.fasterxml.jackson.databind.JsonNode locNode = objectMapper.readTree(locationsParam);
                        if (locNode.isArray()) {
                            List<String> locList = new ArrayList<>();
                            for (com.fasterxml.jackson.databind.JsonNode item : locNode) {
                                locList.add(item.asText());
                            }
                            formattedLocations = String.join(",", locList);
                        } else {
                            formattedLocations = locationsParam;
                        }
                    } catch (Exception e) {
                        formattedLocations = locationsParam;
                    }
                }

                String time = request.getParameter("time");
                String vehicle = request.getParameter("vehicle");
                String departureDateStr = request.getParameter("departureDate");
                java.util.Date depDate = null;
                if (departureDateStr != null && !departureDateStr.isEmpty()) {
                    try {
                        depDate = new SimpleDateFormat("yyyy-MM-dd").parse(departureDateStr);
                    } catch (Exception ignored) {}
                }
                String information = request.getParameter("information");

                String schedulesJson = request.getParameter("schedules");
                List<Tour.ScheduleItem> scheduleItems = new ArrayList<>();
                if (schedulesJson != null && !schedulesJson.trim().isEmpty()) {
                    try {
                        com.fasterxml.jackson.databind.JsonNode schedNode = objectMapper.readTree(schedulesJson);
                        if (schedNode.isArray()) {
                            for (com.fasterxml.jackson.databind.JsonNode sNode : schedNode) {
                                String title = sNode.has("title") ? sNode.get("title").asText() : "";
                                String desc = sNode.has("description") ? sNode.get("description").asText() : "";
                                scheduleItems.add(new Tour.ScheduleItem(title, desc));
                            }
                        }
                    } catch (Exception ignored) {}
                }

                String slug = (name != null) ? name.toLowerCase().replaceAll("[^a-z0-9]+", "-") + "-" + (System.currentTimeMillis() % 100000) : "tour-" + System.currentTimeMillis();

                Tour tour = new Tour();
                tour.setCode("28T" + (System.currentTimeMillis() % 100000));
                tour.setName(name);
                tour.setCategoryId(categoryId);
                tour.setPosition(position);
                tour.setStatus(status != null ? status : "active");
                tour.setAvatar(avatarUrl);
                tour.setImages(imageUrls);
                tour.setPriceAdult(priceAdult);
                tour.setPriceChildren(priceChildren);
                tour.setPriceBaby(priceBaby);
                tour.setPriceNewAdult(priceNewAdult);
                tour.setPriceNewChildren(priceNewChildren);
                tour.setPriceNewBaby(priceNewBaby);
                tour.setStockAdult(stockAdult);
                tour.setStockChildren(stockChildren);
                tour.setStockBaby(stockBaby);
                tour.setLocations(formattedLocations);
                tour.setTime(time);
                tour.setVehicle(vehicle);
                tour.setDepartureDate(depDate);
                tour.setInformation(information);
                tour.setSchedules(scheduleItems);
                tour.setSlug(slug);

                tourDAO.createTour(tour);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Tạo tour thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        if (path.contains("/edit/")) {
            try {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                Tour existing = tourDAO.getTourById(id);
                if (existing != null) {
                    String name = request.getParameter("name");
                    String categoryStr = request.getParameter("category");
                    int categoryId = (categoryStr != null && !categoryStr.isEmpty()) ? Integer.parseInt(categoryStr) : existing.getCategoryId();
                    String positionStr = request.getParameter("position");
                    int position = (positionStr != null && !positionStr.isEmpty()) ? Integer.parseInt(positionStr) : existing.getPosition();
                    String status = request.getParameter("status");

                    Part avatarPart = null;
                    try {
                        avatarPart = request.getPart("avatar");
                    } catch (Exception ignored) {}
                    if (avatarPart != null && avatarPart.getSize() > 0) {
                        String avatarUrl = CloudinaryUtil.upload(avatarPart);
                        if (avatarUrl != null && !avatarUrl.isEmpty()) {
                            existing.setAvatar(avatarUrl);
                        }
                    }

                    List<String> imageUrls = new ArrayList<>();
                    String[] paramImages = request.getParameterValues("images");
                    if (paramImages != null) {
                        for (String img : paramImages) {
                            if (img != null && !img.trim().isEmpty() && img.startsWith("http")) {
                                if (!imageUrls.contains(img.trim())) {
                                    imageUrls.add(img.trim());
                                }
                            }
                        }
                    }
                    try {
                        for (Part part : request.getParts()) {
                            if ("images".equals(part.getName()) && part.getSize() > 0) {
                                if (part.getContentType() != null && part.getContentType().startsWith("image/")) {
                                    String upUrl = CloudinaryUtil.upload(part);
                                    if (upUrl != null && !upUrl.isEmpty()) {
                                        imageUrls.add(upUrl);
                                    }
                                }
                            }
                        }
                    } catch (Exception ignored) {}
                    if (!imageUrls.isEmpty()) {
                        existing.setImages(imageUrls);
                    }

                    if (name != null && !name.isEmpty()) existing.setName(name);
                    existing.setCategoryId(categoryId);
                    existing.setPosition(position);
                    if (status != null && !status.isEmpty()) existing.setStatus(status);

                    existing.setPriceAdult(parseDouble(request.getParameter("priceAdult"), existing.getPriceAdult()));
                    existing.setPriceChildren(parseDouble(request.getParameter("priceChildren"), existing.getPriceChildren()));
                    existing.setPriceBaby(parseDouble(request.getParameter("priceBaby"), existing.getPriceBaby()));
                    existing.setPriceNewAdult(parseDouble(request.getParameter("priceNewAdult"), existing.getPriceNewAdult()));
                    existing.setPriceNewChildren(parseDouble(request.getParameter("priceNewChildren"), existing.getPriceNewChildren()));
                    existing.setPriceNewBaby(parseDouble(request.getParameter("priceNewBaby"), existing.getPriceNewBaby()));

                    existing.setStockAdult(parseInt(request.getParameter("stockAdult"), existing.getStockAdult()));
                    existing.setStockChildren(parseInt(request.getParameter("stockChildren"), existing.getStockChildren()));
                    existing.setStockBaby(parseInt(request.getParameter("stockBaby"), existing.getStockBaby()));

                    String locationsParam = request.getParameter("locations");
                    if (locationsParam != null && !locationsParam.trim().isEmpty()) {
                        try {
                            com.fasterxml.jackson.databind.JsonNode locNode = objectMapper.readTree(locationsParam);
                            if (locNode.isArray()) {
                                List<String> locList = new ArrayList<>();
                                for (com.fasterxml.jackson.databind.JsonNode item : locNode) {
                                    locList.add(item.asText());
                                }
                                existing.setLocations(String.join(",", locList));
                            } else {
                                existing.setLocations(locationsParam);
                            }
                        } catch (Exception e) {
                            existing.setLocations(locationsParam);
                        }
                    }

                    String time = request.getParameter("time");
                    if (time != null) existing.setTime(time);
                    String vehicle = request.getParameter("vehicle");
                    if (vehicle != null) existing.setVehicle(vehicle);

                    String departureDateStr = request.getParameter("departureDate");
                    if (departureDateStr != null && !departureDateStr.isEmpty()) {
                        try {
                            existing.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse(departureDateStr));
                        } catch (Exception ignored) {}
                    }
                    String info = request.getParameter("information");
                    if (info != null) existing.setInformation(info);

                    String schedulesJson = request.getParameter("schedules");
                    if (schedulesJson != null && !schedulesJson.trim().isEmpty()) {
                        try {
                            com.fasterxml.jackson.databind.JsonNode schedNode = objectMapper.readTree(schedulesJson);
                            if (schedNode.isArray()) {
                                List<Tour.ScheduleItem> sList = new ArrayList<>();
                                for (com.fasterxml.jackson.databind.JsonNode sNode : schedNode) {
                                    String title = sNode.has("title") ? sNode.get("title").asText() : "";
                                    String desc = sNode.has("description") ? sNode.get("description").asText() : "";
                                    sList.add(new Tour.ScheduleItem(title, desc));
                                }
                                existing.setSchedules(sList);
                            }
                        } catch (Exception ignored) {}
                    }

                    tourDAO.updateTour(existing);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> result = new HashMap<>();
            result.put("code", "success");
            result.put("message", "Cập nhật tour thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        // Generic fallback
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", "success");
        result.put("message", "Thao tác thành công!");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    private double parseDouble(String str, double defaultVal) {
        if (str == null || str.trim().isEmpty()) return defaultVal;
        try {
            return Double.parseDouble(str.trim());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    private int parseInt(String str, int defaultVal) {
        if (str == null || str.trim().isEmpty()) return defaultVal;
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", "success");
        result.put("message", "Xóa thành công!");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}