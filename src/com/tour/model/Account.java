package com.tour.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account {
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private String avatar;
    private String roleName;
    private int roleId;
    private String position = "Giám đốc";
    private String status;
    private String token;
    private String password;
    private List<String> permissions = new ArrayList<>();

    public Account() {
        this.permissions = new ArrayList<>();
    }

    public Account(int id, String fullName, String email, String phone, String avatar, String roleName, String status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.roleName = roleName;
        this.status = status;
        this.permissions = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAvatar() { return avatar != null ? avatar : ""; }
    public String getAvatarOrDefault() { return avatar != null && !avatar.trim().isEmpty() ? avatar : "/admin/assets/images/AvatarIcon.jpg"; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public String getPosition() { return position != null ? position : "Giám đốc"; }
    public void setPosition(String position) { this.position = position; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<String> getPermissions() { 
        return permissions != null ? permissions : new ArrayList<>(); 
    }
    public void setPermissions(List<String> permissions) { 
        this.permissions = permissions != null ? permissions : new ArrayList<>(); 
    }

    public boolean hasPermission(String perm) {
        if (perm == null || perm.trim().isEmpty()) {
            return true;
        }
        // Quản trị viên (roleId == 1 hoặc tên Quản trị viên) có đầy đủ toàn bộ quyền
        if (roleId == 1 || "Quản trị viên".equalsIgnoreCase(roleName)) {
            return true;
        }
        if (permissions == null || permissions.isEmpty()) {
            return false;
        }
        if (permissions.contains("*") || permissions.contains("all")) {
            return true;
        }
        return permissions.contains(perm.trim());
    }
}
