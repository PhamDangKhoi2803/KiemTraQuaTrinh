package vn.iotstar.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.backend.DTO.RegisterRequest;
import vn.iotstar.backend.DTO.OtpRequest;
import vn.iotstar.backend.entity.User;
import vn.iotstar.backend.repository.UserRepository;
import vn.iotstar.backend.service.OtpService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserRepository userRepository;
    private final OtpService otpService;

    public AuthController(UserRepository userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        System.out.println("Email đăng ký: " + request.getEmail()); // Log kiểm tra

        // Kiểm tra nếu email đã được kích hoạt trong database
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent() && existingUser.get().isActivated()) {
            return ResponseEntity.badRequest().body("Email đã tồn tại");
        }

        // Gửi OTP mà không lưu user vào database
        otpService.generateOtp(request.getEmail());
        return ResponseEntity.ok("OTP đã được gửi qua email!");
    }



    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {
        if (!otpService.validateOtp(request.getEmail(), request.getOtp())) {
            return ResponseEntity.badRequest().body("OTP không hợp lệ.");
        }

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword()); // Lưu mật khẩu thật
        }

        user.setActivated(true);
        userRepository.save(user);
        otpService.removeOtp(request.getEmail());

        return ResponseEntity.ok("Xác thực thành công! Hãy đăng nhập.");
    }


}
