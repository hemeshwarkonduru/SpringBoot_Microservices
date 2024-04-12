package com.everythingin.user.service;

import com.everythingin.user.dto.UserDetails;
import com.everythingin.user.entity.User;
import com.everythingin.user.exception.MyCustomException;
import com.everythingin.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public List<UserDetails> allUserDetails() throws Exception {
        try {
            List<User> users = userRepository.findAll();
            List<UserDetails> out = new ArrayList<>();
            if (!users.isEmpty()) {
                logger.info("Getting all user Details" );
                for (User ite : users) {
                    UserDetails each = new UserDetails();

                    each.setFirstName(ite.getFirstName());
                    each.setLastName(ite.getLastName());
                    each.setAge(ite.getAge());
                    each.setDob(ite.getDob());

                    out.add(each);
                }
            }

            return out;
        } catch (Exception e) {
            throw e;
        }
    }

    public void saveUserData(User user) throws MyCustomException {
        try {
            if (!(user == null)) {
                userRepository.save(user);

            } else {
                throw new MyCustomException("dead");
            }
        } catch (MyCustomException e) {
            throw new MyCustomException();
        }


    }



    public UserDetails getUserDataByMailMethod(String gmail) throws Exception {
        try {
            UserDetails userDetails = new UserDetails();
            if (gmail != null && !gmail.isEmpty()) {
                Optional<User> dataOpt = userRepository.findByGmail(gmail);
                if (dataOpt.isPresent()) {
                    User data = dataOpt.get();
                    userDetails.setFirstName(data.getFirstName());
                    userDetails.setLastName(data.getLastName());
                    userDetails.setAge(data.getAge());
                    userDetails.setDob(data.getDob());
                    userDetails.setGmail(data.getGmail());

                    return userDetails;
                }

            }
            return userDetails;
        } catch (Exception e) {
            throw e;
        }
    }

    static String something(){
        try(Scanner f = new Scanner(new File("test.in"))){
            return String.format("%s,%d,%s",f.nextLine(),f.nextInt(),f.nextLine());
        }catch(Exception e){
            return null
        }
    }
}
