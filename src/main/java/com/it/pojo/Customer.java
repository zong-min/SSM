package com.it.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer id;        // 客户 id
    private String username;   // 客户名称
    private String jobs;       // 职业
    private String phone;      // 电话
}