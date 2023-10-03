package com.tucanoo.crm.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tucanoo.crm.data.entities.User;
import com.tucanoo.crm.data.repositories.UserRepository;
import com.tucanoo.crm.dto.UserDTO;
import com.tucanoo.crm.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserWebController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public String index() {
        return "/user/index.html";
    }

    @GetMapping(value = "/data_for_datatable", produces = "application/json")
    @ResponseBody
    public String getDataForDatatable(@RequestParam Map<String, Object> params) {
        int draw = params.containsKey("draw") ? Integer.parseInt(params.get("draw").toString()) : 1;
        int length = params.containsKey("length") ? Integer.parseInt(params.get("length").toString()) : 30;
        int start = params.containsKey("start") ? Integer.parseInt(params.get("start").toString()) : 30;
        int currentPage = start / length;

        String sortName = "id";
        String dataTableOrderColumnIdx = params.get("order[0][column]").toString();
        String dataTableOrderColumnName = "columns[" + dataTableOrderColumnIdx + "][data]";
        if (params.containsKey(dataTableOrderColumnName))
            sortName = params.get(dataTableOrderColumnName).toString();
        String sortDir = params.containsKey("order[0][dir]") ? params.get("order[0][dir]").toString() : "asc";

        Sort.Order sortOrder = new Sort.Order((sortDir.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC), sortName);
        Sort sort = Sort.by(sortOrder);

        Pageable pageRequest = PageRequest.of(currentPage,
            length,
            sort);

        String queryString = (String) (params.get("search[value]"));

        Page<User> users = userService.getUsersForDatatable(queryString, pageRequest);

        long totalRecords = users.getTotalElements();

        List<Map<String, Object>> cells = new ArrayList<>();
        users.forEach(user -> {
            Map<String, Object> cellData = new HashMap<>();
            cellData.put("id", user.getId());
            cellData.put("username", user.getUsername());
            cellData.put("fullName", user.getFullName());
            cellData.put("enabled", user.getEnabled());
            cellData.put("dateCreated", user.getDateCreated());
            cells.add(cellData);
        });

        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("draw", draw);
        jsonMap.put("recordsTotal", totalRecords);
        jsonMap.put("recordsFiltered", totalRecords);
        jsonMap.put("data", cells);

        String json = null;
        try {
            json = objectMapper.writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        User userInstance = userRepository.findById(Long.valueOf(id)).get();

        model.addAttribute("userInstance", userInstance);

        return "/user/edit.html";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("userInstance") UserDTO userDTO,
                         BindingResult bindingResult,
                         RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/user/edit.html";
        } else {
            if (userService.updateUser(userDTO) != null)
                atts.addFlashAttribute("message", "User updated successfully");
            else
                atts.addFlashAttribute("message", "User update failed.");

            return "redirect:/user";
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("userInstance", new User());
        return "/user/create.html";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("userInstance") UserDTO userDTO,
                       BindingResult bindingResult,
                       RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/user/create.html";
        } else {

            if (userService.createNewUser(userDTO) != null)
                atts.addFlashAttribute("message", "User created successfully");
            else
                atts.addFlashAttribute("message", "User creation failed.");

            return "redirect:/user";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes atts) {
        User userInstance = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User Not Found:" + id));

        userRepository.delete(userInstance);

        atts.addFlashAttribute("message", "User deleted.");

        return "redirect:/user";
    }

}
