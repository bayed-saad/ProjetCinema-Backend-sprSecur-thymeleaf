package org.sid.cine.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {
    @GetMapping(value = "/eror")
    public String erreur(){
        return "eror";
    }

    @GetMapping(value = "/login")
    public String login(){ 
        return "login";
    }
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/login";
    }

}
