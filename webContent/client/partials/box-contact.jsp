<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="box-contact">
    <ul>
        <li>
            <a target="_blank" href="${not empty settingWebsiteInfo.facebook ? settingWebsiteInfo.facebook : '#'}" title="Facebook">
                <i class="fa-brands fa-facebook"></i>
            </a>
        </li>
        <li>
            <a target="_blank" href="${not empty settingWebsiteInfo.instagram ? settingWebsiteInfo.instagram : '#'}" title="Instagram">
                <i class="fa-brands fa-instagram"></i>
            </a>
        </li>
        <li>
            <a target="_blank" href="${not empty settingWebsiteInfo.github ? settingWebsiteInfo.github : '#'}" title="GitHub">
                <i class="fa-brands fa-github"></i>
            </a>
        </li>
        <li>
            <a target="_blank" href="${not empty settingWebsiteInfo.youtube ? settingWebsiteInfo.youtube : '#'}" title="YouTube">
                <i class="fa-brands fa-youtube"></i>
            </a>
        </li>
    </ul>
</nav>
