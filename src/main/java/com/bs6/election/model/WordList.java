package com.bs6.election.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordList {
    private String state;
    private Map<String,Integer> words;
    private String candidate;
}
