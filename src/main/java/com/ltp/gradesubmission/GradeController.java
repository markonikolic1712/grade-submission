package com.ltp.gradesubmission;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GradeController {

    // kreira se lista Grade objekata - ova lista ce se setovati modelu kao atribut i sa modelom ce se poslati na view
    // ova lista ce se popuniti samo jednom i to kada se inicijalizuje klasa GradeController. Kasnije kada se uradi refresh stranice u browseru pozvace se metoda getGrades() ali klasa GradeController je vec inicijalizovana pa se lista nece ponovo kreirati
    // kada se pokrene aplikacija instancira se ova klasa i kreira se lista stdentGrades. Kada se uradi submit forme poziva se submitGrade() i grade se dodaje u listu. Taj dodati grade ce ostati u listi dok aplikacija radi - dok postoji instanca klase GradeController postojace i property/lista studentGrades i dodati grase u njoj. 
    List<Grade> studentGrades = new ArrayList<>();

    @GetMapping("/grades")
    public String getGrades(Model model){
        model.addAttribute("grades", studentGrades);
        return "grades";
    }

    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) String id) {

        int index = getGradeIndex(id);

        // getGradeIndex(name) == Constants.NOT_FOUND ? new Grade() : studentGrades.get(getGradeIndex(name)) - proverava se da li student postoji u listi. Ako ne postoji onda se kreira prazan Grade objekat a ako postoji uzima se taj student iz liste (po indeksu studentGrades.get(i))
        model.addAttribute("grade", index == Constants.NOT_FOUND ? new Grade() : studentGrades.get(index));
        return "form";
    }

    @PostMapping("/handleSubmit")
    public String submitForm(@Valid Grade grade, BindingResult result){

        // proverava se da li je validacija podataka vratila da podaci imaju ili nemaju greske
        // ako ima losih podataka korisnik se vraca na formu a BindingResult ima u sebi objekat sa podacima i koji su podaci sa greskama. Ove greske se u thymeleaf uzimaju i prikazuju sa th:errors
        // Radi se return "form"; zato sto se salje BindingResult a kada bi se radio rediract BindingResult bi se izgubio.
        if(result.hasErrors()) return "form";

        // prvo se proverava da li student postoji u listi i dodaje se samo ako vec nije u listi
        int index = getGradeIndex(grade.getId());
        if(index == Constants.NOT_FOUND) {
            studentGrades.add(grade);
        } else {
            // ako student postoji u listi preko postojeceg se upisuje novi - update-ovani
            studentGrades.set(index, grade);
        }

        // kada se uradi submit forme radi se redirect na /grades. Kada bi se radio samo return "grades" onda bi bila ucitana ta strana ali podaci ne bi bili poslati u modelu. Kada se radi redirect na /grades poziva se metoda getGrades() u model se dodaje artibut sa listom grade objekata i taj model se salje na view.
        return "redirect:/grades";
    }

    // poroverava se da li student sa tim imenom postoji u listi
    public Integer getGradeIndex(String id){
        for(int i = 0; i < studentGrades.size(); i++){
            System.out.println("id: " + id + " | " + studentGrades.get(i).getId());
            if(studentGrades.get(i).getId().equals(id)) return i;
        }
        return Constants.NOT_FOUND;
    }
}
