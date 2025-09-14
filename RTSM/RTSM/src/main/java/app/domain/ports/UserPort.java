package app.domain.ports;

import app.domain.model.User;
import java.util.List;

public interface UserPort {
    void save(User user) throws Exception;
    User findByUserName(String userName);
    User findByDocument(Long document);
    List<User> findAll();
    void delete(User user) throws Exception;
    void update(User user) throws Exception; 
}
