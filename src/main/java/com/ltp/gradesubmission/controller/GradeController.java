package com.ltp.gradesubmission.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ltp.gradesubmission.Grade;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.service.GradeService;

@Controller
public class GradeController {


    
    @Autowired
    GradeService gradeService;

    @GetMapping("/grades")
    public String getGrades(Model model){
        model.addAttribute("grades", gradeService.getGrades());
        return "grades";
    }

    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) String id) {
        // getGradeIndex(name) == Constants.NOT_FOUND ? new Grade() : studentGrades.get(getGradeIndex(name)) - proverava se da li student postoji u listi. Ako ne postoji onda se kreira prazan Grade objekat a ako postoji uzima se taj student iz liste (po indeksu studentGrades.get(i))
        model.addAttribute("grade", gradeService.getGradeById(id));
        return "form";
    }

    @PostMapping("/handleSubmit")
    public String submitForm(@Valid Grade grade, BindingResult result){

        // proverava se da li je validacija podataka vratila da podaci imaju ili nemaju greske
        // ako ima losih podataka korisnik se vraca na formu a BindingResult ima u sebi objekat sa podacima i koji su podaci sa greskama. Ove greske se u thymeleaf uzimaju i prikazuju sa th:errors
        // Radi se return "form"; zato sto se salje BindingResult a kada bi se radio rediract BindingResult bi se izgubio.
        if(result.hasErrors()) return "form";

        // ovde se poziva metoda iz servisa i prosledjuje se Grade objekat a ta metoda ce proveriti da li tgreba da se doda novi Grade ili treba da se uradi update postojeceg
        gradeService.submitGrade(grade);

        // kada se uradi submit forme radi se redirect na /grades. Kada bi se radio samo return "grades" onda bi bila ucitana ta strana ali podaci ne bi bili poslati u modelu. Kada se radi redirect na /grades poziva se metoda getGrades() u model se dodaje artibut sa listom grade objekata i taj model se salje na view.
        return "redirect:/grades";
    }
}
