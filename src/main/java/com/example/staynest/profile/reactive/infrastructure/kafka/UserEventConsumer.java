package com.example.staynest.profile.reactive.infrastructure.kafka;

import com.example.staynest.profile.reactive.command.create_elasticsearch_document.CreateSearchDocumentCommand;
import com.example.staynest.profile.reactive.command.create_elasticsearch_document.CreateSearchDocumentCommandHandler;
import com.example.staynest.profile.reactive.command.create_profile.CreateProfileCommand;
import com.example.staynest.profile.reactive.command.create_profile.CreateProfileCommandHandler;
import com.example.staynest.profile.reactive.infrastructure.kafka.enums.EventTypes;
import com.example.staynest.profile.reactive.validation.Validation;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Base64;


@Component
public class UserEventConsumer {

    private static final String KAFKA_USER_EVENTS_TOPIC = "user-events";
    private static final String KAFKA_PROFILE_GROUP_ID = "staynest-profile";

    private static final String ID_KEY = "id";
    private static final String PUBLIC_ID_KEY = "publicId";
    private static final String USERNAME_KEY = "username";
    private static final String ENCRYPTION_VERSION_KEY = "encryptionVersion";
    private static final String ENCRYPTION_KEY_ID_KEY = "encryptionKeyId";
    private static final String EVENT_TYPE_KEY = "eventType";

    private static final String USER_CREATED_EVENT_TYPE = EventTypes.USER_CREATED.name();

    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CreateProfileCommandHandler profileCommandHandler;
    private final CreateSearchDocumentCommandHandler searchCommandHandler;


    public UserEventConsumer(
            CreateProfileCommandHandler profileCommandHandler,
            CreateSearchDocumentCommandHandler searchCommandHandler
    ){
        this.profileCommandHandler = profileCommandHandler;
        this.searchCommandHandler = searchCommandHandler;
    }


    @KafkaListener(topics = KAFKA_USER_EVENTS_TOPIC, groupId = KAFKA_PROFILE_GROUP_ID)
    public void consume(String message, Acknowledgment acknowledgment){

        JsonNode jsonNode = objectMapper.readTree(message);

        String eventType = jsonNode.get(EVENT_TYPE_KEY).asString();

        Validation.validate(eventType, "eventType", CLASS_NAME);

        if(USER_CREATED_EVENT_TYPE.equalsIgnoreCase(eventType))
            createProfile(jsonNode, acknowledgment);

    }

    private void createProfile(
            JsonNode after, Acknowledgment acknowledgment
    ){
        JsonNode id = after.get(ID_KEY);
        String publicId = after.get(PUBLIC_ID_KEY).asString();
        String username = after.get(USERNAME_KEY).asString();
        JsonNode encryptionVersion = after.get(ENCRYPTION_VERSION_KEY);
        JsonNode encryptionKeyId = after.get(ENCRYPTION_KEY_ID_KEY);

        Validation.validate(id, "id", CLASS_NAME);
        Validation.validate(publicId, "publicId", CLASS_NAME);
        Validation.validate(username, "username", CLASS_NAME);
        Validation.validate(encryptionVersion, "encryptionVersion", CLASS_NAME);
        Validation.validate(encryptionKeyId, "encryptionKeyId", CLASS_NAME);

        Long identity = id.asLong();
        byte[] pubId = Base64.getDecoder().decode(publicId);
        byte[] userName = Base64.getDecoder().decode(username);
        Short encKeyId = encryptionKeyId.asShort();
        Short encVersion = encryptionVersion.asShort();

        this.profileCommandHandler.handle(
                new CreateProfileCommand(
                        identity,
                        pubId,
                        encVersion,
                        encKeyId
                )
        )
                .whenComplete((response, exception)->{
                    if (exception != null) {
                        exception.printStackTrace();
                        return;
                    }

                    this.searchCommandHandler.handle(
                            new CreateSearchDocumentCommand(
                                    id.asLong(),
                                    pubId,
                                    userName,
                                    encVersion,
                                    encKeyId
                            )
                    ).whenComplete((res, exc)->{
                        if (exc != null) {
                            exc.printStackTrace();
                            return;
                        }

                        acknowledgment.acknowledge();
                    });

                });
    }
}