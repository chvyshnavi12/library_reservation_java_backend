package com.login.loginpage.controller;

import com.login.loginpage.vo.Booking;
import jakarta.servlet.http.HttpSession;
import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.login.loginpage.model.User;
import com.login.loginpage.repo.UserRepo;
import com.login.loginpage.vo.LoginRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String frontPage()
    {
        return "frontpage"; // returns front.html
    }

    @GetMapping("/loginpage")
    public String getLoginPage(Model model)
    {
        model.addAttribute("loginRequest", new LoginRequest());
        return "loginpage";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        userRepo.save(user);
        return "redirect:/loginpage";  // back to login
    }

    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute LoginRequest loginRequest,
                            Model model,
                            HttpSession session) {
        User user = userRepo.findByUsernameAndPassword(
                loginRequest.getUsername(), loginRequest.getPassword());

        if (user != null) {
            session.setAttribute("loggedInUser", user);
            model.addAttribute("user", user);
            return "home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "loginpage";
        }
    }

    @GetMapping("/home")
    public String getHomePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user != null) {
            model.addAttribute("user", user);
            return "home";
        } else {
            return "redirect:/"; // if session expired
        }
    }

    @GetMapping("/aboutpage")
    public String getAboutPage() {
        return "aboutpage";
    }

    @GetMapping("/seatreservation")
    public String getSeatReservationPage() {
        return "seatreservation";
    }

    @GetMapping("/book")
    public String getBookPage() {
        return "book";
    }

    @GetMapping("/seatavailable")
    public String getSeatAvailablePage() {
        return "seatavailable";
    }

    @PostMapping("/reserveSeats")
    public String reserveSeats(@RequestParam("floor") String floor,
                               @RequestParam("seats") List<String> seats,
                               HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }

        Booking booking = new Booking();
        booking.setFloorNumber(Integer.parseInt(floor));
        booking.setSeats(seats);
        booking.setTotalSeats(seats.size());
        booking.setTime(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy, hh:mm a")));

        // ✅ store booking in session
        session.setAttribute("booking", booking);

        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    public String getConfirmationPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        Booking booking = (Booking) session.getAttribute("booking");

        if (user == null || booking == null) {
            return "redirect:/";
        }

        model.addAttribute("user", user);
        model.addAttribute("booking", booking);

        return "confirmation";
    }





}
