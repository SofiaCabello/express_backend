package org.example.express_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerInfoDTO implements Serializable {
    private Long id;
    private String username;
    private String phone;
    private String email;
}
