package com.ltp.gradesubmission.security.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.service.UserService;
import com.ltp.gradesubmission.service.UserServiceImpl;

import lombok.AllArgsConstructor;

// ovu klasu sa @Component pretvaramo u bean da nebi klase bile tightly coupled
// u klasi AuthenticationFilter je potrebna instanca ove klase pa se koristi @autowired da bi bile loosly coupled
// u AuthenticationFilter se koristi lombok @AllArgsConstructor pa se kao property samo navede CustomAuthenticationManager i on ce biti automatski injectovan 
@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager { 

    // koristi se @AllArgsConstructor pa je ovo automatski autowired
    private UserService userService;

    // injectuje se  za hashovanje lozinke 
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // authentication je objekat koji se prosledjuje ovoj metodi i u njemu se kredencijali koji trebaju da se uporede sa onima u bazi
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // uzima se user po username-u
        // kasa se uzimaju podaci iz requesta kreira se objekat klase UsernamePasswordAuthenticationToken i u njegovproperty principal se smestaju username i password a user name uzima sa getName(). Property principaj je objekat. U credentials property-u se cuva lozinka
        // iz authentication objekta iz principal property-a username moze da se uzme na dva nacina
        //User user = userService.getUser(authentication.getName());
        User user = userService.getUser(authentication.getPrincipal().toString());
        
        // kada smo dobili user-a iz baze po username-u sada se proverava lozinka. Dobijena lozinka od klijenta se hash-uje i uporedjuje sa onom iz baze. Ako korisnik zna dobru lozinku ond ce nen hash biti isti kao onaj iz baze
        // ako se lozinke ne poklapaju onda se baca izuzetak BadCredentialsException("Wrong PASSWORD")
        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            throw new BadCredentialsException("You provided an incorrect password.");
        }

        // ako su username i lozinka dobri onda se doslo do ovde i vraca se authentification objekat kome se setuju username i password
        // username - authentication.getName() ili user.getUsername() - isti su
        // password - authentication.getCredentials().toString() ili user.getPassword() - isti su
        // ovo se vraca u AuthentificationFilter u metodu attemptAuthentification()
        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());
    }
}
