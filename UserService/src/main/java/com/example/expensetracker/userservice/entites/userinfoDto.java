package com.example.expensetracker.userservice.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record userinfoDto (
        @NonNull
        @JsonProperty("first_name")
        String firstName,
        @NonNull
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("user_id")
        @NonNull
        String userId,
        @JsonProperty("email")
        String email,
        @JsonProperty("phone_number")
        Long phoneNumber,
        @JsonProperty("profile_pc")
        String profilePic

        // whats the purpose of thie userservice to get uesr and then
) {
    public UserInfo transformToUserInfo() {
    return UserInfo.builder()
            .userId(this.userId())        // Notice: it's userId(), not getUserId()
            .firstName(this.firstName())
            .LastName(this.lastName())
            .phoneNumber(this.phoneNumber())
            .email(this.email())
            .profilPic(this.profilePic())
            .build();
}

}
