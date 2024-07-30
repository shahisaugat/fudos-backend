package com.example.fooddelivery.Service.Impl;
import com.example.fooddelivery.Entity.Payment;
import com.example.fooddelivery.Pojo.PaymentPojo;
import com.example.fooddelivery.Repository.PaymentRepo;
import com.example.fooddelivery.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepo paymentRepo;
    @Override
    public void addPayment(PaymentPojo paymentPojo) {
        Payment payment = new Payment();
        payment.setId(paymentPojo.getId());
        payment.setDate(paymentPojo.getDate());
        payment.setType(paymentPojo.getType());
        payment.setAmount(paymentPojo.getAmount());
        payment.setOrder(paymentPojo.getOrder());
        this.paymentRepo.save(payment);
    }

    @Override
    public void updateData(Integer id, PaymentPojo paymentPojo) {
        Optional<Payment> paymentOptional = paymentRepo.findById(id);
        if (paymentOptional.isPresent()) {
            Payment existingPayment = paymentOptional.get();
            updatePaymentProperties(existingPayment, paymentPojo);
            paymentRepo.save(existingPayment); // Save the updated student
        } else {
            throw new IllegalArgumentException("payment with ID " + id + " not found");
        }

    }
    private void updatePaymentProperties(Payment payment, PaymentPojo paymentPojo) {
        payment.setId(paymentPojo.getId());
        payment.setDate(paymentPojo.getDate());
        payment.setType(paymentPojo.getType());
        payment.setAmount(paymentPojo.getAmount());
        payment.setOrder(paymentPojo.getOrder());
        this.paymentRepo.save(payment);
    }

    @Override
    public void deleteById(Integer id) {
        paymentRepo.deleteById(id);

    }

    @Override
    public List<Payment> getAll() {
        return paymentRepo.findAll();
    }

    @Override
    public Optional<Payment> findById(Integer id) {
        return paymentRepo.findById(id);
    }


    @Override
    public boolean existsById(Integer id) {
        return this.paymentRepo.existsById(id);
    }
}
