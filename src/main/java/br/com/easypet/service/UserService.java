package br.com.easypet.service;

import br.com.easypet.domain.entity.User;
import br.com.easypet.dto.request.ChangePasswordRequest;
import br.com.easypet.dto.request.UpdateProfileRequest;
import br.com.easypet.dto.response.UserResponse;
import br.com.easypet.exception.BusinessException;
import br.com.easypet.exception.ResourceNotFoundException;
import br.com.easypet.repository.UserRepository;
import br.com.easypet.security.JwtService;
import br.com.easypet.security.TokenBlacklistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtService jwtService;

    public UserResponse getProfile() {
        return UserResponse.from(getAuthenticatedUser());
    }

    @Transactional
    public UserResponse updateProfile(UpdateProfileRequest request) {
        User user = getAuthenticatedUser();

        if (!user.getEmail().equals(request.email()) &&
                userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        user.setName(request.name());
        user.setEmail(request.email());

        return UserResponse.from(userRepository.save(user));
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        User user = getAuthenticatedUser();

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new BusinessException("Senha atual incorreta");
        }

        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new BusinessException("Nova senha e confirmação não conferem");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    private User getAuthenticatedUser(){
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public void logout(String token) {
        long expiration = jwtService.getExpirationTime(token);
        tokenBlacklistService.AddToBlackList(token, expiration);
    }

}
