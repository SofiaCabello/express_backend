package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddAddressDTO implements Serializable {
    private Integer id;
    private String address;
}
