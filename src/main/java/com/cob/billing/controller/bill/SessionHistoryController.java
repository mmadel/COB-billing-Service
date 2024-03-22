package com.cob.billing.controller.bill;

import com.cob.billing.usecases.bill.history.FindSessionHistoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/session/history")
public class SessionHistoryController {
    @Autowired
    FindSessionHistoryUseCase findSessionHistoryUseCase;

    @GetMapping("/find")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") int offset,
                                       @RequestParam(name = "limit") int limit) {
        return new ResponseEntity<>(findSessionHistoryUseCase.find(offset + 1, limit), HttpStatus.OK);
    }
}
