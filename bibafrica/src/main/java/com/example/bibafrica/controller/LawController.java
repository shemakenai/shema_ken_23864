package com.example.bibafrica.controller;

import com.example.bibafrica.model.Lawyer;
import com.example.bibafrica.securityconfig.EmailSenderServiceConfig;
import com.example.bibafrica.services.LawyerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class LawController {
    @Autowired
    private LawyerInterface studentService;
    @Autowired
    private EmailSenderServiceConfig emailSenderServiceConfig;

    @GetMapping("/home")
    public String homePage(Model model) {

        return findPaginated(1, model);
    }

    @GetMapping("/inde")
    public String ind() {
        return "homes";
    }

    @GetMapping("/")
    public String homme() {
        return "aboutus";
    }

    @GetMapping("/come")
    public String homes() {
        return "aboutus";
    }

    @GetMapping("/lawyer")
    public String candidate() {
        return "package";
    }

    @GetMapping("/tem")
    public String temp() {
        return "templa";
    }

    @GetMapping("/registration1")
    public String registerStudentPage(Model model) {
        Lawyer stud = new Lawyer();
        model.addAttribute("student", stud);
        return "registrar";
    }

    @GetMapping("/student-page")
    public String studentpage(Model model) {
        Lawyer stud = new Lawyer();
        model.addAttribute("student", stud);
        return "student";
    }

    @PostMapping("/register")
    public String registerLawyer(@ModelAttribute("student") Lawyer theStudent) throws MessagingException {
        Lawyer savedStudent = studentService.registerStudent(theStudent);
        if (savedStudent != null) {
            emailSenderServiceConfig.sendCitizenEmail(theStudent.getEmails(), "REGISTRATION", theStudent.getNames());
            return "redirect:/student-page?success";
        } else {
            return "redirect:/student-page?error";
        }
    }

    @PostMapping("/reg")
    public String registerStudentInDb(@ModelAttribute("student") Lawyer theStudent) throws MessagingException {
        Lawyer savedStudent = studentService.registerStudent(theStudent);
        if (savedStudent != null) {
            emailSenderServiceConfig.sendCitizenEmail(theStudent.getEmails(), "REGISTRATION", theStudent.getNames());

            return "redirect:/registration1?success";
        } else {
            return "redirect:/registration1?error";
        }
    }

    @GetMapping("/home/update/{id}")
    public String editStudent(@PathVariable String id, Model model) {

        Long lawyerId = Long.parseLong(id);
        model.addAttribute("student", studentService.findStudentByStudentId(lawyerId));
        return "update";
    }

    @PostMapping("/home/{id}")
    public String updateStudent(@PathVariable String id,
            @ModelAttribute("student") Lawyer student, Model model) {

        Long lawyerId = Long.parseLong(id);
        Lawyer existingStudent = studentService.findStudentByStudentId(lawyerId);
        existingStudent.setTel(student.getTel());
        existingStudent.setId(student.getId());
        existingStudent.setNames(student.getNames());
        existingStudent.setCases(student.getCases());
        existingStudent.setDate(student.getDate());
        existingStudent.setEmails(student.getEmails());
        existingStudent.setLawyer(student.getLawyer());
        studentService.updateStudent(existingStudent);
        return "redirect:/home";
    }

    @GetMapping("/home/{id}")
    public String deleteStudent(@PathVariable String id) {
        Long lawyerId = Long.parseLong(id);
        studentService.deleteStudent(lawyerId);
        return "redirect:/home";
    }

    @GetMapping("/sear")
    public String search(Model model) {
        Lawyer student = new Lawyer();
        model.addAttribute("search", student);

        return "search";
    }

    @PostMapping("/sear")
    public String searchh(@ModelAttribute("search") Lawyer student, Model model) {
        Lawyer student1 = studentService.findStudentByStudentId(student.getId());
        if (student1 != null) {
            model.addAttribute("student1", student1);
            return "search";
        } else {
            model.addAttribute("error", "this person is not exist");
            return "search";
        }
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<Lawyer> page = studentService.pagenateStudent(pageNo, pageSize);
        List<Lawyer> studentList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listStudents", studentList);
        return "home-page";

    }

}
