package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.invoice.search.SessionHistoryCriteria;
import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import com.cob.billing.response.handler.ResponseHandler;
import com.cob.billing.usecases.bill.history.FindSessionHistoryUseCase;
import com.cob.billing.usecases.bill.history.SearchSessionHistoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/session/history")
public class SessionHistoryController {
    @Autowired
    FindSessionHistoryUseCase findSessionHistoryUseCase;
    @Autowired
    SearchSessionHistoryUseCase searchSessionHistoryUseCase;

    @GetMapping("/find")
    public ResponseEntity<Object> find(@RequestParam(name = "offset") int offset,
                                       @RequestParam(name = "limit") int limit) {
        Pageable paging = PageRequest.of(offset, limit, Sort.by("createdAt").descending());
        return new ResponseEntity<>(findSessionHistoryUseCase.find(paging), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<Object> search(@RequestParam(name = "offset") int offset,
                                         @RequestParam(name = "limit") int limit
            , @RequestBody SessionHistoryCriteria sessionHistoryCriteria) {
        return ResponseHandler
                .generateResponse("Successfully find sessions history",
                        HttpStatus.OK,
                        searchSessionHistoryUseCase.search(offset + 1, limit, sessionHistoryCriteria));
    }
}
