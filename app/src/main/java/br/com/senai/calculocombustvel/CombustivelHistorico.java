package br.com.senai.calculocombustvel;

public class CombustivelHistorico {
    private String titulo;
    private float valorEtanol;
    private float valorGasolina;
    private float percentual;

    public CombustivelHistorico(String titulo, float valorEtanol, float valorGasolina, float percentual) {
        this.titulo = titulo;
        this.valorEtanol = valorEtanol;
        this.valorGasolina = valorGasolina;
        this.percentual = percentual;
    }
}
