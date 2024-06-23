package com.homecooked.common.auth;

import com.homecooked.common.chef.dto.ChefCreateUpdateDto;
import com.homecooked.common.client.dto.ClientCreateUpdateDto;
import com.homecooked.common.constant.Role;
import com.homecooked.common.security.JwtResponse;
import com.homecooked.common.security.JwtService;
import com.homecooked.common.security.OtpService;
import com.homecooked.common.security.RoleContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;
    private final JwtService jwtService;
    private final OtpService otpService;

    @PostMapping("/{domain}/api/auth/signin")
    public Object signIn(@PathVariable String domain, @RequestBody AuthDto request) {
        return authenticationService.signIn(request, Role.fromDomain(domain));
    }

    @PostMapping("/{domain}/api/auth/chef/register")
    public Object chefRegister(@RequestBody ChefCreateUpdateDto request) {
        return authenticationService.registerChef(request);
    }

    @PostMapping("/{domain}/api/auth/client/register")
    public Object clientRegister(@RequestBody ClientCreateUpdateDto request) {
        return authenticationService.registerClient(request);
    }

    @PostMapping("/{domain}/api/auth/refresh")
    public JwtResponse refreshToken(@PathVariable String domain, @RequestBody JwtResponse body) throws Exception {
        RoleContext.setRoleHolder(Role.fromDomain(domain));
        return jwtService.refreshAccessToken(body);
    }

    @PostMapping("/{domain}/api/auth/send_otp_sms")
    public String sendOtpSMS(@PathVariable String domain, @RequestParam String phoneNumber) {
        otpService.sendOtpSMS(phoneNumber);
        return "OTP sent successfully.";
    }

    @PostMapping("/{domain}/api/auth/verify_otp")
    public String verifyOtp(@PathVariable String domain, @RequestParam String phoneNumber, @RequestParam String otp) {
        boolean isOtpValid = otpService.verifyOtp(phoneNumber, otp);
        if (isOtpValid) {
            return "OTP verified successfully";
        } else {
            throw new RuntimeException("Invalid OTP");
        }
    }
}
