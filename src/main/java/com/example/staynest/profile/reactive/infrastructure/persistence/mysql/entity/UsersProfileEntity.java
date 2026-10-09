package com.example.staynest.profile.reactive.infrastructure.persistence.mysql.entity;


import com.example.staynest.profile.reactive.validation.Validation;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("users_profile")
public class UsersProfileEntity implements Persistable<Long> {

    @Transient
    private static final String CLASS_NAME = UsersProfileEntity.class.getSimpleName();

    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "public_id")
    private byte[] publicId;

    @Column(value = "public_id_hash")
    private byte[] publicIdHash;

    @Column(value = "name")
    private byte[] name;

    @Column(value = "bio")
    private byte[] bio;

    @Column(value = "profile_picture_url")
    private byte[] profilePictureUrl;

    @Column(value = "date_of_birth")
    private byte[] dateOfBirth;

    @Column(value = "gender")
    private byte[] gender;

    @Column(value = "encryption_version")
    private Short encryptionVersion;

    @Column(value = "encryption_key_id")
    private Short encryptionKeyId;

    @Column(value = "hashing_version")
    private Short hashingVersion;

    @Column(value = "hashing_key_id")
    private Short hashingKeyId;

    @Version
    @Column(value = "version")
    private Long version;

    @CreatedDate
    @Column(value = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(value = "updated_at")
    private Instant updatedAt;


    @Override
    public @Nullable Long getId() {
        return this.id;
    }

    @Transient
    private boolean isNew = false;

    public void setAsNew(){
        this.isNew = true;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }


    public UsersProfileEntity(){}

    public void setId(final long id){
        Validation.validate(id, "id", CLASS_NAME);
        this.id = id;
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
        Validation.validate(name, "name", CLASS_NAME);
        this.name = name.clone();
    }

    public void setBio(final byte[] bio){
        Validation.validate(bio, "bio", CLASS_NAME);
        this.bio = bio.clone();
    }

    public void setProfilePictureUrl(final byte[] profilePictureUrl){
        Validation.validate(profilePictureUrl, "profilePictureUrl", CLASS_NAME);
        this.profilePictureUrl = profilePictureUrl.clone();
    }

    public void setDateOfBirth(final byte[] dateOfBirth){
        Validation.validate(dateOfBirth, "dateOfBirth", CLASS_NAME);
        this.dateOfBirth = dateOfBirth.clone();
    }

    public void setGender(final byte[] gender){
        Validation.validate(gender, "gender", CLASS_NAME);
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

    public void setVersion(final long version){
        this.version = version;
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
