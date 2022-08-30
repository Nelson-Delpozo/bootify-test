package io.bootify.my_app.service;

import io.bootify.my_app.domain.ToDoItem;
import io.bootify.my_app.model.ToDoItemDTO;
import io.bootify.my_app.repos.ToDoItemRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ToDoItemService {

    private final ToDoItemRepository toDoItemRepository;

    public ToDoItemService(final ToDoItemRepository toDoItemRepository) {
        this.toDoItemRepository = toDoItemRepository;
    }

    public List<ToDoItemDTO> findAll() {
        return toDoItemRepository.findAll(Sort.by("id"))
                .stream()
                .map(toDoItem -> mapToDTO(toDoItem, new ToDoItemDTO()))
                .collect(Collectors.toList());
    }

    public ToDoItemDTO get(final Long id) {
        return toDoItemRepository.findById(id)
                .map(toDoItem -> mapToDTO(toDoItem, new ToDoItemDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final ToDoItemDTO toDoItemDTO) {
        final ToDoItem toDoItem = new ToDoItem();
        mapToEntity(toDoItemDTO, toDoItem);
        return toDoItemRepository.save(toDoItem).getId();
    }

    public void update(final Long id, final ToDoItemDTO toDoItemDTO) {
        final ToDoItem toDoItem = toDoItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(toDoItemDTO, toDoItem);
        toDoItemRepository.save(toDoItem);
    }

    public void delete(final Long id) {
        toDoItemRepository.deleteById(id);
    }

    private ToDoItemDTO mapToDTO(final ToDoItem toDoItem, final ToDoItemDTO toDoItemDTO) {
        toDoItemDTO.setId(toDoItem.getId());
        toDoItemDTO.setName(toDoItem.getName());
        return toDoItemDTO;
    }

    private ToDoItem mapToEntity(final ToDoItemDTO toDoItemDTO, final ToDoItem toDoItem) {
        toDoItem.setName(toDoItemDTO.getName());
        return toDoItem;
    }

    public boolean nameExists(final String name) {
        return toDoItemRepository.existsByNameIgnoreCase(name);
    }

}
