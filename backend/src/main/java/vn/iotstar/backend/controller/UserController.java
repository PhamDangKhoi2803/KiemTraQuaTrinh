package vn.iotstar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.iotstar.backend.model.LoginModel;
import vn.iotstar.backend.model.RegisterModel;
import vn.iotstar.backend.service.UserService;

//Pham Ngoc Hoa 22110330
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginModel loginModel) {
        return new ResponseEntity<>(userService.login(loginModel),HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterModel registerModel){
        return new ResponseEntity<>(userService.register(registerModel),HttpStatus.OK);
    }

    // Sử dụng phương thức put: put để cập nhật 1 resource đã tồn tại trên server theo uri đã cung cấp và body kèm theo.
    // Cập nhật thuộc tính active của User từ 0 thành 1 nếu active thành công
    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String otp){
        return new ResponseEntity<>(userService.verifyAccount(email,otp),HttpStatus.OK);
    }

    // Cập nhật thuộc tính otp của User khi tạo lại otp mới
    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email){
        return new ResponseEntity<>(userService.regenerateOtp(email),HttpStatus.OK);
    }
}
