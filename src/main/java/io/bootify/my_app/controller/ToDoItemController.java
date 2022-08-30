package io.bootify.my_app.controller;

import io.bootify.my_app.model.ToDoItemDTO;
import io.bootify.my_app.service.ToDoItemService;
import io.bootify.my_app.util.WebUtils;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/toDoItems")
public class ToDoItemController {

    private final ToDoItemService toDoItemService;

    public ToDoItemController(final ToDoItemService toDoItemService) {
        this.toDoItemService = toDoItemService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("toDoItems", toDoItemService.findAll());
        return "toDoItem/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("toDoItem") final ToDoItemDTO toDoItemDTO) {
        return "toDoItem/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("toDoItem") @Valid final ToDoItemDTO toDoItemDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("name") && toDoItemService.nameExists(toDoItemDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.toDoItem.name");
        }
        if (bindingResult.hasErrors()) {
            return "toDoItem/add";
        }
        toDoItemService.create(toDoItemDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("toDoItem.create.success"));
        return "redirect:/toDoItems";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("toDoItem", toDoItemService.get(id));
        return "toDoItem/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("toDoItem") @Valid final ToDoItemDTO toDoItemDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("name") &&
                !toDoItemService.get(id).getName().equalsIgnoreCase(toDoItemDTO.getName()) &&
                toDoItemService.nameExists(toDoItemDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.toDoItem.name");
        }
        if (bindingResult.hasErrors()) {
            return "toDoItem/edit";
        }
        toDoItemService.update(id, toDoItemDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("toDoItem.update.success"));
        return "redirect:/toDoItems";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        toDoItemService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("toDoItem.delete.success"));
        return "redirect:/toDoItems";
    }

}
