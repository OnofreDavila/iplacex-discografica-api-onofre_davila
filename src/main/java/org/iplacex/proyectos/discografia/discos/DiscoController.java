package org.iplacex.proyectos.discografia.discos;


import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;
    @Autowired
    private IArtistaRepository artistaRepo;

    //insertar registro de disco
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandleInsertDiscoRequest(@RequestBody Disco disco){
        if(artistaRepo.existsById(disco.getIdArtista())){
            return new ResponseEntity<>(discoRepo.save(disco),null, HttpStatus.CREATED);
        }
    
        return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
    }

    //Obtener registro en base a su ID
    @GetMapping(
        value = "/disco/{_id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandleGetDiscoRequest(@PathVariable("_id") String _id){
        Optional<Disco> temp = discoRepo.findById(_id);
        
        if(!temp.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(temp.get(), null, HttpStatus.OK);
    }

    //Obtener todos los registros de la coleccion
    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest(){
        List<Disco> discos = discoRepo.findAll();

        return new  ResponseEntity<>(discos,null, HttpStatus.OK);
    }

    //Obtener registros de los discos de un artista en base a su id desde la coleccion discos.
    @GetMapping(
        value = "/discos/{idArtista}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosPorArtistaRequest(
        @PathVariable("idArtista") String idArtista
    ){
        List<Disco> discos = discoRepo.findDiscosByIdArtista(idArtista);

        return new  ResponseEntity<>(discos,null, HttpStatus.OK);
    }
    
}
