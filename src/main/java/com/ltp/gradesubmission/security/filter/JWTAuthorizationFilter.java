package com.ltp.gradesubmission.security.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ltp.gradesubmission.security.SecurityConstants;

// kada se korisnik uloguje on dobija JWT i kada zeli da pristupi API resursima (url-ovi u kontrolerima) on u header-u salje JWT kao dokaz da je autentifikovan.  
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    // ovde je dosao request koji je klijent poslao i koji u header-u ima "Authorization: Berer JWT"
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // uzima se token iz header-a koji je u property-u "Authorization"
        // ovo dole ce vratiti string "Bearer JWT" pa se deo "Bearer " menja sa "" 
        String header = request.getHeader("Authorization"); 

        // ako korisnik salje request da se registruje (kreira nalog) onda nema property-a "Authorization" pa je String header = null onda nema JWT-a pa predji na sledeci filter. Kada se predje na sledeci filter ide se na registraciju korisnika
        // !header.startsWith(SecurityConstants.BEARER) - ili ako ima property-a "Authorization" a ne pocinje sa "Bearer " onda nema JWT-a pa predji na sledeci filter. Kada se predje na sledeci filter ide se na registraciju korisnika
        if(header == null || !header.startsWith(SecurityConstants.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(SecurityConstants.BEARER, "");

        // verifikuje se da li je token validan - koristi se metoda require() iz JWT klase
        // Algorithm.HMAC512(SecurityConstants.SECRET_KEY) je algoritam koji je koriscen za kreiranje treceg dela tokena tj. digitalnog potpisa
        // require() vraca authentification buider pa se zato posle dodaje build()
        // na kraju se verifikuje token koji je dobijen od klijenta - ako je validan sa getSubject() iz njega izvlacimo username
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                        .build()
                        .verify(token)
                        .getSubject();

        // kreira se authentication objekat od username-a i mora da se koristi konstruktor kojem se prosledjuju parametri principal, credentials i authorities. credentials je nulla a authorities je prazna lista ali mora da se koristi ovaj konstruktor jer ce ovako property u authentication objektu biti setovan na true. Ovo ne moze da se uradi sa authentication.setAuthenticated(true); jer se dobija legal exception

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList());
        
        // authentication objekat se setuje u SecurityContextHolder-u
        // authentication objekat (korisnik) koji je u SecurityContextHolder-u autorizovan je da koristi api resurse
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // kada se zavrsila autorizacija sa doFilter() moramo da kazemo requestu da ide na sledeci filter a posto je ovo poslednji filter u lancu onda request prelazi na kontrolere
        filterChain.doFilter(request, response);
    }
    
}
