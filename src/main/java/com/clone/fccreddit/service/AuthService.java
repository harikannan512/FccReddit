package com.clone.fccreddit.service;

import com.clone.fccreddit.dto.AuthenticationResponse;
import com.clone.fccreddit.dto.LoginRequest;
import com.clone.fccreddit.dto.RegisterRequest;
import com.clone.fccreddit.exceptions.SpringRedditException;
import com.clone.fccreddit.model.NotificationEmail;
import com.clone.fccreddit.model.User;
import com.clone.fccreddit.model.VerificationToken;
import com.clone.fccreddit.repository.UserRepository;
import com.clone.fccreddit.repository.VerificationRepository;
import com.clone.fccreddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

//    For Dependency Injection we can use field injection or constructor injection. But constructor
//    injection is generally recommended. As @AllArgsConstructor handles our constructor creation at
//    run time, it will also handle constructor injection.

    @Transactional // As we are interacting with a RDBMS here, we need to add @Transactional annotation
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);


        String token = generateVerificationToken(user);

        NotificationEmail notificationEmail = new NotificationEmail("Please Activate your account",
                user.getEmail(),
                "Thank you for signing up to Spring Reddit. " +
                        "To Activate your reddit account, please Click Here : " +
                        "http://localhost:8080/api/auth/accountVerification/" + token);

        mailService.sendMail(notificationEmail);
    }

    public String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationRepository.save(verificationToken);
        return token;
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User Not Found with the name :" + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));

        fetchUserAndEnable(verificationToken.get());
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
