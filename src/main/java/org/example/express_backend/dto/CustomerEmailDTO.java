package org.example.express_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CustomerEmailDTO implements Serializable {
    private String email;
    private String code;
}
