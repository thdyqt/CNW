package model.BO;

import model.BEAN.SettingWebsiteInfo;
import model.DAO.SettingDAO;

public class SettingBO {
    private final SettingDAO settingDAO = new SettingDAO();

    public SettingWebsiteInfo getSetting() {
        return settingDAO.getSetting();
    }

    public boolean updateSetting(SettingWebsiteInfo setting) {
        return settingDAO.updateSetting(setting);
    }
}
