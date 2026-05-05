package com.example.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendNewEmployeeCredentials(String toEmail, String name, String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Thông tin tài khoản đăng nhập hệ thống");
        message.setText("Xin chào " + name + ",\n\n" +
                "Tài khoản của bạn đã được tạo thành công trên hệ thống Quản lý Trại Lợn.\n\n" +
                "Dưới đây là thông tin đăng nhập của bạn:\n" +
                "Tên đăng nhập: " + username + "\n" +
                "Mật khẩu: " + password + "\n\n" +
                "Vui lòng đổi mật khẩu sau khi đăng nhập.\n\n" +
                "Trân trọng,\nBan Quản Trị Hệ Thống.");
        javaMailSender.send(message);
    }

    public void sendPasswordResetOtp(String toEmail, String name, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Mã xác nhận khôi phục mật khẩu - Quản lý Trại Lợn");
        message.setText("Xin chào " + name + ",\n\n" +
                "Bạn đã yêu cầu khôi phục mật khẩu trên hệ thống Quản lý Trại Lợn.\n\n" +
                "Mã xác nhận (OTP) của bạn là: " + otpCode + "\n\n" +
                "Vui lòng nhập mã này trên trang đăng nhập để đặt lại mật khẩu mới. " +
                "Nếu bạn không yêu cầu thao tác này, vui lòng bỏ qua email này.\n\n" +
                "Trân trọng,\nBan Quản Trị Hệ Thống.");
        javaMailSender.send(message);
    }
}
