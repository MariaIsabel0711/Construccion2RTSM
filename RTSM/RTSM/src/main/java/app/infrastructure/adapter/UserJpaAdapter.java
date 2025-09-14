package app.infrastructure.adapter;

import app.domain.model.User;
import app.domain.ports.UserPort;
import app.infrastructure.entity.UserEntity;
import app.infrastructure.mapper.UserMapper;
import app.infrastructure.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserJpaAdapter implements UserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserJpaAdapter(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) throws Exception {
        UserEntity entity = userMapper.toEntity(user);
        userJpaRepository.save(entity);
    }

    @Override
    public User findByUserName(String userName) {
        UserEntity entity = userJpaRepository.findByUserName(userName);
        return userMapper.toDomain(entity);
    }

    @Override
    public User findByDocument(Long document) {
        UserEntity entity = userJpaRepository.findByDocument(document);
        return userMapper.toDomain(entity);
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> entities = userJpaRepository.findAll();
        return entities.stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(User user) throws Exception {
        UserEntity entity = userMapper.toEntity(user);
        userJpaRepository.delete(entity);
    }

    @Override
    public void update(User user) throws Exception {
        save(user);
    }
}
