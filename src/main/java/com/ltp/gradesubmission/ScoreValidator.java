package com.ltp.gradesubmission;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// prvi parametar je anotacija za koju je logika u ovoj klasi a drugi je tip vrednosti koja se validira
// <Score, String> - validacija je @Score a tip podatka koji se validira je String
public class ScoreValidator implements ConstraintValidator <Score, String>  {

    // ovo je lista String vrednosti koja je prihvatljiva za score. Ako korisnik unese podatak koji postoji u listi onda se vraca true i podatak je validan - u suprotnom nije validan. 
    List<String> scores = Arrays.asList("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F");

    // ova metoda treba da vrati true ili false - ako je podatak validan ili nije 
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // proverava se da li uneti podatak postoji u listi
        for(String score : scores) {
            if(score.equals(value)) return true;
        }

        return false;
    }   
}
