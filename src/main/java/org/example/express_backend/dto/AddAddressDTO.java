package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddAddressDTO implements Serializable {
    private Long id;
    private String address;
}
