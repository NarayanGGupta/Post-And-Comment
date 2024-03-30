package BlogApp.controller;

import BlogApp.entity.User;
import BlogApp.payload.LoginDto;
import BlogApp.payload.SignUp;
import BlogApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    // http://localhost:8080/api/auth/sign-up
    @PostMapping("/sign-up")
    public ResponseEntity<String> createUser( @RequestBody SignUp signUp){
        if(userRepository.existsByEmail(signUp.getEmail())){
            return  new ResponseEntity<>("EmailId Is Already Registered!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(userRepository.existsByUsername(signUp.getUsername())){
            return  new ResponseEntity<>("Username Is Already Registered!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = new User();

        user.setId(signUp.getId());
        user.setName(signUp.getName());
        user.setEmail(signUp.getEmail());
        user.setUsername(signUp.getUsername());
        user.setPassword((passwordEncoder.encode(signUp.getPassword())));

        userRepository.save(user);

        return new ResponseEntity<>("User Is Registered Successfully!!",HttpStatus.CREATED);

    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> createSignIn(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return new ResponseEntity<>("SignIn Successfully!!",HttpStatus.OK);
    }
}
