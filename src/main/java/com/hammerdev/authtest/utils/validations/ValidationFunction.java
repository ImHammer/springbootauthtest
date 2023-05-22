package com.hammerdev.authtest.utils.validations;

@FunctionalInterface
public interface ValidationFunction
{
	boolean get(Object value);
}
