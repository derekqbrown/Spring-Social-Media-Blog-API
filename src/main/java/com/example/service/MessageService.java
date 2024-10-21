package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    MessageRepository mr;

    public Message createMessage(Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            message.setMessageId(-1);
            return message;
        }
        return mr.save(message);
    }

    public List<Message> getAllMessages() {
        return mr.findAll();
    }

    public Message getMessageById(int id) {
        Optional<Message> optionalMessage = mr.findById(id);
        return optionalMessage.isPresent() ? optionalMessage.get() : new Message(id, "NULL",(long)0);
    }

    public void deleteMessageById(int id) {
        mr.deleteById(id);
    }

    public Integer updateMessageById(Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            return 400;
        }
        
        mr.save(message);
        return 200;
    }

    public List<Message> getMessagesByAccount(int accountId) {
        return mr.findAllByPostedBy(accountId);

    }
}
