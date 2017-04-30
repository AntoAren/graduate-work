package by.bsu.zakharankou.restservices.model.token;

import by.bsu.zakharankou.restservices.common.JWS;

public class IdentityCredentials extends Credentials {

    private JWS.JWSSubject subject;

    public IdentityCredentials(JWS.JWSSubject subject, String username) {
        super(username, null);
        this.subject = subject;
    }

    public JWS.JWSSubject getSubject() {
        return subject;
    }

    public void setSubject(JWS.JWSSubject subject) {
        this.subject = subject;
    }

}