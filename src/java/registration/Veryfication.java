package registration;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Veryfication {
    
    // our email is: 
    // password: nadkadnick
    // to get rid of errors: add javax.mail-1.6-2.jar
    
    final static String username = "authen.elect@gmail.com";
    final static String password = "nadkadnick";
    
    
    
    // returns a generated number that was send to user
    // user needs to copy it in the text field
    
    
    public static int sendVeryficationEmail(String emailTo) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

                int verification = (int) (Math.random() * 10000000) + 10000000;
		try {                    
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
                        
                        
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emailTo));
			message.setSubject("Email Verification ~ Redistricting");
			message.setText("Thank you for showing interest in the future of the country! :)"
				+ "\n\n Verification number: " + verification);

			Transport.send(message);

		} catch (MessagingException e) {
                    return 0;
                }
                
                return verification;
    
    
    }
    
}
