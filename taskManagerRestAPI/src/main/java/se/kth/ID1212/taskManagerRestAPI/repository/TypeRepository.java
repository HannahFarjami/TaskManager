package se.kth.ID1212.taskManagerRestAPI.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.ID1212.taskManagerRestAPI.domain.Category;


@Repository
public interface TypeRepository extends JpaRepository<Category,String> {
}
