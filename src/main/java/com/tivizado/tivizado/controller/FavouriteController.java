package com.tivizado.tivizado.controller;

import java.net.URI;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tivizado.tivizado.model.Favourite;
import com.tivizado.tivizado.model.Show;
import com.tivizado.tivizado.repository.FavouriteRepository;
import com.tivizado.tivizado.repository.ShowRepository;

@RestController
@RequestMapping("api/favourite")
public class FavouriteController {

    @Autowired
    private ShowRepository reposhow;

    @Autowired
    private FavouriteRepository repofav;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getFavouriteById(@PathVariable("id") Integer id) {
        try {
            System.out.println(id + "Arrrr");
            List<Favourite> favourites = repofav.findByUserid(id);

			if (favourites.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(favourites, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFavourite(@RequestBody @Validated Favourite data) {

        try {

            if (data.getUserid() == null || data.getShowid() == null) {
                return new ResponseEntity<>("JSON object is empty or null.", HttpStatus.BAD_REQUEST);
            }

            // Revisar si esa serie existe en la bd
            Show newShow = new Show();
            Long resultShowId = null;
            Optional<Show> verifyShowById = reposhow.findByShowid(data.getShowid());

            // Si no existe, agregarla la serie para futuras busquedas
            if (!verifyShowById.isPresent()) {
                // Buscar en la api y agregar la serie en la BD
                URI uri = new URI("https://api.tvmaze.com/shows/" + data.getShowid());

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<Show> responseEntity = restTemplate.getForEntity(uri, Show.class);
                System.out.println(responseEntity);
                Show resultApi = responseEntity.getBody();

                // Si no existe la serie tirar error
                if (resultApi == null) {
                    return new ResponseEntity<>("Tv show not exist", HttpStatus.NOT_FOUND);
                }

                newShow.setShowid(resultApi.getId());
                newShow.setName(resultApi.getName());
                newShow.setPremiered(resultApi.getPremiered());
                newShow.setStatus(resultApi.getStatus());
                newShow.setUrl(resultApi.getUrl());

                Show result = reposhow.save(newShow);
                resultShowId = result.getId();
            }

            // Id show Verificado
            Long id_show = resultShowId != null ? resultShowId : verifyShowById.get().getId();
            Integer id_user = data.getUserid();

            // Revisar si ya no esta agregado a favoritos
            Optional<Favourite> verifyFav = repofav.findByShowidAndUserid(id_show, id_user);

            if (verifyFav.isPresent()) {
                return new ResponseEntity<>("Favourite is exist.", HttpStatus.NOT_FOUND);
            }

            // Agregar a favoritos con la relacion con el id del user y de la serie...
            Favourite newFav = new Favourite();
            newFav.setShowid(id_show);
            newFav.setUserid(id_user);

            // Save
            repofav.save(newFav);

            return new ResponseEntity<>("Saved Succesfully", HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteFavourite(@RequestBody Favourite data) {

        try {

            if (data.getUserid() == null || data.getShowid() == null) {
                return new ResponseEntity<>("JSON object is empty or null.", HttpStatus.BAD_REQUEST);
            }

            Long id_show = data.getShowid();
            Integer id_user = data.getUserid();

            // Revisar si existe el favorito
            Optional<Favourite> verifyFav = repofav.findByShowidAndUserid(id_show, id_user);

            if (!verifyFav.isPresent()) {
                return new ResponseEntity<>("Favourite not exist.", HttpStatus.NOT_FOUND);
            }

            Integer verifyFavId = verifyFav.get().getId();

            // Eliminar de la tabla
            repofav.deleteById(verifyFavId);

            return new ResponseEntity<>("Removed Succesfully", HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
