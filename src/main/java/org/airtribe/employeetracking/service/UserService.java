package org.airtribe.employeetracking.service;

import org.airtribe.employeetracking.entity.User;
import org.airtribe.employeetracking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
@Autowired
private UserRepository userRepository;

    public String getRoleByEmail(String email) {
        // Find the user by email
        User user = userRepository.findByEmail(email);
        return user != null ? user.getRole().name() : null;
    }


}
