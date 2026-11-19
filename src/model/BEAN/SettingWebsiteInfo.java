package model.BEAN;

public class SettingWebsiteInfo {
    private int id;
    private String websiteName;
    private String phone;
    private String email;
    private String address;
    private String logo;
    private String favicon;
    private String facebook;
    private String instagram;
    private String whatsapp;
    private String github;
    private String youtube;
    private String copyright;

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

    public SettingWebsiteInfo(int id, String websiteName, String phone, String email, String address, String logo, String favicon,
                              String facebook, String instagram, String whatsapp, String youtube, String copyright) {
        this.id = id;
        this.websiteName = websiteName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.logo = logo;
        this.favicon = favicon;
        this.facebook = facebook;
        this.instagram = instagram;
        this.whatsapp = whatsapp;
        this.github = whatsapp;
        this.youtube = youtube;
        this.copyright = copyright;
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

    public String getFacebook() { return facebook; }
    public void setFacebook(String facebook) { this.facebook = facebook; }

    public String getInstagram() { return instagram; }
    public void setInstagram(String instagram) { this.instagram = instagram; }

    public String getWhatsapp() { return (whatsapp != null && !whatsapp.isEmpty()) ? whatsapp : github; }
    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }

    public String getGithub() { return (github != null && !github.isEmpty()) ? github : whatsapp; }
    public void setGithub(String github) { this.github = github; }

    public String getYoutube() { return youtube; }
    public void setYoutube(String youtube) { this.youtube = youtube; }

    public String getCopyright() { return copyright; }
    public void setCopyright(String copyright) { this.copyright = copyright; }
}
