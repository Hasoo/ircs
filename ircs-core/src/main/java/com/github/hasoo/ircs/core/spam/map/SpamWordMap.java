package com.github.hasoo.ircs.core.spam.map;

import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpamWordMap {
  private HashMap<String, List<String>> spamWords;
}
