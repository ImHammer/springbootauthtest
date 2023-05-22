package com.hammerdev.authtest.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username = "";

    @Column(nullable = false)
    private String firstName = "";

    @Column(nullable = false)
    private String lastName = "";

    @Column(nullable = false)
    private String password = "";

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data_id")
    private PersonData data;

    public Person()
    {
    }

    public Person(String username, String firstName, String lastName, String password)
    {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public Long getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public PersonData getData()
    {
        return data;
    }

    public void setData(PersonData data)
    {
        this.data = data;
    }
}
