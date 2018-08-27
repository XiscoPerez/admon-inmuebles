package mx.com.admoninmuebles.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class CorreoServiceImpl implements CorreoService{
	
	@Autowired
    private JavaMailSender sender;
	
	@Autowired
	private TemplateEngine templateEngine;
 
    public String build(String nombre) {
        Context context = new Context();
        context.setVariable("nombre", nombre);
        return templateEngine.process("correoRecuperaContrasenia", context);
    }
	
	public String sendMail() {
	    MimeMessage message = sender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	
	    try {
	    	helper.setFrom("contacto_gesco@gesco-pls.com");
	        helper.setTo("fco_javiercarrillo@hotmail.com");
	        helper.setText(build("Paco"), true);
	        helper.setSubject("Mail From Spring Boot");
	        sender.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	        return "Error while sending mail ..";
	    } catch (MailException e) {
	    	e.printStackTrace();
	    	 return "Error while sending mail ..";
	    }
	    return "Mail Sent Success!";
	}

}
