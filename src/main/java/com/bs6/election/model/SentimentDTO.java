package com.bs6.election.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SentimentDTO {
    String state;
    Integer bidenpositive;
    Integer bidennegative;
    Integer bidenneutral;
    Integer trumpneutral;
    Integer trumppositive;
    Integer trumpnegative;
    Integer total;

}
