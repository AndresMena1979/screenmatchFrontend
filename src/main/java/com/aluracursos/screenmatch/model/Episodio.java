package com.aluracursos.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


@Entity // identificar que es una entidad o una tabla
@Table(name = "episodios")  //Colocar nombre a la tabla

public class Episodio {

    @Id                  // Marca el campo como clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //ID generado automaticamente,Le dice a JPA que el valor del ID se genere automáticamente usando una estrategia específica.
    // GenerationType.IDENTITY significa: "Deja que la base de datos lo genere automáticamente (como una columna autoincremental)."
    private Long Id;
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double evaluacion;
    private LocalDate fechaDeLanzamiento;

    @ManyToOne
    private Serie serie;    //atributo de serie que identifica a que serie va a pertenecer los episodios

    public Episodio(){   //Constructor predeterminado


    }






    public Episodio(Integer numero, DatosEpisodio d) {
        this.temporada = numero;
        this.titulo = d.titulo();
        this.numeroEpisodio = d.numeroEpisodio();
        try{
            this.evaluacion = Double.valueOf(d.evaluacion());
        }catch (NumberFormatException e){
            this.evaluacion = 0.0;
        }
        try{
            this.fechaDeLanzamiento = LocalDate.parse(d.fechaDeLanzamiento());
        } catch (DateTimeParseException e){
            this.fechaDeLanzamiento = null;
        }

    }
//-----------getter y setter--------------------------------------------------
    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public LocalDate getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }


//------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", evaluacion=" + evaluacion +
                ", fechaDeLanzamiento=" + fechaDeLanzamiento;
    }
}
