package br.com.easypet.service;

import br.com.easypet.domain.entity.User;
import br.com.easypet.domain.entity.Vet;
import br.com.easypet.domain.enums.Role;
import br.com.easypet.dto.request.LoginRequest;
import br.com.easypet.dto.request.RegisterRequest;
import br.com.easypet.dto.request.VetRequest;
import br.com.easypet.dto.response.AuthResponse;
import br.com.easypet.exception.BusinessException;
import br.com.easypet.exception.ResourceNotFoundException;
import br.com.easypet.repository.UserRepository;
import br.com.easypet.repository.VetRepository;
import br.com.easypet.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final VetRepository vetRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole().name());
    }

    @Transactional
    public AuthResponse registerVet(RegisterRequest registerRequest, VetRequest vetRequest) {
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        if (vetRepository.existsByCrmv(vetRequest.crmv())) {
            throw new BusinessException("CRMV já cadastrado");
        }

        User user = User.builder()
                .name(registerRequest.name())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(Role.VET)
                .build();

        userRepository.save(user);

        Vet vet = Vet.builder()
                .name(registerRequest.name())
                .crmv(vetRequest.crmv())
                .specialty(vetRequest.specialty())
                .phone(vetRequest.phone())
                .active(true)
                .user(user)
                .build();

        vetRepository.save(vet);

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole().name());
    }
}