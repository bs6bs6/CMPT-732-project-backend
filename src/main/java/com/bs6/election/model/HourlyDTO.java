package com.bs6.election.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HourlyDTO {
    Integer hour;
    Integer biden;
    Integer trump;
}
