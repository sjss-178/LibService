package com.lms.services.impl;

import com.lms.domain.Staff;
import com.lms.repos.StaffRepository;
import com.lms.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Staff getStaffById(Long id) {
        Optional<Staff> staffOptional = staffRepository.findById(id);
        return staffOptional.orElse(null); // or throw custom exception
    }
    @Override
    public Staff getStaffByUsername(String username){
        Optional<Staff> staffOptional = staffRepository.findByUsername(username);
        return staffOptional.orElse(null);
    }
}
