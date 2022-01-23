package com.example.demorentalcar.api;

import com.example.demorentalcar.model.Transaction;
import com.example.demorentalcar.model.TransactionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class TransactionApiController implements TransactionApi {

    @Autowired
    private TransactionApiService transactionApiService;

    @Override
    public TransactionList getAllTransaction(String userId, String state) {
        return new TransactionList(transactionApiService.getAllTransaction(userId, state));
    }

    @Override
    public TransactionList getTransaction(String userId, String transactionId) {
        List<Transaction> list = new ArrayList<>();
        Transaction transaction = transactionApiService.getTransaction(userId, transactionId);
        if (transaction != null) {
            list.add(transaction);
        }
        return new TransactionList(list);


    }
}
