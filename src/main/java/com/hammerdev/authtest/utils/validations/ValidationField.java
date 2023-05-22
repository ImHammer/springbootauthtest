package com.hammerdev.authtest.utils.validations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.hammerdev.authtest.utils.Utils;

public final class ValidationField implements Serializable
{
	public enum FieldType
	{
		STRING, NUMBER
	}

	public static ValidationField string()
	{
		return new ValidationField(FieldType.STRING);
	}

	public static ValidationField number()
	{
		return new ValidationField(FieldType.NUMBER);
	}

	private FieldType fieldType;
	// private List<ValidationExecutor> validations;
	private Map<ValidationType, ValidationExecutor> validations;
	private List<ValidationType> errors;

	public ValidationField(FieldType fieldType)
	{
		this(fieldType, new HashMap<>(), new ArrayList<>());
	}

	public ValidationField(ValidationField validationField)
	{
		this(validationField.fieldType, validationField.validations, validationField.errors);
	}

	public ValidationField(FieldType fileldType, Map<ValidationType, ValidationExecutor> validations, List<ValidationType> errors)
	{
		this.fieldType = fileldType;
		this.validations = validations;
		this.errors = errors;
	}

	public FieldType getFieldType()
	{
		return fieldType;
	}

	public Map<ValidationType, ValidationExecutor> getValidations()
	{
		return validations;
	}

	public List<ValidationType> getErrors()
	{
		return errors;
	}

	public ValidationField validate(Object value)
	{
		Iterator<Map.Entry<ValidationType, ValidationExecutor>> iterator = validations.entrySet().iterator();
		while(iterator.hasNext()) {
			ValidationExecutor executor = iterator.next().getValue();
			if (!executor.getValidation().get(value)) {
				errors.add(executor.getType());
			}
		}

		return this;
	}

	public ValidationField validateException(Object value) throws ValidationErrorException
	{
		Iterator<Map.Entry<ValidationType, ValidationExecutor>> iterator = validations.entrySet().iterator();
		while(iterator.hasNext()) {
			ValidationExecutor executor = iterator.next().getValue();
			if (!executor.getValidation().get(value)) {
				throw new ValidationErrorException(executor.getErrMessage(), executor.getType(), this);
			}
		}
		return this;
	}

	public boolean canError(ValidationType type)
	{
		return errors.contains(type);
	}

	public ValidationField min(int min, String errMessage)
	{
		this.validations.put(ValidationType.MIN, new ValidationExecutor(
			ValidationType.MIN.setValue(min),
			(Object value) -> 
				(value instanceof Integer && ((Integer)value) >= min) || 
				(value instanceof String && ((String)value).length() >= min),
			errMessage
		));
		return this;
	}

	public ValidationField max(int max, String errMessage)
	{
		this.validations.put(ValidationType.MAX, new ValidationExecutor(
			ValidationType.MAX.setValue(max),
			(Object value) ->
				(value instanceof Integer && ((Integer)value) <= max) || 
				(value instanceof String && ((String)value).length() <= max),
			errMessage
		));
		return this;
	}

	// SE EU TIVESSE TOMADO OUTROS CAMINHOS, ISSO NÃO SERIA NECESSÁRIO
	// MAS EU PREFERI ASSIM, TALVEZ EU MUDE NO FUTURO, MAS NÃO AGORA.
	public ValidationField minClear(int min, String errMessage)
	{
		this.validations.put(ValidationType.MIN, new ValidationExecutor(
			ValidationType.MIN.setValue(min),
			(Object value) -> 
				(value instanceof Integer && ((Integer)value) >= min) || 
				(value instanceof String && Utils.removeMultiplesSpace((String)value).length() >= min),
			errMessage
		));
		return this;
	}

	public ValidationField maxClear(int max, String errMessage)
	{
		this.validations.put(ValidationType.MAX, new ValidationExecutor(
			ValidationType.MAX.setValue(max),
			(Object value) ->
				(value instanceof Integer && ((Integer)value) <= max) || 
				(value instanceof String && Utils.removeMultiplesSpace((String)value).length() <= max),
			errMessage
		));
		return this;
	}

	public ValidationField pattern(String pattern, String errMessage)
	{
		this.validations.put(ValidationType.PATTERN, new ValidationExecutor(
			ValidationType.PATTERN.setValue(pattern),
			(Object value) -> (value instanceof String && Utils.match(pattern, (String)value)),
			errMessage
		));
		return this;
	}

	public ValidationField nonNull(String message)
	{
		this.validations.put(ValidationType.NONNULL, new ValidationExecutor(
			ValidationType.NONNULL,
			Objects::nonNull,
			message
		));
		return this;
	}

	public ValidationField nonBlank(String message)
	{
		this.validations.put(ValidationType.NONBLANK, new ValidationExecutor(
			ValidationType.NONBLANK,
			(Object value) -> (value instanceof String && Utils.removeMultiplesSpace((String)value).length() > 0),
			message
		));
		return this;
	}
}
