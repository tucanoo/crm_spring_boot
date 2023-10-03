package com.tucanoo.crm.services;

import com.tucanoo.crm.data.entities.User;
import com.tucanoo.crm.data.repositories.UserRepository;
import com.tucanoo.crm.data.specifications.UserDatatableFilter;
import com.tucanoo.crm.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<User> getUsersForDatatable(String queryString, Pageable pageable) {

        UserDatatableFilter userDatatableFilter = new UserDatatableFilter(queryString);

        return userRepository.findAll(userDatatableFilter, pageable);
    }

    public User createNewUser(UserDTO userDTO) {
        User user = User.builder()
            .fullName(userDTO.getFullName())
            .role(userDTO.getRole())
            .username(userDTO.getUsername())
            .password(passwordEncoder.encode(userDTO.getPassword()))
            .enabled(true)
            .build();

        return userRepository.save(user);
    }

    public User updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setEnabled(userDTO.getEnabled());
        user.setRole(userDTO.getRole());

        return userRepository.save(user);
    }
}
