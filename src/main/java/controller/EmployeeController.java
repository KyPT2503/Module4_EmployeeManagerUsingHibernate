package controller;

import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.employee.IEmployeeService;

@Controller
@RequestMapping("")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/all")
    public ModelAndView showIndex() {
        return new ModelAndView("index", "employees", employeeService.getAll());
    }

    @GetMapping("/create")
    public ModelAndView showCreate() {
        return new ModelAndView("create", "employee", new Employee());
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute("employee") Employee employee) {
        employeeService.add(employee);
        return new ModelAndView("redirect:/all");
    }

    @GetMapping("/update/{id}")
    public ModelAndView showUpdate(@PathVariable int id) {
        return new ModelAndView("create", "employee", employeeService.getById(id));
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute("employee") Employee employee) {
        employeeService.update(employee.getId(), employee);
        return new ModelAndView("redirect:/all");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDelete(@PathVariable int id) {
        return new ModelAndView("/delete", "employee", employeeService.getById(id));
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute("employee") Employee employee) {
        employeeService.remove(employee.getId());
        return new ModelAndView("redirect:/all");
    }
}
