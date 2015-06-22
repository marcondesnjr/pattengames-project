
package org.bitbucket.marcondesads.patterngames.api.modelo;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;

/**
 * Classe de envio de emails
 * @author José Marcondes do Nascimento Junior
 */
public class EmailSender {
    
    public static void sender(final Cliente to, final String msg, final String titulo){
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                Email email = new Email();
                email.setFromAddress("PatternGames", "patterngames3@gmail.com");
                email.addRecipient(to.getNome(), to.getEmail(), Message.RecipientType.TO);
                email.setSubject(titulo);
                email.setText(msg);
                Mailer m = new Mailer("smtp.gmail.com",465,"patterngames3@gmail.com","umasenha");
                m.sendMail(email);
            }
        }, "EmailSend");
        t.run();
        
    }
    
    
}
