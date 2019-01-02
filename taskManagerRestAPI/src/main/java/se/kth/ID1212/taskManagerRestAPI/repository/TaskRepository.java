package se.kth.ID1212.taskManagerRestAPI.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.ID1212.taskManagerRestAPI.domain.Task;


import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByDueDate(LocalDate dueDate);

    @Query("SELECT t FROM Task t WHERE t.dueDate != ?1")
    List<Task> findAllIsNotDueDate(LocalDate dueDate);

    @Query("SELECT t FROM Task t WHERE t.dueDate >= ?1 AND t.dueDate <= ?2")
    List<Task> findAllBetweenDueDate(LocalDate startDate, LocalDate endDate);

    @Query("SELECT t FROM Task t WHERE t.dueDate >= ?1 AND t.dueDate <= ?2 AND t.isDone=?3")
    List<Task> findAllBetweenDueDateAndIsDone(LocalDate startDate, LocalDate endDate,Boolean isDone);

}
