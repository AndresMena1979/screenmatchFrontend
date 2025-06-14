package com.aluracursos.screenmatch.model;

import com.aluracursos.screenmatch.service.ConsultaChatGPT;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;   // para importar la clase OptionalDouble, que es parte del paquete estándar de Java (java.util), y se usa cuando trabajas con valores double
                                   // opcionales (es decir, que pueden o no estar presentes).

@Entity                  //Indica que esto va hacer una Tabla de base de datos
@Table(name = "series")  //Para cambiar el nombre de la tabla y no tome el nombre de la clase



public class Serie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //ID generado automaticamente

    private Long Id;

    @Column(unique = true)          //Para que no permita tener titulos repetidos
    private String titulo;

    private Integer totalTemporadas;

    private Double evaluacion;

    private String poster;

    @Enumerated(EnumType.STRING)  // Para indicar que hay un Enum
    private Categoria genero;

    private String actores;

    private String sinopsis;

   // @Transient   //Para indicar que hay una lista de episodios pero no se van a usar en el momento

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Relacion entre la tabla serie y la tabla episodio de 1 serie a muchos episodios
                                   // mappedBy = "serie", es el campo que relaciona uno con el otro, private Serie serie; que se creo en episodio
                                   //cascade = CascadeType.ALL: Esto hace que todas las operaciones realizadas sobre el episodio (guardar, actualizar, eliminar) también se apliquen automáticamente a la serie relacionada.

    private List<Episodio> episodios;

    public Serie(){  //Constructor predeterminado excencial para que trabaje la opcion 3

    }
    public Serie(DatosSerie datosSerie){   //constructor de Serie

        this.titulo = datosSerie.titulo();
        this.totalTemporadas = datosSerie.totalTemporadas();

        this.evaluacion= OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse( 0 );
        this.poster = datosSerie.poster();
        this.genero =Categoria.fromString(datosSerie.genero().split(",")[0].trim()); //Esta línea convierte el primer género de una cadena (que probablemente contiene varios géneros separados por comas)
                                                                                           // en una instancia del enum Categoria. Si hay mas de un genero selecciona el primero(genero que estan divididos por la ,)
        this.actores =datosSerie.actores();
        this.sinopsis=datosSerie.sinopsis();

 //------------------------------
      //para usar el traductor

    /*  this.sinopsis = ConsultaChatGPT.obtenerTraduccion(datosSerie.sinopsis());

        try {
            this.sinopsis = ConsultaChatGPT.obtenerTraduccion(datosSerie.sinopsis());
        } catch (Exception e) {
            System.out.println("Error al traducir sinopsis: " + e.getMessage());
            this.sinopsis = "Sinopsis no disponible";
        } */

    }
//--------------------------------

   /* OptionalDouble.of = es una forma segura y clara de trabajar con resultados double que podrían no existir.
      Double.valueOf = Convierte ese String (por ejemplo "8.5") en un objeto Double.
      orElse = Extrae el valor del OptionalDouble. Si el OptionalDouble tuviera un valor, lo devuelve.
      Si no tuviera valor (aunque en este caso siempre lo tiene), devuelve 0 como valor por defecto.  */


    // El método fromString busca convertir un texto recibido (por ejemplo "Action" o "Comedy") en un valor del enum Categoria correspondiente (como Categoria.ACCION o Categoria.COMEDIA).
    // Categoria.fromString(...) Convierte ese texto ("Action") en una instancia del enum Categoria, usando el método que tú definiste:
    // Categoria.ACCION
    // La función split() en Java se utiliza para dividir un String en partes, usando un delimitador (como una coma, espacio, guion, etc.).
    // trinm()= no traiga un valor vacio.  La función .trim() en Java elimina los espacios en blanco al inicio y al final de un String.

//----------------------------------------getter y setter


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public List<Episodio> getEpisodios() {

        return episodios;

          }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e ->e.setSerie(this));
        this.episodios=episodios;

    }


    @Override
    public String toString() {
        return  "genero=" + genero + '\'' +
                ", titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", evaluacion=" + evaluacion +
                ", poster='" + poster + '\'' +
                ", actores='" + actores + '\'' +
                ", sinopsis='" + sinopsis + '\''+
                ", episodios='" + episodios + '\'';
    }






}
