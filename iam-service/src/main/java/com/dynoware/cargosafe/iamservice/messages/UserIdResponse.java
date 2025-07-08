package com.dynoware.cargosafe.iamservice.messages;

public class UserIdResponse {
    private String username;
    private Long userId;

    public UserIdResponse() {}

    public UserIdResponse(String username, Long userId) {
        this.username = username;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
