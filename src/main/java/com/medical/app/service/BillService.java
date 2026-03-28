package com.medical.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.medical.app.model.Bill;
import com.medical.app.repository.BillRepository;

@Service
public class BillService {

    @Autowired
    private BillRepository repo;

    public Bill saveBill(Bill bill) {
        return repo.save(bill);
    }

    public List<Bill> getAllBills() {
        return repo.findAll();
    }
}