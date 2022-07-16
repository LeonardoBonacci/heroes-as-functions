package com.example.ignite;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import lombok.Data;

@Data
public class Person {
  
  @QuerySqlField(index = true)
  private Long id;
  
  @QuerySqlField(index = true)
  private String name;
  
  private boolean isEmployed;
}