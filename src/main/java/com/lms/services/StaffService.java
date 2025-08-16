package com.lms.services;

import com.lms.domain.Staff;

public interface StaffService {
    Staff getStaffById(Long id);
    Staff getStaffByUsername(String username);
}
