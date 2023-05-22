package com.hammerdev.authtest.utils.validations;

import java.io.Serializable;

public class ValidationExecutor implements Serializable
{
	private ValidationType type;
	private ValidationFunction validation;
	private String errMessage;

	public ValidationExecutor(ValidationType type, ValidationFunction validation, String errMessage)
	{
		this.type = type;
		this.validation = validation;
		this.errMessage = errMessage;
	}

	public ValidationType getType()
	{
		return type;
	}

	public void setType(ValidationType type)
	{
		this.type = type;
	}

	public ValidationFunction getValidation()
	{
		return validation;
	}

	public void setValidation(ValidationFunction validation)
	{
		this.validation = validation;
	}

	public String getErrMessage()
	{
		return errMessage;
	}

	public void setErrMessage(String errMessage)
	{
		this.errMessage = errMessage;
	}

}