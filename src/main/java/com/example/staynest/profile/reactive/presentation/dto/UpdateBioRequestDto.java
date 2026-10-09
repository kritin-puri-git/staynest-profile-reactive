package com.example.staynest.profile.reactive.presentation.dto;

import jakarta.validation.constraints.Size;

public class UpdateBioRequestDto {

    @Size(max = 500)
    private String bio;

    public void setBio(String bio){
        bio = (bio == null) ? null : bio.trim();

        if(bio != null && bio.isBlank()){
            bio = null;
        }

        this.bio = bio;
    }

    public String getBio(){
        return this.bio;
    }
}
