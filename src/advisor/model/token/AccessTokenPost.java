package advisor.model.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccessTokenPost {
    @JsonProperty("client_id")
    private String clientId = "2ee3d9aa7be04620bbc2838939e84407";
    @JsonProperty("client_secret")
    private String clientSecret = "aea346f5479d4f7f955fbe683b3a6e47";
    @JsonProperty("grant_type")
    private String grantType = "authorization_code";
    @JsonProperty("code")
    private String code;
    @JsonProperty("redirect_uri")
    private String redirectUri = "http://localhost:8080";

    public AccessTokenPost(String code) {
        this.code = code;
    }

    public static AccessTokenPost of(String code) {
        return new AccessTokenPost(code);
    }
}
