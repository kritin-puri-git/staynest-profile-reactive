package com.example.staynest.profile.reactive.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateNameRequestDto {

    @NotBlank
    @Size(max = 50)
    @Pattern(
            regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$"
    )
    private String name;

    public void setName(String name){
        this.name = (name == null) ? null : name.trim();
    }

    public String getName(){
        return this.name;
    }
}
