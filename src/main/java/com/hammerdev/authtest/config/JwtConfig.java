package com.hammerdev.authtest.config;

public class JwtConfig
{
    private String secret;
    private int expires;

    public String getSecret()
    {
        return secret;
    }
    public void setSecret(String secret)
    {
        this.secret = secret;
    }
    public int getExpires()
    {
        return expires;
    }
    public void setExpires(int expires)
    {
        this.expires = expires;
    }
}
