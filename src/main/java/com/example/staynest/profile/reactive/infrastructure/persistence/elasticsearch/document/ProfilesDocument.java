package com.example.staynest.profile.reactive.infrastructure.persistence.elasticsearch.document;

import com.example.staynest.profile.reactive.validation.Validation;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;

@Document(indexName = "profiles")
public class ProfilesDocument {

    private static final String CLASS_NAME = ProfilesDocument.class.getSimpleName();

    @Id
    private Long id;

    private SeqNoPrimaryTerm seqNoPrimaryTerm;

    @Field(type = FieldType.Keyword)
    private String publicId;

    @MultiField(
            mainField = @Field(type = FieldType.Keyword),
            otherFields = @InnerField(
                    suffix = "autocomplete",
                    type = FieldType.Search_As_You_Type
            )
    )
    private String username;

    @MultiField(
            mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(
                            suffix = "keyword",
                            type = FieldType.Keyword
                    ),
                    @InnerField(
                            suffix = "autocomplete",
                            type = FieldType.Search_As_You_Type
                    ),
            }
    )
    private String name;

    @Field(type = FieldType.Binary, index = false)
    private byte[] profilePictureUrl;

    @Field(type = FieldType.Short, index = false)
    private Short encryptionKeyId;

    @Field(type = FieldType.Short, index = false)
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