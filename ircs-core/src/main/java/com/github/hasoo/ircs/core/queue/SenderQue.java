package com.github.hasoo.ircs.core.queue;

import com.github.hasoo.ircs.core.router.map.Sender;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SenderQue {

  private String msgKey;
  private String userKey;
  private String groupname;
  private String username;
  private Double fee;
  private LocalDateTime resDate;
  private LocalDateTime sentDate;
  private String msgType;
  private String contentType;
  private String phone;
  private String callback;
  private String message;
  private String code;
  private String desc;
  private LocalDateTime doneDate;
  private String net;

  private String routingType;
  private List<Sender> senders;
}
