package demo1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import demo1.entity.Student;
import demo1.repo.StudentRepository; 

@RestController
@RequestMapping("/api")
public class Studentcontroller {

	private final StudentRepository studentRepository;

	public Studentcontroller(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;

	}

 
	@GetMapping("/student")
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
//get by id
	@GetMapping("/student/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
		return studentRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	//create id
	@PostMapping("/student/create")
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

   
//param
    @GetMapping("/students")
    public ResponseEntity<Student> getStudentByParam(@RequestParam Long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        }

    //update
    @PutMapping("/student/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setFirstName(studentDetails.getFirstName());
                    existingStudent.setLastName(studentDetails.getLastName());
                    existingStudent.setEmail(studentDetails.getEmail());
                    existingStudent.setIsActive(studentDetails.isIsActive());
                    Student updated = studentRepository.save(existingStudent);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    //isActive  true false param
    @GetMapping("/student/filter")
    public List<Student> getStudentsByActiveStatus(@RequestParam(required = false) Boolean isActive) {
        if (isActive == null) {
            return studentRepository.findAll(); // No filter applied
        } else {
            return studentRepository.findByIsActive(isActive);
        }
    }


    // Soft delete (deactivate) student
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Student> softDeleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setIsActive(false);
                    Student updated = studentRepository.save(student);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
          }
}

