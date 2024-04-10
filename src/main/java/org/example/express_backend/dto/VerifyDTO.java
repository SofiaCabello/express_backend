package org.example.express_backend.dto;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Data
@Builder
public class VerifyDTO implements Serializable {
    private Long userId;
    private String token;
}
