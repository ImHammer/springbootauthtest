package com.hammerdev.authtest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class PersonAddress
{
	@Id
	@Column(name = "person_id")
	private Long id;

	@Column
	private String street;

	@Column
	private String city;

	@Column
	private String state;

	@Column
	private int number;

	@Column
	private String cep;

	@OneToOne
	@MapsId
	@JoinColumn(name = "data_id")
	private PersonData data;
}
