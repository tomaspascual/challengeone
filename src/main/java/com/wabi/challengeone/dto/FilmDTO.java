package com.wabi.challengeone.dto;

import lombok.*;
import lombok.extern.java.Log;

@Data
@Builder
@Log
public class FilmDTO {

    private int Year;
    private int Score;
    private String Title;

}
