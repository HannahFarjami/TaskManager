package se.kth.ID1212.taskManagerRestAPI.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.ID1212.taskManagerRestAPI.domain.User;
import se.kth.ID1212.taskManagerRestAPI.repository.UserRepository;


/**
 * This class is responsible for serving the requests given from the presentation layer regarding users.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void createUser(User user){
        userRepository.save(user);
    }
}
