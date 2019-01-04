package se.kth.ID1212.taskManagerRestAPI.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.ID1212.taskManagerRestAPI.domain.Task;


import java.time.LocalDate;
import java.util.List;


/**
 * Interface for making database calls.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TaskRepository extends JpaRepository<Task,Long> {


    @Query("SELECT t FROM Task t WHERE t.dueDate >= ?1 AND t.dueDate <= ?2 AND t.userId = ?3")
    List<Task> findAllBetweenDueDate(LocalDate startDate, LocalDate endDate,Long userId);

    @Query("SELECT t FROM Task t WHERE t.dueDate >= ?1 AND t.dueDate <= ?2 AND t.isDone=?3 AND t.userId = ?4")
    List<Task> findAllBetweenDueDateAndIsDone(LocalDate startDate, LocalDate endDate,Boolean isDone,Long userId);

    Task findByIdAndUserId(Long id,Long userId);

    void deleteByIdAndUserId(Long id,Long userId);

}
