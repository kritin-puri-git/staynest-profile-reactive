package com.example.staynest.profile.reactive.domain;

import com.example.staynest.profile.reactive.validation.Validation;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;

public class SearchProfiles {

    private static final String CLASS_NAME = SearchProfiles.class.getSimpleName();

    private Long id;

    private SeqNoPrimaryTerm seqNoPrimaryTerm;

    private String publicId;

    private String username;

    private String name;

    private byte[] profilePictureUrl;

    private Short encryptionKeyId;

    private Short encryptionVersion;


    public void setId(long id){
        this.id = id;
    }

    public void setSeqNoPrimaryTerm(
            SeqNoPrimaryTerm seqNoPrimaryTerm
    ) {
        this.seqNoPrimaryTerm = seqNoPrimaryTerm;
    }

    public void setPublicId(String publicId){
        Validation.validate(publicId, "publicId", CLASS_NAME);
        this.publicId = publicId;
    }

    public void setUsername(String username){
        Validation.validate(username, "username", CLASS_NAME);
        this.username = username;
    }

    public void setName(String name){
        Validation.validate(name, "name", CLASS_NAME);
        this.name = name;
    }

    public void setProfilePictureUrl(byte[] profilePictureUrl, short encryptionKeyId, short encryptionVersion){
        Validation.validate(profilePictureUrl, "profilePictureUrl", CLASS_NAME);
        this.profilePictureUrl = profilePictureUrl;
        this.encryptionKeyId = encryptionKeyId;
        this.encryptionVersion = encryptionVersion;
    }

    public Long getId(){
        return this.id;
    }

    public SeqNoPrimaryTerm getSeqNoPrimaryTerm() {
        return this.seqNoPrimaryTerm;
    }

    public String getPublicId(){
        return this.publicId;
    }

    public String getUsername(){
        return this.username;
    }

    public String getName(){
        return this.name;
    }

    public byte[] getProfilePictureUrl(){
        return this.profilePictureUrl;
    }

    public Short getEncryptionKeyId() {
        return encryptionKeyId;
    }

    public Short getEncryptionVersion() {
        return encryptionVersion;
    }
}