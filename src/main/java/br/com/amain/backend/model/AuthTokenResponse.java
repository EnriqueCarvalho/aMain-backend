package br.com.amain.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@AllArgsConstructor
public class AuthTokenResponse {

    private String accessToken;
    private long expiresIn;
    private Psicologo psicologo;
    private Paciente paciente;
}
