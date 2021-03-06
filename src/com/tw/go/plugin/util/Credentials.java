package com.tw.go.plugin.util;

import com.thoughtworks.go.plugin.api.response.validation.ValidationError;
import com.thoughtworks.go.plugin.api.response.validation.ValidationResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Credentials {

    private final String user;
    private final String password;

    public Credentials(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }

    public String getUserInfo() throws UnsupportedEncodingException {
        return String.format("%s:%s", user, URLEncoder.encode(password, "UTF-8"));
    }

    public void validate(ValidationResult validationResult) {
        if (StringUtil.isBlank(user) && StringUtil.isNotBlank(password))
            validationResult.addError(new ValidationError(RepoUrl.USERNAME, "Both Username and password are required."));
        if (StringUtil.isNotBlank(user) && StringUtil.isBlank(password))
            validationResult.addError(new ValidationError(RepoUrl.PASSWORD, "Both Username and password are required."));
    }

    public boolean provided() {
        return StringUtil.isNotBlank(user) && StringUtil.isNotBlank(password);
    }

    public boolean detected() {
        return (user != null) || (password != null);
    }
}
