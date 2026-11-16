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
    private String password;
    private List<String> permissions = new ArrayList<>();

    public Account() {
        this.id = 2;
        this.fullName = "Phan Thanh Duy";
        this.email = "ptd.ntn2907@gmail.com";
        this.phone = "0905383132";
        this.position = "Giám đốc";
        this.avatar = "https://res.cloudinary.com/r5hwa2cj/image/upload/v1791638065/hdjdks9vbgdmjveyu4kq.jpg";
        this.roleName = "Quản trị viên";
        this.roleId = 1;
        this.status = "active";
        this.permissions = Arrays.asList(
            "dashboard-view",
            "category-view", "category-create", "category-edit", "category-delete",
            "tour-view", "tour-create", "tour-edit", "tour-delete", "tour-trash",
            "order-view", "order-edit",
            "user-view", "user-create", "user-edit", "user-delete",
            "contact-view", "contact-delete",
            "setting-view", "setting-edit",
            "role-view", "role-create", "role-edit", "role-delete",
            "account-view", "account-create", "account-edit", "account-delete"
        );
    }

    public Account(int id, String fullName, String email, String phone, String avatar, String roleName, String status) {
        this();
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.roleName = roleName;
        this.status = status;
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

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }

    public boolean hasPermission(String perm) {
        return permissions.contains(perm);
    }
}
