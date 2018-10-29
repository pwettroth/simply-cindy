package com.simplycindy.controllers;

import com.simplycindy.models.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Controller
@RequestMapping("email")
public class ContactController {

    @Autowired
    public JavaMailSender emailSender;


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        // TODO Secure this password
        mailSender.setUsername("simply.cindy.decor@gmail.com");
        mailSender.setPassword("TEST123!");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return mailSender;
    }

    @RequestMapping(value = "contactForm", method = RequestMethod.GET)
    public String viewContactForm(Model model) {

        model.addAttribute("title", "Contact");
        model.addAttribute("email", new Email());
        return "contact/contactForm";
    }

    @RequestMapping(value = "contactForm", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String submitContactForm(@ModelAttribute Email newEmail, Model model) throws MessagingException {

        model.addAttribute("title", "Contact");

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.addAttachment("attached-image.jpg", newEmail.getImage());

        helper.setText("From: " +
                newEmail.getName() + " (" + newEmail.getEmail() + ")" +
                "<br/>" +
                newEmail.getMessage() +
                "<br/>", true);

        helper.setSubject("Customer Inquiry");
        helper.setTo("pwettroth@gmail.com");
        helper.setFrom(newEmail.getEmail());

        emailSender.send(message);

        return "redirect:/home";
    }
}
