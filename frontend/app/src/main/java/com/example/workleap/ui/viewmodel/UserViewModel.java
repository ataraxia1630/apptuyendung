package com.example.workleap.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.User;
import com.example.workleap.data.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
//    private LiveData<List<User>> users;
//
//    public UserViewModel() {
//        userRepository = new UserRepository();
//        users = userRepository.getUsers();
//    }
//
//    public LiveData<List<User>> getUsers() {
//        return users;
//    }
}

