package ru.vk_test.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vk_test.dao.AccountDao;
import ru.vk_test.dao.RoleDao;
import ru.vk_test.excention.InvalidPasswordException;
import ru.vk_test.excention.RoleNotFoundException;
import ru.vk_test.excention.UsernameExistsException;
import ru.vk_test.excention.UsernameNotFoundException;
import ru.vk_test.requests.RegistrationRequest;
import ru.vk_test.security.Account;
import ru.vk_test.security.Base64Utils;
import ru.vk_test.security.BaseToken;
import ru.vk_test.security.Role;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {



    private final AccountDao accountDao;
    private final RoleDao roleDao;

    private final BCryptPasswordEncoder bCryptPasswordEncoder  = new BCryptPasswordEncoder();
    public UsernamePasswordAuthenticationToken login(HttpServletRequest httpServletRequest) throws InvalidPasswordException, UsernameNotFoundException {
        if (!Base64Utils.tokenIsValid(httpServletRequest)){
            throw new UsernameNotFoundException();
        }
        Map<String, String> map = Base64Utils.decodeFromRequest(httpServletRequest);
        Optional<Account> accountOptional = accountDao.findByUsername(map.get("username"));
        if (accountOptional.isEmpty()){
            log.info("Аккаунт {} не найден", map.get("username"));
            log.info("Аккаунтов {}", accountDao.findAll().size());
            throw new UsernameNotFoundException();
        }
        if (bCryptPasswordEncoder.matches(map.get("password"),accountOptional.get().getPassword())){
            log.info("Пользователь авторизован");
            return new UsernamePasswordAuthenticationToken(accountOptional.get(),null, accountOptional.get().getAuthorities());
        }
        log.info("Пароль {} неверный", map.get("password"));
        throw new InvalidPasswordException();
    }
    public BaseToken baseRegistration(RegistrationRequest registrationRequest) throws RoleNotFoundException, UsernameExistsException {
        Account account = new Account();
        for (String roleName : registrationRequest.getRoles() ){
            Optional<Role> role = roleDao.findByName(roleName);
            if (role.isEmpty()){
                log.info("Роль {} не найдена", roleName);
                throw new RoleNotFoundException();
            }
            account.addRole(role.get());
        }
        if (accountDao.findByUsername(registrationRequest.getUsername()).isPresent()){
            log.info("Пользователь {} уже существует", registrationRequest.getUsername());
            throw new UsernameExistsException();
        }
        account.setUsername(registrationRequest.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(registrationRequest.getPassword()));
        accountDao.save(account);
        log.info("Успешное создание пользователя");
        return new BaseToken(Base64Utils.encode(account.getUsername(), registrationRequest.getPassword()));
    }
}
