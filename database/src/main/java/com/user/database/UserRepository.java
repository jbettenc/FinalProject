package com.user.database;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
class UserRepository{
    List<User> usrs = new ArrayList<User>();

    void printUsers(){
        for(User usr: usrs){
            System.out.println(usr.getEmail());
        }
    }

    ResponseEntity<Integer> save(User usr){
        if(findByEmail(usr.getEmail()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        usrs.add(usr);
        printUsers();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    ResponseEntity<Integer> save(String email){
        if(findByEmail(email) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        User toInsert = new User("", "", email);
        usrs.add(toInsert);
        printUsers();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    ResponseEntity<Integer> deleteByEmail(String email){
        if(findByEmail(email) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for(int i = 0; i < usrs.size(); i++) {
            if(usrs.get(i).getEmail().equals(email)){
                usrs.remove(i);
                printUsers();
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        }
		return null;
    }

    ResponseEntity<Integer> delete(User user){
        if(findByEmail(user.getEmail()) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for(int i = 0; i < usrs.size(); i++) {
            if(usrs.get(i).getEmail().equals(user.getEmail())){
                usrs.remove(i);
                printUsers();
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        }
		return null;
    }

    User findByEmail(String email){
        for(User usr: usrs){
            if(usr.getEmail().equals(email)){
                return usr;
            }
        }
        return null;
    }

    List<User> findByFirstName(String firstName){
        List<User> res = new ArrayList<User>();
        for(User usr: usrs){
            if(usr.getFirstName().equals(firstName)){
                res.add(usr);
            }
        }
        return res;
    }

    List<User> findByLastName(String lastName){
        List<User> res = new ArrayList<User>();
        for(User usr: usrs){
            if(usr.getLastName().equals(lastName)){
                res.add(usr);
            }
        }
        return res;
    }


}
