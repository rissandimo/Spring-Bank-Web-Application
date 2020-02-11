package com.rissandimo.bankwebapp3.controller;

import com.rissandimo.bankwebapp3.dao.Transaction;
import com.rissandimo.bankwebapp3.dao.Type;
import com.rissandimo.bankwebapp3.dao.User;
import com.rissandimo.bankwebapp3.repository.TransactionRepository;
import com.rissandimo.bankwebapp3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController
{
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    @Autowired
    private User user;

    public TransactionController(TransactionRepository transactionRepository, UserRepository userRepository)
    {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/addNewTransaction")
    public String addNewTransaction(Model model)
    {
        model.addAttribute("newTransaction", new Transaction());
        return "transactions/new-transaction";
    }

    @GetMapping("/transactionList")
    public String getTransactionList(Model model)
    {
        model.addAttribute("transactions", transactionRepository.findTransactionsByUser(getCurrentUser()));
        return "transactions/transaction-list";
    }

    @PostMapping("/processNewTransaction")
    public String processNewTransaction(@ModelAttribute Transaction newTransaction)
    {
        Type tranTypeEnum = newTransaction.getType();
        String tranTypeString = tranTypeEnum.toString();


        //update balance
        switch(tranTypeEnum)
        {
            case DEPOSIT:
            {
                newTransaction.setBalance(user.getCurrentBalance() + newTransaction.getAmount());
                user.setCurrentBalance(user.getCurrentBalance() + newTransaction.getAmount());
                break;
            }
            case WITHDRAWAL:
            {
                newTransaction.setBalance(user.getCurrentBalance() - newTransaction.getAmount());

                user.setCurrentBalance(user.getCurrentBalance() - newTransaction.getAmount());
                break;
            }
            case PURCHASE:
            {
                newTransaction.setBalance(user.getCurrentBalance() - newTransaction.getAmount());

                user.setCurrentBalance(user.getCurrentBalance() - newTransaction.getAmount());
            }

        }
        transactionRepository.save(newTransaction);

        User currentUser = getCurrentUser();
        newTransaction.setUser(currentUser);
        userRepository.save(currentUser);
        return "redirect:/transactions/transactionList";

    }


    private User getCurrentUser()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = ((UserDetails) principal).getUsername();

        return userRepository.findUserByUsername(username);
    }


}
