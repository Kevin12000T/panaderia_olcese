package com.olcese.panaderia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // Página de login
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // buscará templates/login.html
    }
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // templates/register.html
    }

    @GetMapping("/dashboard-admin")
    public String adminDashboard() {
        return "dashboard-admin";
    }

    @GetMapping("/dashboard-cliente")
    public String clienteDashboard() {
        return "dashboard-cliente";
    }
}
