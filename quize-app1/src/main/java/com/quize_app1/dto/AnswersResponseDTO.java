package com.quize_app1.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnswersResponseDTO {

    private int id;
    private String response;

    public AnswersResponseDTO(int id, String response) {
        this.id = id;
        this.response = response;
    }

    public AnswersResponseDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
