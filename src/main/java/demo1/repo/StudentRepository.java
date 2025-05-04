package demo1.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo1.entity.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	List<Student> findByIsActive(boolean isActive);
}
