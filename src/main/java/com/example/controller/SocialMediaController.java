package com.example.controller;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        return ResponseEntity.status(as.registerAccount(account)).body(as.getAccount(account).get());
 
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        int statusCode =as.login(account);
        if(statusCode!=200){
            return ResponseEntity.status(statusCode).body(account);
        }
        return ResponseEntity.status(statusCode).body(as.getAccount(account).get());
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        if(as.getAccountById(message.getPostedBy()) == null){
            return ResponseEntity.status(400).body(message);
        }
        Message createdMessage = ms.createMessage(message);
        if(createdMessage.getMessageId() < 0){
            return ResponseEntity.status(400).body(createdMessage);
        }
        return ResponseEntity.status(200).body(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(ms.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("message_id") int id){
        Message msg = ms.getMessageById(id);
        if(msg.getMessageText().contentEquals("NULL")){
            return ResponseEntity.status(200).body(null);
        }
        return ResponseEntity.status(200).body(msg);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable("message_id") int id){
        Message msg = ms.getMessageById(id);
        if(msg.getMessageText().contentEquals("NULL")){
            return ResponseEntity.status(200).body(null);
        }
        ms.deleteMessageById(id);
        return ResponseEntity.status(200).body(1);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable("message_id") int id, @RequestBody Message message){
        Message msg = ms.getMessageById(id);
        if(msg.getMessageText().contentEquals("NULL")){
            return ResponseEntity.status(400).body(null);
        }
        message.setPostedBy(msg.getPostedBy());
        message.setTimePostedEpoch(msg.getTimePostedEpoch());
        message.setMessageId(id);
        
        return ResponseEntity.status(ms.updateMessageById(message)).body(1);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable("account_id") int accountId){
        return ResponseEntity.status(200).body(ms.getMessagesByAccount(accountId));
    }

}
