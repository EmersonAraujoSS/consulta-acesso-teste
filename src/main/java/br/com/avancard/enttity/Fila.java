package br.com.avancard.enttity;


import java.util.Objects;


//Representa a tabela "Fila" no banco de dados
public class Fila {

    //ATRIBUTOS
    private Long id;
    private String cpf;
    private String matricula;
    private String status;
    private String mst;
    private String msr;
    private String msd;
    private String mct;
    private String mcr;
    private String mcd;
    private String situacaoTextBox;



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
    public String getMst() {
        return mst;
    }
    public void setMst(String mst) {
        this.mst = mst;
    }
    public String getMsr() {
        return msr;
    }
    public void setMsr(String msr) {
        this.msr = msr;
    }
    public String getMsd() {
        return msd;
    }
    public void setMsd(String msd) {
        this.msd = msd;
    }
    public String getMct() {
        return mct;
    }
    public void setMct(String mct) {
        this.mct = mct;
    }
    public String getMcr() {
        return mcr;
    }
    public void setMcr(String mcr) {
        this.mcr = mcr;
    }
    public String getMcd() {
        return mcd;
    }
    public void setMcd(String mcd) {
        this.mcd = mcd;
    }
    public String getSituacaoTextBox() {
        return situacaoTextBox;
    }
    public void setSituacaoTextBox(String situacaoTextBox) {
        this.situacaoTextBox = situacaoTextBox;
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
