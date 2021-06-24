package com.letsCode.socialNetwork.controller;

import com.letsCode.socialNetwork.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {

    private int counter = 4;    //3 messages already created
    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>(){{
        add(new HashMap<String, String>() {{put("id", "1"); put("text", "First message"); }});
        add(new HashMap<String, String>() {{put("id", "2"); put("text", "Second message"); }});
        add(new HashMap<String, String>() {{put("id", "3"); put("text", "Third message"); }});
    }};


    @GetMapping
    public List<Map<String, String>> list(){
        return messages;  //returns json
    }

    @GetMapping("{id}")
    public Map<String, String> getAnyMessage(@PathVariable String id){
        return getMessage(id);
    }

    private Map<String, String> getMessage(String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new); //our custom exception
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message){
        message.put("id", String.valueOf(counter++));
        messages.add(message);

        return message;
    }

    @PutMapping("{id}")
    public Map<String , String> update(@PathVariable String id,
                                       @RequestBody Map<String, String> message){

        Map<String, String> messageFromDb = getMessage(id);
        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){   //in testing gonna use the return answer's code
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }
}
