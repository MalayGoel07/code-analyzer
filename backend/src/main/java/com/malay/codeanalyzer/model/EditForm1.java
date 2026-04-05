package com.malay.codeanalyzer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EditForm1 {

    private String userId;

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name must be at least 3 characters")
    private String name;
    private String email;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
