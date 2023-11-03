package com.swp_project_g4.Service;

public enum CookiesToken {
    LEARNER("learnerJwtToken"),
    ADMIN("adminJwtToken"),
    ORGANIZATION("organizationJwtToken"),
    INSTRUCTOR("instructorJwtToken"),
    RESET("resetJwtToken");


    private final String text;

    CookiesToken(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
