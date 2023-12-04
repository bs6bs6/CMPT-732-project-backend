package com.bs6.election.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PredictDTO {
    String uuid;
    Integer hour;
    Integer dayofweek;
    Integer online_age;
    String candidate;
    String language;
    String sentiment;
}
