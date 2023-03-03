package com.ltp.gradesubmission.security.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.security.SecurityConstants;
import com.ltp.gradesubmission.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private CustomAuthenticationManager customAuthenticationManager;

    // kada korisnik posalje request na url /api/authenticate poziva se ova metoda - attemptAuthentication()
    // Authentication - import org.springframework.security.core.Authentication;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // iz requesta se uzimaju kredencijali koje je poslao klijent 
        // sa getInputStream() iz requesta se uzimaju podaci kao binary data i konvertuju se u property-e objekta User
        // koristi se ObjectMapper - uzimaju se podaci iz requesta, desirajizuju se i ObjectMapper mapira podatke na property-e objekat User
        // ako korisnik posalje pogresna imena property-a ObjectMapper() ne moze to da obradi pa se stavlja u try-catch blok
        // ovde se baca izuzetak RuntimeErrorException(. Ne moze neki nas custom izuzetak jer se ovaj request hendluje dok jos nije dosao do nasih kontrolera iz kojih se bacaju exception-i iz ApplicationExceptionHandler 
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());

            // sada je vec kreiran authentification objekat (user) i on se salje u AuthentificationManager
            // prvi parametar je tzv. 'principal' i po njemu s eidentifikuje korisnik a to je ovde username - user.getClass()
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // authentication je objekat koji sadrzi izvucene username i password iz requesta i oni se prosledjuju CustomAuthenticationManager-u koji ih proverava. Ako su kredencijali dobri poziva se successfulAuthentication()
            // ako kredencijali nisu dobri poziva se unsuccessfulAuthentication()
            return customAuthenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    // ako je poziv metode customAuthenticationManager.authenticate(authentication) u try bloku vratio authentification objekat znaci da je autentifikacija prosla i da je korisnik u requestu prosledio dobar username i password pa se sada poziva ova metoda
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        System.out.println("**************************** username => OK ||||||| password => OK ****************************");
        
        // kreiranje JWT-a
        // posto se u payload JWT-a ukljucuje trenutno vreme on ce svaki put biti drugaciji. Kada god korisnik posalje kredencijale za logovanje vratice mu se drugaciji JWT zbog razlicitog vremena
        String token = JWT.create()
        .withSubject(authResult.getName()) // iz authentication objekta se uzima username
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION)) // uzima se trenutno vreme i na njega se dodaje 2 sata
        .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY)); // potpisivanje JWT-a


        System.out.println("*************************************************************************");
        System.out.println(token);
        System.out.println("*************************************************************************");

        // kreira se response koji ce u headeru imati "Authorization" : "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJQZXJhIiwiZXhwIjoxNjc3MzQxNDI4fQ.cSchrWE22Y5nfdbE2YhKfQFFIZeaxMDl_XldxvFuOJiPfq6ppSvdjJj7XU0AZ8Uf7jMIIkUDQMISXOSvkVaeZg"
        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + token);
        response.getWriter().write("JWT: " + token);
        response.getWriter().flush();
    }

    // ako ako korisnik nije prosledio dobre podatke onda se poziva ova metoda
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
                System.out.println("**************************** username or password => WRONG ****************************");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(failed.getMessage());
                response.getWriter().flush();
    }
}
