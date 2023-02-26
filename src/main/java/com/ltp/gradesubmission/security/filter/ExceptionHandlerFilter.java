package com.ltp.gradesubmission.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ltp.gradesubmission.exception.EntityNotFoundException;

// kreira se custom filter
// OncePerRequestFilter garantuje da ce se pokrenuti jednom po requestu
public class ExceptionHandlerFilter extends OncePerRequestFilter {

// Kreiramo klasu ExceptionHandlerFilter koja ce biti prvi filter koji ce biti pozvan i u ovoj klasi se radu unwrapp requesta klijenta za logovanje. U ovoj klasi je try-catch blok i u njemu se radu unwrapp pa ako je klijent prosledio objekat sa losim imenima property-a (nije username ili password) onda se iz catch-a salje response klijentu. Ako je request dobar onda se prosledjuje u sledeci filter.
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch(EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            // ako zelimo da ispisemo string u response-u onda se dodaje
            response.getWriter().write("message: Username doesn't exists");
            response.getWriter().flush();
        } catch (JWTVerificationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("message: JWT NOT VALID");
            response.getWriter().flush();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            // ako je request los onda se salje odgovarajuci HttpStatus code.
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            // ako zelimo da ispisemo string u response-u onda se dodaje
            response.getWriter().write("message: 400 - BAD REQUEST");
            response.getWriter().flush();
        }
    }
}
