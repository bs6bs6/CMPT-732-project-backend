package com.bs6.election.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordDTO {
    private String uuid;
    private String state;
    private String word;
    private Integer count;
    private String candidate;
}
