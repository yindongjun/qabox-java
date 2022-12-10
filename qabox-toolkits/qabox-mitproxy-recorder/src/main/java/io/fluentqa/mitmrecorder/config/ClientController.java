package io.fluentqa.mitmrecorder.config;

import io.fluentqa.mitmrecorder.MessageCache;
import io.fluentqa.mitmrecorder.MitmInterceptedMessage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Api(tags = { "client" })
@RestController
public class ClientController {

    @Autowired
    private MessageCache<String, MitmInterceptedMessage> messageCache;

    @GetMapping("/last")
    public @ResponseBody ResponseEntity<MitmInterceptedMessage> getLastMessage(@RequestParam(value = "key", defaultValue = "") String key) {
        MitmInterceptedMessage mitmInterceptedMessage;

        if (!key.isEmpty()) {
            mitmInterceptedMessage = messageCache.getLastByKeyPart(key);
        } else {
            mitmInterceptedMessage = messageCache.getLast();
        }

        if (mitmInterceptedMessage != null) {
            return ResponseEntity.status(HttpStatus.OK).body(mitmInterceptedMessage);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @DeleteMapping("/clean")
    public void cleanMessageCache() {
        messageCache.removeAll();
    }

    @GetMapping("/all")
    public @ResponseBody ResponseEntity<List<MitmInterceptedMessage>> getAllMessages(@RequestParam(value = "key", defaultValue = "") String key) {
        List<MitmInterceptedMessage> mitmInterceptedMessageList;

        if (!key.isEmpty()) {
            mitmInterceptedMessageList = messageCache.getAllByKeyPart(key);
        } else {
            mitmInterceptedMessageList = messageCache.getAll();
        }

        if (mitmInterceptedMessageList != null && mitmInterceptedMessageList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(mitmInterceptedMessageList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @GetMapping("/list")
    public @ResponseBody ResponseEntity<String> getMessageList(@RequestParam(value = "key", defaultValue = "") String key) {
        String messageList;

        if (!key.isEmpty()) {
            messageList = messageCache.getListByKeyPart(key);
        } else {
            messageList = messageCache.getList();
        }

        if (messageList != null && !messageList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(messageList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

}
