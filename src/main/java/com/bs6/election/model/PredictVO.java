package com.bs6.election.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PredictVO {
    //"hour", "dayofweek", "online_age", "candidate", "language", "sentiment
    Integer hour;
    Integer dayofweek;
    Integer online_age;
    String candidate;
    String language;
    String sentiment;

}
