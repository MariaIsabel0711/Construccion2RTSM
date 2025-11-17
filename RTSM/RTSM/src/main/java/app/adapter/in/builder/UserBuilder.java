package app.adapter.in.builder;

import app.domain.model.User;
import app.domain.model.enums.Role;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserBuilder {
    
    public User build(String fullName, String document, String email, String phone, String dob,
                      String address, String gender, String userName, String password, Role role) {
        User u = new User();
        u.setFullName(fullName);
        u.setDocument(document);
        u.setEmail(email);
        u.setPhoneNumber(phone);
        u.setDateOfBirth(dob);
        u.setAddress(address);
        u.setGender(gender);
        u.setUserName(userName);
        u.setPassword(password);
        
        // Crear Set<Role> porque User tiene Set<Role>, no Role individual
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        u.setRoles(roles);
        
        return u;
    }

    public User applyPersonalDataUpdates(User u, String fullName, String email, String phone,
                                         String address, String dob, String gender) {
        if (fullName != null && !fullName.isEmpty()) u.setFullName(fullName);
        if (email != null && !email.isEmpty()) u.setEmail(email);
        if (phone != null && !phone.isEmpty()) u.setPhoneNumber(phone);
        if (address != null && !address.isEmpty()) u.setAddress(address);
        if (dob != null && !dob.isEmpty()) u.setDateOfBirth(dob);
        if (gender != null && !gender.isEmpty()) u.setGender(gender);
        return u;
    }
}
