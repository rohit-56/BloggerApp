package com.example.BloggerApp.http.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
public class CommentRequest {

    @JsonProperty("content")
    public String content;
}
