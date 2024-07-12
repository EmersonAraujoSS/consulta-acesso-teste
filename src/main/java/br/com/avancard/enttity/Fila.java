package br.com.avancard.enttity;


import java.util.Objects;


//Representa a tabela "Fila" no banco de dados
public class Fila {

    //ATRIBUTOS
    private Long id;
    private String cpf;
    private String matricula;
    private String status;


    //CONSTRUTOR
    public Fila() {

    }


    //MÉTODOS ESPECIAIS
    public int getId() {
        return Math.toIntExact(id);
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fila fila = (Fila) o;
        return Objects.equals(id, fila.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
