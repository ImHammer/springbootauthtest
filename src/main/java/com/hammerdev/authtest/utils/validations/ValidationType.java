package com.hammerdev.authtest.utils.validations;

public enum ValidationType
{
	MIN, MAX, PATTERN, NONNULL, NONBLANK;

	private Object value;

	public Object getValue()
	{
		return value;
	}

	public ValidationType setValue(Object value)
	{
		this.value = value;
		return this;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return this.name();
	}
}