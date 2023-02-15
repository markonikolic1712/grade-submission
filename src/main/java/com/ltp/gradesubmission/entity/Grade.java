package com.ltp.gradesubmission.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import com.ltp.gradesubmission.Score;
import lombok.*;

// na tabelu se dodaje unique constraint da kombinacija podataka student_id i course_id mora da bude jedinstvena 
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "grade", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
public class Grade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    //@NotBlank(message = "Name cannot be blank")
    // @Column(name = "subject")
    // private String subject;

    @Score(message = "Score must be a letter grade") // ovo je poruka koju ce BindingResult preneti do thymeleaf-a
    @Column(name = "score", nullable = false)
    private String score;

    // Svaki student moze imati vise ocena pa u grades tabeli moze da se nadje vise redova sa istim student_id. U tabeli grades student_id je foreign key koji referencira kolonu students.id
    // ovo je ManyToOne relacija i znaci da jedan Student ima vide grade
    // u @ManyToOne relacijama child tabela radi sa FK
    // Many grades will be associated with one student
    @ManyToOne(optional = false) // nije opcioni tj. podatak student je obavezan za svaki grade. (optional = false) je runtime instrukcija pa ako kontroler posalje grade bez studenta ovo ce blokirati taj zahtev pre nego sto posalje zahtev u vazu
    @JoinColumn(name = "student_id", referencedColumnName = "id") // grade FK kolona grade.student_id referencira kolonu student.id
    private Student student;

    // jedan course_id moze vise puta da se nadje u grade tabeli
    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    // field student referencira student red iz tabele students pa za grade imamo podatak:
    // ova ocena je A+ i pripada studentu ciji id je 3
    /*
     {
        "id": 1,
        "subject": null,
        "score": "A+",
        "student": {
            "id": 3,
            "name": "Hermione Granger",
            "birthDate": "1979-09-19"
        }
    }
     */
}
