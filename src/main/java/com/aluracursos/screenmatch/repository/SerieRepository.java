package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long>{

    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);


    // List<Serie> findByTotalTemporadasLessThanEquaAndEvaluacionGreaterThanEqual(int totalTemporadas,double evaluacion );

   //@Query( value = "SELECT * FROM series WHERE series.total_temporadas <= 6 And series.evaluacion >= 7.5", nativeQuery = true)


   @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas And s.evaluacion >= :evaluacion")  // : indica que es el valor y no el atributo
   List<Serie> seriePorTemporadaYEvaluacion(int totalTemporadas,double evaluacion);                             // JPQL

//---------Busqueda por nombre o parte del nombre de espisodios

@Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%") //La consulta quiere seleccionar episodios (e) de todas las series (s) cuya lista de episodios contiene alguno con un título que coincida parcialmente con el valor del parámetro nombreEpisodio, sin importar mayúsculas o minúsculas.
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

@Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);

//---------------------------------------
/* Optional<Serie>:
El método devuelve un Optional que puede contener una entidad Serie si se encuentra, o estar vacío si no hay coincidencias.

findByTitulo:
Spring busca por el campo titulo de la entidad Serie. El nombre del campo debe coincidir con un atributo de la clase Serie.

Contains:
Permite coincidencias parciales (como si usaras %nombre% en SQL).
Por ejemplo, si el título es "Breaking Bad", la búsqueda "Break" también lo encuentra.

IgnoreCase:
La búsqueda ignora mayúsculas y minúsculas.
"breaking" encuentra "Breaking Bad", "BREAKING" también lo hace.*/

 //----------------------------Lanzamientos mas reciente
@Query("SELECT s FROM Serie s " + "JOIN s.episodios e " + "GROUP BY s " + "ORDER BY MAX(e.fechaDeLanzamiento) DESC LIMIT 5")
 List<Serie> lanzamientosMasRecientes();

//-----se usa JPQL
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
    List<Episodio> obtenerTemporadasPorNumero(long id, long numeroTemporada);
}
