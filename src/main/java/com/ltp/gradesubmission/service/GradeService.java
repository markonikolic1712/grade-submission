package com.ltp.gradesubmission.service;

import java.util.List;

import com.ltp.gradesubmission.Constants;
import com.ltp.gradesubmission.Grade;
import com.ltp.gradesubmission.repository.GradeRepository;

public class GradeService {
    GradeRepository gradeRepository = new GradeRepository();

    // metoda koja na osnovu indeksa iz liste vraca Grade objekat
    public Grade getGrade(int index){
        return gradeRepository.getGrade(index);
    }

    // metoda koja vraca listu svih Grade objekata
    public List<Grade> getGrades(){
        return gradeRepository.getGrades();
    }

    // metoda koja dodaje Grade objekat u listu
    public void addGrade(Grade grade){
        gradeRepository.addGrade(grade);
    }

    // metoda koja radu update Grade objekta i to tako sto na mesto starog objekta setuje novi. Prosledjuje se index i na mestu tog index-a se upisuje novi Grade objekat - preko starog
    public void updateGrade(int index, Grade newGrade){
        gradeRepository.updateGrade(index, newGrade);
    }

    // poroverava se da li student sa tim imenom postoji u listi
    public Integer getGradeIndex(String id){
        for(int i = 0; i < getGrades().size(); i++){
            System.out.println("id: " + id + " | " + getGrades().get(i).getId());
            if(getGrades().get(i).getId().equals(id)) return i;
        }
        return Constants.NOT_FOUND;
    }

    public Grade getGradeById(String id){

        int index = getGradeIndex(id);

        // getGradeIndex(name) == Constants.NOT_FOUND ? new Grade() : studentGrades.get(getGradeIndex(name)) - proverava se da li student postoji u listi. Ako ne postoji onda se kreira prazan Grade objekat a ako postoji uzima se taj student iz liste (po indeksu studentGrades.get(i))
        return index == Constants.NOT_FOUND ? new Grade() : getGrade(index);
    }

    public void submitGrade(Grade grade){
                // prvo se proverava da li student postoji u listi i dodaje se samo ako vec nije u listi
                int index = getGradeIndex(grade.getId());
                if(index == Constants.NOT_FOUND) {
                    addGrade(grade);;
                } else {
                    // ako student postoji u listi preko postojeceg se upisuje novi - update-ovani
                    updateGrade(index, grade);
                }
    }
}
