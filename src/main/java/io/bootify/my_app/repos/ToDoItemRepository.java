package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {

    boolean existsByNameIgnoreCase(String name);

}
