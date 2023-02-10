package com.ltp.gradesubmission.service;

import java.util.List;

import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.exception.GradeNotFounfException;

public interface GradeService {
    Grade getGrade(int index) throws GradeNotFounfException;
    List<Grade> getGrades();
    void addGrade(Grade grade);
    void updateGrade(int index, Grade newGrade) throws GradeNotFounfException;
    Grade getGradeById(String id) throws GradeNotFounfException;
    void submitGrade(Grade grade) throws GradeNotFounfException;
    void deleteGrade(String id) throws GradeNotFounfException;
}
