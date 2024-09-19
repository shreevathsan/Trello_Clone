package com.ps.trelloapp.domain;

import com.ps.trelloapp.dto.ProjectDto;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ApiResponse {
    private boolean ok;
    private String message;
    private List<ProjectDto> projectDtos;
    private Date date;


    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProjectDto> getProjectDtos() {
        return projectDtos;
    }

    public void setProjectDtos(List<ProjectDto> projectDtos) {
        this.projectDtos = projectDtos;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public ApiResponse()
    {

    }


}
