package com.hammerdev.authtest.utils.validations;

public class ValidationFieldErroException extends Exception
{
	private final String fieldName;
	private final ValidationType validationType;
	private final ValidationField reqFieldValidation;

	public ValidationFieldErroException(String fieldName, ValidationErrorException e)
	{
		this(fieldName, e.getMessage(), e.getValidationType(), e.getFieldValidation());
	}

	public ValidationFieldErroException(String fieldName, String errMessage, ValidationType validationType, ValidationField reqFieldValidation)
	{
		super(errMessage);
		this.fieldName = fieldName;
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

	public String getFieldName()
	{
		return fieldName;
	}
}
