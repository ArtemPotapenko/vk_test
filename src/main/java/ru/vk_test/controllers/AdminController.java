package ru.vk_test.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vk_test.excention.RoleNotFoundException;
import ru.vk_test.excention.UsernameExistsException;
import ru.vk_test.requests.RegistrationRequest;
import ru.vk_test.security.BaseToken;
import ru.vk_test.services.AccountService;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AccountService accountService;
    @PostMapping("api/admin/create_account")
    public ResponseEntity<?> createAccount(RegistrationRequest registrationRequest){
        try {
            return ResponseEntity.ok().body(accountService.baseRegistration(registrationRequest));
        } catch (RoleNotFoundException e) {
            return ResponseEntity.badRequest().body("Такой роли нет");
        } catch (UsernameExistsException e) {
            return ResponseEntity.badRequest().body("Такой пользователь уже существует");
        }
    }


}
