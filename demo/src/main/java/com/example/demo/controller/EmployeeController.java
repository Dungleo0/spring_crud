package com.example.demo.controller;


import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee")
    public String index(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "list";
    }

    @GetMapping("/employee/create")
    public String create(Model model) {
        model.addAttribute("employee", new Employee());
        return "form";
    }

    @GetMapping("/employee/{id}/edit") // Added @PathVariable annotation
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("employee", employeeService.findOne(id));
        return "form";
    }

    @PostMapping("/employee/save")
    public String save(@Validated Employee employee, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "form";
        }
        employeeService.save(employee);
        redirect.addFlashAttribute("success", "Saved employee successfully!");
        return "redirect:/employee";
    }

    @GetMapping("/employee/{id}/delete") // Added @PathVariable annotation
    public String delete(@PathVariable int id, RedirectAttributes redirect) {
        employeeService.delete(id);
        redirect.addFlashAttribute("success", "Deleted   employee successfully!");
        return "redirect:/employee";
    }

    @GetMapping("/employee/search")
    public String search(@RequestParam("s") String s, Model model) {
        if (s.equals("")) {
            return "redirect:/employee";
        }
        model.addAttribute("employees", employeeService.search(s));
        return "list";
    }
}