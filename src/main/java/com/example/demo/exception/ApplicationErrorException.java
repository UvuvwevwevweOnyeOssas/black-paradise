package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationErrorException extends RuntimeException {
	private String msg;

    public ApplicationErrorException(String errorSavingImageFile, IOException e) {
    }
}
