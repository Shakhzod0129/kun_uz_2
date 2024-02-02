package uz.kun.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailSenderService {
    @Value("${my.eskiz.uz.email}")
    private String email;
    @Value("${my.eskiz.uz.password}")
    private String password;
    @Value("${sms.fly.uz.url}")
    private String url;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.gmail.username")
    private String fromAccount;

    public void sendEmail(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public String getTokenWithAuthorization() {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .build();
        Request request = new Request.Builder()
                .url( url +"/api/auth/login")
                .method("POST", body)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException();
            } else {
                JSONObject object = new JSONObject(response.body().string());
                JSONObject data = object.getJSONObject("data");
                Object token = data.get("token");
                return token.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }


    public void sendSmsHTTP(String phone, String text) {
        String token = "Bearer " + getTokenWithAuthorization();

        OkHttpClient client = new OkHttpClient();

        if (phone.startsWith("+")) {
            phone = phone.substring(1);
        }

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("mobile_phone", phone)
                .addFormDataPart("message", text)
                .addFormDataPart("from", "4546")
                .build();

        Request request = new Request.Builder()
                .url(url + "/api/message/sms/send")
                .method("POST", body)
                .header("Authorization", token)
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*Thread thread = new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    System.out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();*/
    }

    public void send(String phone, String text, String code) {
        // create sms history
        sendSmsHTTP(phone, text + code);
    }


}
