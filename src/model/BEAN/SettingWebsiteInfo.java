package model.BEAN;

public class SettingWebsiteInfo {
    private int id;
    private String websiteName;
    private String phone;
    private String email;
    private String address;
    private String logo;
    private String favicon;

    public SettingWebsiteInfo() {}

    public SettingWebsiteInfo(int id, String websiteName, String phone, String email, String address, String logo, String favicon) {
        this.id = id;
        this.websiteName = websiteName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.logo = logo;
        this.favicon = favicon;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getWebsiteName() { return websiteName; }
    public void setWebsiteName(String websiteName) { this.websiteName = websiteName; }

    public String getName() { return websiteName; } 

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    public String getFavicon() { return favicon; }
    public void setFavicon(String favicon) { this.favicon = favicon; }
}

