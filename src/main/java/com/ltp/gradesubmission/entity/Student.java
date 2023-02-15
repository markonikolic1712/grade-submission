package com.ltp.gradesubmission.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor 
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name", nullable = false)
    private String name;
    
    @NonNull
    @Past(message = "The birth date must be in the past")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    // jedan objekat student moze biti vezan (imati listu) za vise grade objekata
    // mappedBy = "student" - ovime kazemo da je veza izmedju Student-a i Grade-a mapirana sa property-em "student" u grade objektu 
    // kada se ovako mapira veza izmedju Student-a i Grade--a nece se kreirati join tabela STUDENT_GARADE koju bi hibernate kreirao da nema ovog mapiranja
    // @JsonIgnore se dodaje jer ne zelimo da grades listu dodajemo u JSON. Svaki grade ima vezu sa studentom pa se za togstudenta uzima grade pa opet taj grade ima studenta i tako se ulazi u beskonacnu petlju. Zato se ovo iskljucuje iz rezultata i samo sluzi za Bidrectional vezu
    // cascade = CascadeType.ALL - ako se obrise student iz tabele student onda redovi u grade tabeli sa tim student_id nisu potrebni pa se i ono kaskacno brisu
    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL) 
    private List<Grade> grades;
}
