package ru.vk_test.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonSerialize
public class User {
   private Long id;
   private String username;
   private String email;
   private String phone;
   private String website;
   private Company company;
   private Address  address;
}
