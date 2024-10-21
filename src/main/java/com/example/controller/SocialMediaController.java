package com.example.controller;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService as;
    @Autowired
    MessageService ms;

    @PostMapping("/register")
    public Account registerAccount(@RequestBody Account account){
        return as.registerAccount(account);
    }

    @PostMapping("/login")
    public Account login(@RequestBody Account account){
        return as.login(account);
    }

    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message){
        return ms.createMessage(message);
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages(){
        return ms.getAllMessages();
    }

    @GetMapping("/messages/{message_id}")
    public List<Message> getMessageById(@PathVariable("message_id") int id){
        return ms.getMessageById(id);
    }
    //TODO: update this to return number of rows deleted
    @DeleteMapping("/messages/{message_id}")
    public void deleteMessageById(@PathVariable("message_id") int id){
        ms.deleteMessageById(id);
    }
    //TODO: update this to return number of rows updated
    @PatchMapping("/messages/{message_id}")
    public void updateMessageById(@PathVariable("message_id") int id){
        ms.updateMessageById(id);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public List<Message> getMessagesByAccount(@PathVariable("account_id") int accountId){
        return ms.getMessagesByAccount(accountId);
    }

}
