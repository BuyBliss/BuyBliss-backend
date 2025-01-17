package com.commerce.ECommerce.Model.Response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {
    private Long vendorId;
    private String name;
    private String email;
    private String contactNo;
}
