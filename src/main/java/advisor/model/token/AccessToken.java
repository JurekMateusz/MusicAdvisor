package advisor.model.token;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AccessToken {
  @SerializedName("access_token")
  private String accessToken;

  @SerializedName("token_type")
  private String tokenType;

  @SerializedName("expires_in")
  private long expiresIn;

  @Setter
  @SerializedName("refresh_token")
  private String refreshToken;

  private String scope;
}
