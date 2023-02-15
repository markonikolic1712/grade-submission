package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.GradeNotFoundException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;

import lombok.*;

// kada se na klasu stavi anotacija @AllArgsConstructor onda anotacije @Autowied sa repozitorijuma moze da se ukloni. Sa @AllArgsConstructor ce se kreirati konstruktor u kome ce GradeRepository i StudentRepository dependency biti inject-ovani kao paremetri 
@AllArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

    GradeRepository gradeRepository;
    StudentRepository studentRepository;
    CourseRepository courseRepository;

    @Override
    public Grade getGrade(Long studentId, Long courseId) {
        Optional<Grade> grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        return unwrapGrade(grade, studentId, courseId);
    }

    // kada se kreira/snima grade record podaci studentId i courseId se salju kao u url-u (@PathVariable) a score se salje kao json u body-u (@RequestBody)
    @Override
    public Grade saveGrade(Grade grade, Long studentId, Long courseId) throws GradeNotFoundException {
        // posto je student obavezan podatak prvo s euzima student po id-u a zatim se taj student setuje grade-u
        Student student = StudentServiceImpl.unwrapStudent(studentRepository.findById(studentId), studentId);
        Course course = CourseServiceImlp.unwrapCourse(courseRepository.findById(courseId), courseId);

        // pre nego sto se grade snimi moraju da mu se setuju student i course
        grade.setStudent(student);
        grade.setCourse(course);

        return gradeRepository.save(grade);
    }

    @Override
    public Grade updateGrade(String score, Long studentId, Long courseId) {
        Optional<Grade> grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        Grade unwrappedGrade = unwrapGrade(grade, studentId, courseId);
        // kada se radi update grade recorda prvo se dohvati grade koji postoji, zatim mu se menja score podatak i zatim se snima u bazu
        // posto ovaj grade vec postoji u bazi, kada se radi save() CrudRepository nece kreirati novi record nego ce uraditi update
        /*
         Optional<Grade> grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        if(grade.isPresent()){
            Grade unwrappedGrade = grade.get(); // uzima se grade/value iz optional-a
            unwrappedGrade.setScore(score); // update-uje se score
            return gradeRepository.save(unwrappedGrade); // snima se novi grade preko starog
        } else {
            throw new GradeNotFoundException(studentId, courseId);
        }
        */
        unwrappedGrade.setScore(score);
        return gradeRepository.save(unwrappedGrade);
    }

    @Override
    public void deleteGrade(Long studentId, Long courseId) {
        // uzima se grade sa trazenim studentId i courseId a zatim se proverava da li postoji. Ako ne postoji baca se izuzetak
        // ovo se obavlja u metodi unwrapGrade()
        Optional<Grade> grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        if(grade.isEmpty()) throw new GradeNotFoundException(studentId, courseId);

        gradeRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public List<Grade> getStudentGrades(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    @Override
    public List<Grade> getCourseGrades(Long courseId) {
        return gradeRepository.findByCourseId(courseId);
    }

    @Override
    public List<Grade> getAllGrades() {
        return (List<Grade>) gradeRepository.findAll();
    }

    static Grade unwrapGrade(Optional<Grade> entity, Long studentId, Long courseId) {
        if (entity.isPresent()) return entity.get();
        else throw new GradeNotFoundException(studentId, courseId);
    }


    /*
    // metoda koja na osnovu indeksa iz liste vraca Grade objekat
    public Grade getGrade(int index) {
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
    public void updateGrade(int index, Grade newGrade) {
        gradeRepository.updateGrade(index, newGrade);
    }

    // poroverava se da li student sa tim imenom postoji u listi
    public int getGradeIndex(Long id) {
        // for(int i = 0; i < getGrades().size(); i++){
        //     System.out.println("id: " + id + " | " + getGrades().get(i).getId());
        //     if(getGrades().get(i).getId().equals(id)) return i;
        // }
        // return Constants.NOT_FOUND;

        return IntStream.range(0, gradeRepository.getGrades().size())
            .filter(index -> gradeRepository.getGrades().get(index).getId() == id)
            .findFirst()
            .orElseThrow(() -> new GradeNotFounfException(id));
    }

    public Grade getGradeById(Long id) {
        int index = getGradeIndex(id);

        // getGradeIndex(name) == Constants.NOT_FOUND ? new Grade() : studentGrades.get(getGradeIndex(name)) - proverava se da li student postoji u listi. Ako ne postoji onda se kreira prazan Grade objekat a ako postoji uzima se taj student iz liste (po indeksu studentGrades.get(i))
        return index == Constants.NOT_FOUND ? new Grade() : getGrade(index);
    }

    public void submitGrade(Grade grade) {
                // prvo se proverava da li student postoji u listi i dodaje se samo ako vec nije u listi
                int index = getGradeIndex(grade.getId());
                if(index == Constants.NOT_FOUND) {
                    addGrade(grade);;
                } else {
                    // ako student postoji u listi preko postojeceg se upisuje novi - update-ovani
                    updateGrade(index, grade);
                }
    }

    @Override
    public void deleteGrade(Long id) throws GradeNotFounfException {
        int index = getGradeIndex(id);
        gradeRepository.deleteGrade(index);
    }
     */
}
