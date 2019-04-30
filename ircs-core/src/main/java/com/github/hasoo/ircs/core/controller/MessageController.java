package com.github.hasoo.ircs.core.controller;

import com.github.hasoo.ircs.core.dto.MessageResponse;
import com.github.hasoo.ircs.core.dto.MultipleSms;
import com.github.hasoo.ircs.core.dto.ReportResponse;
import com.github.hasoo.ircs.core.dto.SmsRequest;
import com.github.hasoo.ircs.core.queue.ReportQue;
import com.github.hasoo.ircs.core.service.ReceiverService;
import com.github.hasoo.ircs.core.service.ReportDeliverService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {

  @Autowired
  private ReceiverService<SmsRequest> smsReceiverService;

  @Autowired
  @Qualifier("reportDeliverService")
  private ReportDeliverService ReportDeliverService;

  @PostMapping(path = "/api/v1/sms", produces = "application/json")
  public ResponseEntity<?> receiveSms(@Valid @RequestBody SmsRequest smsRequest) {

    if (!smsReceiverService.receive(smsRequest)) {
      return new ResponseEntity<Object>(
          new MessageResponse(smsRequest.getKey(), "inner error", "9000"), HttpStatus.OK);
    }

    return new ResponseEntity<Object>(new MessageResponse(smsRequest.getKey(), "success", "1000"),
        HttpStatus.OK);
  }

  @PostMapping(path = "/api/v2/sms", produces = "application/json")
  public ResponseEntity<?> receiveMultipleSms(@Valid @RequestBody MultipleSms multipleSms) {

    log.info(multipleSms.toString());

    return new ResponseEntity<Object>(
        new MessageResponse(multipleSms.getGroupKey(), "success", "1000"),
        HttpStatus.OK);
  }

  @GetMapping(path = "/api/v1/report", produces = "application/json")
  public ResponseEntity<?> writeReport() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    ReportResponse reportResponse = new ReportResponse();
    List<ReportQue> reports = null;
    try {
      reports = ReportDeliverService.receiveReport(username);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    if (null == reports) {
      reportResponse.setResult("NO");
    } else {
      reportResponse.setResult("OK");
      reportResponse.setReportQues(reports);
    }

    return new ResponseEntity<Object>(reportResponse, HttpStatus.OK);
  }
}
