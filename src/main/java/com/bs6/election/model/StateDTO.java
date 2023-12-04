package com.bs6.election.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {
    private String state;
    private UUID id;
    private int biden;
    private int total;
    private int trump;

}
