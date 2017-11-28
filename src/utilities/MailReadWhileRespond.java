package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.search.FlagTerm;

    public class MailReadWhileRespond {
        Properties properties = null;
        private Session session = null;
        private Store store = null;
        private Folder inbox = null;
        private String userName = "qa.gaurav239@gmail.com";// provide user name
        private String password = "result1029";// provide password
        String activationUrl;

        public MailReadWhileRespond() {

        }

        public String readMails(String expectedSubject) {
            properties = new Properties();
            properties.setProperty("mail.host", "imap.gmail.com");
            properties.setProperty("mail.port", "995");
            properties.setProperty("mail.transport.protocol", "imaps");
            session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName, password);
                        }
                    });
            try {
                store = session.getStore("imaps");
                store.connect();
                inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_ONLY);
                Message messages[] = inbox.search(new FlagTerm(
                        new Flags(Flag.SEEN), false));
                ;
                System.out.println("Number of mails = " + messages.length);
                for (int i = 0; i < messages.length; i++)
                {
                    Message message = messages[i];
                    String subject = message.getSubject();
                    if (subject.equals(expectedSubject)) {
                        Address[] from = message.getFrom();
                        System.out.println("-------------------------------");
                        System.out.println("Date : " + message.getSentDate());
                        System.out.println("From : " + from[0]);
                        System.out.println("Subject: " + message.getSubject());
                        System.out.println("Content :");
                        processMessageBody(message);
                        System.out.println("--------------------------------");
                    }
                }
                inbox.close(true); store.close();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return activationUrl;
        }
        public void processMessageBody(Message message)
        {
            try {
                Object content = message.getContent();
                // check for string
                if(content instanceof String) {
                    //System.out.println(content);
                    String mailContent = (String) content;
                    activationUrl = mailContent.substring(
                            mailContent.indexOf("earliest.<br>")+13,
                            mailContent.indexOf("<br><br>Best regards"));
                    //System.out.println(activationUrl);

                }
                else if (content instanceof Multipart) {
                    Multipart multiPart = (Multipart) content;
                    procesMultiPart(multiPart);
                } else if (content instanceof InputStream) {
                    InputStream inStream = (InputStream) content;
                    int ch;
                    while ((ch = inStream.read()) != -1)
                    {
                        System.out.write(ch);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        public void procesMultiPart(Multipart content)
        {
            try {
                int multiPartCount = content.getCount();
                for (int i = 0; i < multiPartCount; i++) {
                    BodyPart bodyPart = content.getBodyPart(i);
                    Object o;
                    o = bodyPart.getContent();
                    if (o instanceof String)
                    {
                        System.out.println(o);
                    } else if (o instanceof Multipart) {
                        procesMultiPart((Multipart) o);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace(); }
        }
        public static void main(String[] args) {
            String expectedSubject = "[FromName] EKCS has a query My Test Project  412_2017-10-12";
            MailReadWhileRespond sample = new MailReadWhileRespond();
            String activationUrl = sample.readMails(expectedSubject);
            System.out.println(activationUrl);
        }
    }