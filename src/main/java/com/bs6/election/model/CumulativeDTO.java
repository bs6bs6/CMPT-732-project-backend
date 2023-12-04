package com.bs6.election.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class CumulativeDTO {
    String date;
    String language;
    Integer cumulative;
}
