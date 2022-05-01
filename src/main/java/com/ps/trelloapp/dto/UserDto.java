package com.ps.trelloapp.dto;

public class UserDto {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDto(String name) {
        this.name = name;
    }
    public UserDto()
    {

    }
}
