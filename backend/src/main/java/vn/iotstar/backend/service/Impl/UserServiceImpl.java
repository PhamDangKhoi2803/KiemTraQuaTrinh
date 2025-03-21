package vn.iotstar.backend.service.Impl;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import vn.iotstar.backend.Util.EmailUtil;
import vn.iotstar.backend.Util.OtpUtil;
import vn.iotstar.backend.entity.User;
import vn.iotstar.backend.model.LoginModel;
import vn.iotstar.backend.model.RegisterModel;
import vn.iotstar.backend.repository.UserRepository;
import vn.iotstar.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpUtil optUtil;

    @Autowired
    private EmailUtil emailUtil;


    @Override
    public String register(RegisterModel registerModel) {
        User userEx = userRepository.findUsersByUsername(registerModel.getUsername());
        if(userEx != null) {
            return "Username is already in use";
        }

        // Kiểm tra nếu đã tồn tại user với email đó và đã active
        User userE = userRepository.findUsersByEmail(registerModel.getEmail());
        if(userE != null && userE.isActive()) {
            return "Email existed!!!";
        }

        String otp = optUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(registerModel.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        User user = new User();
        user.setUsername(registerModel.getUsername());
        user.setEmail(registerModel.getEmail());
        user.setPassword(registerModel.getPassword());
        user.setOtp(otp);
        user.setOptGeneratedTime(LocalDateTime.now());

        // Kiểm tra nếu user đã đăng ký nhưng chưa active thì chỉ update thông tin cho user đó
        if(userE != null && !userE.isActive()){
            updateUser(user);
        }
        else{
            userRepository.save(user);
        }
        return "User registration successful";
    }

    @Override
    public String verifyAccount(String email, String otp) {

        User user = userRepository.findUsersByEmail(email);
        if(user == null) {
            return "Email not existed!!!";
        }

        if(user.getOtp().equals(otp) && Duration.between(user.getOptGeneratedTime(), LocalDateTime.now()).getSeconds() < (1 * 60)) {
            user.setActive(true);
            userRepository.save(user);
            return "OTP verified. You can login";
        }
        else if(!user.getOtp().equals(otp))
        {
            return "Invalid OTP. Please check and try again.";
        }
        return "OTP has expired. Please regenerate and try again.";

    }

    @Override
    public String regenerateOtp(String email) {
        User user = userRepository.findUsersByEmail(email);
        if(user == null) {
            return "Email not existed!!!";
        }

        String otp = optUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOptGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent ... please verify account within 1 minute";

    }

    @Override
    public String login(LoginModel loginModel) {
        User user = userRepository.findByUsername(loginModel.getUsername());
        if (user == null) {
            return "Invalid username or password!!!";
        }
        String password = user.getPassword();

        if(!user.getPassword().equals(password)) {
            return "Invalid username or password!!!";
        }
        else if(!user.isActive()) {
            return "Your account is not verified. Register again!";
        }
        else {
            return "Login Success!";
        }

    }

    public void updateUser(User user) {
        User existing = userRepository.findUsersByEmail(user.getEmail());
        existing.setUsername(user.getUsername());
        existing.setPassword(user.getPassword());
        existing.setOtp(user.getOtp());
        existing.setOptGeneratedTime(LocalDateTime.now());
        userRepository.save(existing);
    }
}

