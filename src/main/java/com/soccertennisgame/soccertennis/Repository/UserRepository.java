package com.soccertennisgame.soccertennis.Repository;


import com.soccertennisgame.soccertennis.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email );
    List<User> findByParentEmail(String parentEmail );
    List<User> findByRootAdmin(String rootAdmin );
    List<User> findByRoot(String root );
    List<User> findByRoleIdAndRootAdmin(int roleId,String rootAdmin );
    List<User> findByRoleIdAndRoot(int roleId,String root );
    List<User> findByRoleIdAndSuperAdmin(int roleId,String root );
    List<User> findByRoleIdAndAdmin(int roleId,String root );
    List<User> findByRoleIdAndSuperSuper(int roleId,String root );
    List<User> findByRoleIdAndSuperMaster(int roleId,String root );
    List<User> findByRoleIdAndMaster(int roleId,String root );

}

