package com.ltp.gradesubmission;

import com.ltp.gradesubmission.Grade;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.service.GradeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    // @InjectMocks anotacija kreira objekat gradeService i u njega inject-uje mock gradeRepository
    @InjectMocks
    GradeService gradeService;

    @Test
    public void getGradesFromRepoTest(){
        // Arrange deo
        // ova recenica dole opisuje sta treba da se uradi da se pripreme podaci za test - ovo je mock repository
        // "when the service calls gradeRepository.getGrades(), then it shoud return a List of grades"
        // kada se ova recenica gore prevede u kod imamo ovo dole
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
            new Grade("Harry", "Potions", "C-"),
            new Grade("Hermione", "Arithmancy", "A+")
        ));

        // Act deo
        // poziv metode koja se testira i setovanje rezultata u listu grades
        List<Grade> result = gradeService.getGrades();


        // Assert deo
        // provera da li metoda vraca ocekivani rezultat. Proverava se: expected vs actual
        assertEquals("Harry", result.get(0).getName());
        assertEquals("Potions", result.get(0).getSubject());
    }

    @Test
    public void getGradeIndexTest(){
        Grade grade = new Grade("Harry", "Potions", "C-");
        // Arrange deo
        // kreiraju se mock podaci koji se koriste za testiranje
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        // kreira se mock kada se trazi grade objekat - kada se trazi Grade objekat po index-u 0 onda vrati ovaj grade gore koji se kreira
        //when(gradeRepository.getGrade(0)).thenReturn(grade);

        // Act deo - poziva se metoda iz gradeService koja treba da pribavi index za prosledjeni id
        int valid = gradeService.getGradeIndex(grade.getId());
        int notFound = gradeService.getGradeIndex("123"); // radi testiranja ovde s eprosledjuje pogresan id
    
        // Assert deo - proverava se da li je dobavljeni index dobar
        assertEquals(0, valid);
        assertEquals(Constants.NOT_FOUND, notFound); // kada se metodi getGradeIndex() prosledi pogresan id ona treba da vrati konstantu NOT_FOUND
    }


    // test da li metoda vraca ispravan Grade kada se prosledi id
    @Test
    public void getGradeByIdTest(){
        Grade grade = new Grade("Harry", "Potions", "C-");
        // ovo dole su mock-ovi za repositorium. Ovime se imitira repositorijum tj. ono sto bi repozitorijum vratio kada bi ga service pozvao.
        // mora da se napravi mock svih metoda iz repozitorijuma koje service poziva
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);

        String id = grade.getId();
        Grade result = gradeService.getGradeById(id);

        // uporedjuje se ono sto je u repozitorijumu (mock objekat grade) i ono sto vraca service metoda koja se testira
        assertEquals(grade, result);
    }

    // proverava se da li se grade objekat dodaje - verify() treba da potvrdi da se to dogodilo jednom
    @Test 
    public void addGradeTest(){
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        //when(gradeRepository.getGrade(0)).thenReturn(grade);

        Grade newGrade = new Grade("Hermione", "Arithmancy", "A+");
        gradeService.submitGrade(newGrade);

        // 1. kreira lista - mock repozitorijuma
        // 2. u mock repozitorijuma se dodaje newGrade
        // 3. koristi se verify metoda. Ona proverava koliko se puta nesto desilo - ovde se proverava da li je metoda addGrade() u repozitorijumu (mock) pozvana jedan put
        verify(gradeRepository, times(1)).addGrade(newGrade);
    }

    @Test
    public void updateGradeTest(){
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        //when(gradeRepository.getGrade(0)).thenReturn(grade);

        grade.setScore("A-");
        gradeService.submitGrade(grade);
        // proverava se da li se repository metoda updateGrade() poziva kada se pozove gradeService.submitGrade(grade)
        verify(gradeRepository, times(1)).updateGrade(0, grade);
    }
}
