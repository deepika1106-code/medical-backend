package com.medical.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.medical.app.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
