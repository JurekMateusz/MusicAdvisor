package advisor.model.api.error;

import lombok.Getter;

@Getter
public class Error {
  private int statusCode;
  private String message;
}
