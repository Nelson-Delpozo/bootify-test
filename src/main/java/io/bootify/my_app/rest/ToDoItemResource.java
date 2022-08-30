package io.bootify.my_app.rest;

import io.bootify.my_app.model.ToDoItemDTO;
import io.bootify.my_app.service.ToDoItemService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/toDoItems", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToDoItemResource {

    private final ToDoItemService toDoItemService;

    public ToDoItemResource(final ToDoItemService toDoItemService) {
        this.toDoItemService = toDoItemService;
    }

    @GetMapping
    public ResponseEntity<List<ToDoItemDTO>> getAllToDoItems() {
        return ResponseEntity.ok(toDoItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoItemDTO> getToDoItem(@PathVariable final Long id) {
        return ResponseEntity.ok(toDoItemService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createToDoItem(@RequestBody @Valid final ToDoItemDTO toDoItemDTO) {
        return new ResponseEntity<>(toDoItemService.create(toDoItemDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateToDoItem(@PathVariable final Long id,
            @RequestBody @Valid final ToDoItemDTO toDoItemDTO) {
        toDoItemService.update(id, toDoItemDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteToDoItem(@PathVariable final Long id) {
        toDoItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
