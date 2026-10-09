package com.example.staynest.profile.reactive.domain;

import com.example.staynest.profile.reactive.validation.Validation;

public class UserProfile {

    private static final String CLASS_NAME = UserProfile.class.getSimpleName();

    private Long id;
    private byte[] publicId;
    private byte[] publicIdHash;

    private byte[] name;
    private byte[] bio;
    private byte[] profilePictureUrl;
    private byte[] dateOfBirth;
    private byte[] gender;


    private Short encryptionVersion;
    private Short encryptionKeyId;
    private Short hashingVersion;
    private Short hashingKeyId;

    private final Long version;

    public UserProfile(){
        this.version = null;
    }
    public UserProfile(long version){
        this.version = version;
    }
    public UserProfile(long id, long version){
        this.id = id;
        this.version = version;
    }

    public void setPublicId(final byte[] publicId){
        Validation.validate(publicId, "publicId", CLASS_NAME);
        this.publicId = publicId.clone();
    }

    public void setPublicIdHash(final byte[] publicIdHash){
        Validation.validate(publicIdHash, "publicIdHash", CLASS_NAME);
        this.publicIdHash = publicIdHash.clone();
    }

    public void setName(final byte[] name){
        this.name = name.clone();
    }

    public void setBio(final byte[] bio){
        this.bio = bio.clone();
    }

    public void setProfilePictureUrl(final byte[] profilePictureUrl){
        this.profilePictureUrl = profilePictureUrl.clone();
    }

    public void setDateOfBirth(final byte[] dateOfBirth){
        this.dateOfBirth = dateOfBirth.clone();
    }

    public void setGender(final byte[] gender){
        this.gender = gender.clone();
    }

    public void setEncryptionVersion(final short encryptionVersion){
        this.encryptionVersion = encryptionVersion;
    }

    public void setEncryptionKeyId(final short encryptionKeyId){
        this.encryptionKeyId = encryptionKeyId;
    }

    public void setHashingVersion(final short hashingVersion){
        this.hashingVersion = hashingVersion;
    }

    public void setHashingKeyId(final short hashingKeyId){
        this.hashingKeyId = hashingKeyId;
    }

    public Long getId(){
        return this.id;
    }

    public byte[] getPublicId(){
        return this.publicId == null ? null : this.publicId.clone();
    }

    public byte[] getPublicIdHash(){
        return this.publicIdHash == null ? null : this.publicIdHash.clone();
    }

    public byte[] getName(){
        return this.name == null ? null : this.name.clone();
    }

    public byte[] getBio(){
        return this.bio == null ? null : this.bio.clone();
    }

    public byte[] getProfilePictureUrl(){
        return this.profilePictureUrl == null ? null : this.profilePictureUrl.clone();
    }

    public byte[] getDateOfBirth(){
        return this.dateOfBirth == null ? null : this.dateOfBirth.clone();
    }

    public byte[] getGender(){
        return this.gender == null ? null : this.gender.clone();
    }

    public Short getEncryptionVersion(){
        return this.encryptionVersion;
    }

    public Short getEncryptionKeyId(){
        return this.encryptionKeyId;
    }

    public Short getHashingVersion(){
        return this.hashingVersion;
    }

    public Short getHashingKeyId(){
        return this.hashingKeyId;
    }

    public Long getVersion(){
        return this.version;
    }

}
