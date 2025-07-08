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
        helper.setSubject("Validação de Conta - TRUST");

        String html = """
        <!DOCTYPE html>
        <html lang="pt">
        <head>
            <meta charset="UTF-8">
            <style>
                .card {
                    background-color: #ffffff;
                    max-width: 600px;
                    margin: auto;
                    padding: 40px;
                    border-radius: 16px;
                    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                }
                h2 {
                    text-align: center;
                    color: #003781;
                    font-weight: bold;
                }
                p {
                    font-size: 15px;
                    color: #333;
                    line-height: 1.6;
                }
                .btn {
                    display: block;
                    width: fit-content;
                    margin: 30px auto;
                    padding: 12px 30px;
                    background-color: #34bcb2;
                    color: white;
                    text-decoration: none;
                    border-radius: 8px;
                    font-weight: bold;
                }
                ul {
                    margin-top: 16px;
                }
                .footer {
                    text-align: center;
                    margin-top: 40px;
                    color: #f0f0f0;
                    font-size: 12px;
                }
                .logo {
                    display: block;
                    margin: 0 auto 20px auto;
                    width: 100px;
                }
            </style>
        </head>
        <body style="margin:0; padding:0; background-color:#33c3b5;">
            <div style="padding: 40px 0;">
                <div class="card">
                    <h2>Validação de Conta</h2>
                    <p>Olá,</p>
                    <p>Obrigado por se registar na <strong>TRUST</strong>. A sua conta foi criada com sucesso.</p>
                    <p>Para finalizar o processo deve validar o seu email clicando no botão abaixo:</p>
                    <a href="%s" class="btn">Validar Email</a>
                    <p>Na TRUST pode:</p>
                    <ul>
                        <li>Participar em sinistros e consultar os dados a qualquer momento;</li>
                        <li>Receber notificações e lembretes personalizados;</li>
                        <li>Submeter pedidos de reembolso.</li>
                    </ul>
                    <p>Se não foi você que criou esta conta, por favor ignore este e-mail ou contacte-nos via <a href="mailto:servicosaocliente@trustsaude.pt">servicosaocliente@trustsaude.pt</a></p>
                    <p>Com os melhores cumprimentos,<br><strong>EQUIPA TRUST</strong></p>
                </div>
                <div class="footer">©2025 Trust - Gestão Integrada de Saúde</div>
            </div>
        </body>
        </html>
        """.formatted(confirmationLink);

        helper.setText(html, true);
        helper.setFrom("maistrustnoreply@trustsaude.pt");

        mailSender.send(message);
    }


    public void enviarEmailRecuperacao(String to, String resetLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Recuperação de Password - TRUST");

        String html = """
        <!DOCTYPE html>
        <html lang="pt">
        <head>
            <meta charset="UTF-8">
            <style>
                .card {
                    background-color: #ffffff;
                    max-width: 600px;
                    margin: auto;
                    padding: 40px;
                    border-radius: 16px;
                    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                }
                h2 {
                    text-align: center;
                    color: #003781;
                    font-weight: bold;
                }
                p {
                    font-size: 15px;
                    color: #333;
                    line-height: 1.6;
                }
                .btn {
                    display: block;
                    width: fit-content;
                    margin: 30px auto;
                    padding: 12px 30px;
                    background-color: #34bcb2;
                    color: white;
                    text-decoration: none;
                    border-radius: 8px;
                    font-weight: bold;
                }
                .footer {
                    text-align: center;
                    margin-top: 40px;
                    color: #f0f0f0;
                    font-size: 12px;
                }
                .logo {
                    display: block;
                    margin: 0 auto 20px auto;
                    width: 100px;
                }
            </style>
        </head>
        <body style="margin:0; padding:0; background-color:#33c3b5;">
            <div style="padding: 40px 0;">
                <div class="card">
                    <h2>Recuperação de Password</h2>
                    <p>Olá,</p>
                    <p>Recebemos um pedido para redefinir a sua password.</p>
                    <p>Clique no botão abaixo para criar uma nova password de acesso:</p>
                    <a href="%s" class="btn">Redefinir Password</a>
                    <p>Se não foi você que solicitou a alteração, pode ignorar este email com segurança.</p>
                    <p>Com os melhores cumprimentos,<br><strong>EQUIPA TRUST</strong></p>
                </div>
                <div class="footer">©2025 Trust - Gestão Integrada de Saúde</div>
            </div>
        </body>
        </html>
        """.formatted(resetLink);

        helper.setText(html, true);
        helper.setFrom("maistrustnoreply@trustsaude.pt");

        mailSender.send(message);
    }

}
