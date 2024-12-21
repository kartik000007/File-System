package com.Email.MailSending.dto;

public class InvalidUserDetailDTO {
    private Long id;
    private String name;
    private Long phoneNumber;
    private String email;
    private String validationField;
    private String validationMessage;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValidationField() {
        return validationField;
    }

    public void setValidationField(String validationField) {
        this.validationField = validationField;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    @Override
    public String toString() {
        return "InvalidUserDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", validationField='" + validationField + '\'' +
                ", validationMessage='" + validationMessage + '\'' +
                '}';
    }
}
