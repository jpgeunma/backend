package com.jpmarket.domain.chattings;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class Chattings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



}
