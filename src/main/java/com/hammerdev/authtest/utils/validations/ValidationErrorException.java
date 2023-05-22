package com.hammerdev.authtest.utils.validations;

public class ValidationErrorException extends Exception
{
	private final ValidationType validationType;
	private final ValidationField reqFieldValidation;

	public ValidationErrorException(String arg0, ValidationType validationType, ValidationField reqFieldValidation)
	{
		super(arg0);
		this.validationType = validationType;
		this.reqFieldValidation = reqFieldValidation;
	}

	public ValidationType getValidationType()
	{
		return validationType;
	}

	public ValidationField getFieldValidation()
	{
		return reqFieldValidation;
	}
}
