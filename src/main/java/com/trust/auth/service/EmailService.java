package com.trust.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailConfirmacao(String to, String confirmationLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Confirmação de Conta - MaisTrust");
        helper.setText("<p>Olá!</p><p>Obrigado por se registar na MaisTrust.</p>" +
                "<p>Clique no link abaixo para confirmar a sua conta:</p>" +
                "<p><a href=\"" + confirmationLink + "\">Confirmar Conta</a></p>" +
                "<p>Se não foi você, ignore este email.</p>", true);
        helper.setFrom("maistrustnoreply@trustsaude.pt");

        mailSender.send(message);
    }

    public void enviarEmailRecuperacao(String to, String resetLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Recuperação de Password - MaisTrust");

        String corpoHtml = """
        <!DOCTYPE html>
        <html lang="pt">
        <body>
            <p>Olá!</p>
            <p>Recebemos um pedido de recuperação de password.</p>
            <p>Clique no link abaixo para criar uma nova password:</p>
            <p><a href="%s" style="color:#5A48E8;font-weight:bold;">Recuperar Password</a></p>
            <p>Se não foi você, ignore este email.</p>
        </body>
        </html>
        """.formatted(resetLink);

        helper.setText(corpoHtml, true); // ✅ HTML ativado
        helper.setFrom("maistrustnoreply@trustsaude.pt");

        mailSender.send(message);
    }






}
