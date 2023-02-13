package com.ltp.gradesubmission.repository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ltp.gradesubmission.entity.Grade;

@Repository
public interface GradeRepository extends CrudRepository<Grade, Long> {

    Grade findByStudentIdAndCourseId(Long studentId, Long courseId);
    List<Grade> findAll();
    
/*
    // kreira se lista Grade objekata - ova lista ce se setovati modelu kao atribut i sa modelom ce se poslati na view
    // ova lista ce se popuniti samo jednom i to kada se inicijalizuje klasa GradeController. Kasnije kada se uradi refresh stranice u browseru pozvace se metoda getGrades() ali klasa GradeController je vec inicijalizovana pa se lista nece ponovo kreirati
    // kada se pokrene aplikacija instancira se ova klasa i kreira se lista stdentGrades. Kada se uradi submit forme poziva se submitGrade() i grade se dodaje u listu. Taj dodati grade ce ostati u listi dok aplikacija radi - dok postoji instanca klase GradeController postojace i property/lista studentGrades i dodati grase u njoj. 
    private List<Grade> studentGrades = new ArrayList<>();

    // metoda koja na osnovu indeksa iz liste vraca Grade objekat
    public Grade getGrade(int index){
        return studentGrades.get(index);
    }

    // metoda koja vraca listu svih Grade objekata
    public List<Grade> getGrades(){
        return studentGrades;
    }

    // metoda koja dodaje Grade objekat u listu
    public void addGrade(Grade grade){
        grade.setId( (long) (studentGrades.size() + 1));
        studentGrades.add(grade);
    }

    // metoda koja radu update Grade objekta i to tako sto na mesto starog objekta setuje novi. Prosledjuje se index i na mestu tog index-a se upisuje novi Grade objekat - preko starog
    public void updateGrade(int index, Grade newGrade){
        studentGrades.set(index, newGrade);
    }

    public void deleteGrade(int index) {
        studentGrades.remove(index);
    }
 */
}
