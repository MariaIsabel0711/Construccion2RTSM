package app.infrastructure.mapper;

import app.domain.model.User;
import app.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        
        if (user.getId() != null && user.getId() > 0) {
            entity.setId(user.getId());
        }
        
        entity.setFullName(user.getFullName());
        entity.setDocument(user.getDocument());
        entity.setEmail(user.getEmail());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setDateOfBirth(user.getDateOfBirth());
        entity.setAddress(user.getAddress());
        entity.setGender(user.getGender());
        entity.setUserName(user.getUserName());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole()); 
        return entity;
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        User user = new User();
        
        user.setId(entity.getId());
        user.setFullName(entity.getFullName());
        user.setDocument(entity.getDocument());
        user.setEmail(entity.getEmail());
        user.setPhoneNumber(entity.getPhoneNumber());
        user.setDateOfBirth(entity.getDateOfBirth());
        user.setAddress(entity.getAddress());
        user.setGender(entity.getGender());
        user.setUserName(entity.getUserName());
        user.setPassword(entity.getPassword());
        user.setRole(entity.getRole()); 

        return user;
    }
}
