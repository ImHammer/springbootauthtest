package com.hammerdev.authtest.utils;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class Responses
{
    private Responses() {}
    
    public static <T> ResponseEntity<T> ok(@Nullable T body)
    {
        return ResponseEntity.ok().body(body);
    }

    public static <T> ResponseEntity<T> created(@Nullable T body, URI uri)
    {
        return ResponseEntity.created(uri).body(body);
    }


    public static ResponseStatusException badRequest(@Nullable String errMessage)
    {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, errMessage);
    }
    
    public static ResponseEntity<Response> notFound(String resMessage)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(null, resMessage));
    }

    public static ResponseEntity<Response> noContent()
    {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    public static record Response(Object data, @Nullable String message)
    {}
}
