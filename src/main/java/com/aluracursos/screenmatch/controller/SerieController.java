package com.aluracursos.screenmatch.controller; // Indica que esta clase pertenece al paquete controller, dentro del proyecto com.aluracursos.screenmatch. Usualmente se usa para controladores web, donde se definen las rutas o endpoints.

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.SerieService;
import com.aluracursos.screenmatch.dto.SerieDTO;
//import com.aluracursos.screenmatch.model.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
                              /* @RestController :Le dice a Spring que esta clase es un controlador web REST.
                              Devuelve directamente datos (como texto o JSON) en lugar de renderizar una página HTML.
                              Equivale a combinar @Controller + @ResponseBody.

                               En Spring, un @Controller o @RestController es una clase que se encarga de recibir, procesar y
                               responder a las solicitudes HTTP que llegan a tu aplicación web o API. Es parte del patrón MVC
                               (Modelo-Vista-Controlador), donde el controlador es quien conecta el frontend con la lógica de negocio
                               (servicios, modelos, etc.).*/

public class SerieController {

    // @Autowired                             // Indica a Spring que haga una inyeccion de dependencias
    // private SerieRepository repository;    // Se envio estas dos lineas a SerieService

    @Autowired
    private SerieService servicio;      // inyeccion de dependencia de SerieService

    //@GetMapping("/series")
    @GetMapping()                                             /*@GetMapping("/series"): Asocia el método mostrarMensaje() con las peticiones GET que lleguen a la ruta /series.
                                                                         Cuando el usuario accede a http://localhost:8080/series (o el puerto que estés usando), Spring ejecuta este método.
                                                                         El método retorna una cadena de texto, que se envía al navegador como respuesta.*/
    //public String mostrarMensaje(){
    //  return "Este es mi primer mensaje en mi Aplicacion Web";

    public List<SerieDTO> obtenerTodasLasSeries() {            // Este método devuelve una lista de objetos de tipo SerieDTO (un record o clase usada para transferir datos).

        return servicio.obtenerTodasLasSeries();


          /*return repository.findAll().stream()                   // se llevo  a SerieService
                  .map(s -> new SerieDTO(s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(), s.getPoster(),
                          s.getGenero(), s.getActores(), s.getSinopsis()))
                  .collect(Collectors.toList());*/
    }

    // @GetMapping("/series/top5")
    @GetMapping("/top5")

    public List<SerieDTO> obtenerTop5() {

        return servicio.obtenerTop5();
    }


    @GetMapping("/lanzamientos")

    public List<SerieDTO> obtenerLanzamientosMasRecientes() {

        return servicio.obtenerLanzamientosMasRecientes();
    }


    @GetMapping("/{id}")   // id es un parametro dinamico
    public SerieDTO obtenerPorId(@PathVariable long id) {

        return servicio.obtenerPorId(id);
    }

    //--------------------------------Mapear todos los episodios

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obtenerTodasLasTemporadas(@PathVariable long id) {
        return servicio.obtenerTodasLasTemporadas(id);


    }

    //------------------------Mapear episodios por temporada o temporada por numero

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obtenerTemporadasPorNumero(@PathVariable long id, @PathVariable long numeroTemporada) {
        return servicio.obtenerTemporadasPorNumero(id,numeroTemporada);


    }

    @GetMapping("/categoria/{nombreGenero}")
    public List<SerieDTO> obtenerSeriePorCategoria(@PathVariable String nombreGenero){

        return servicio.obtenerSeriesPorCategoria(nombreGenero);
    }


    /*@GetMapping("/inicio")                 // Ejemplo que se borro
    public String muestraMensaje(){
          return "Probando LiveReloading";
    }*/


}
