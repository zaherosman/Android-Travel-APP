package br.com.ibm.tripsparterapi.controllers;


import br.com.ibm.tripsparterapi.controllers.response.ResponseHandler;
import br.com.ibm.tripsparterapi.domain.favorite.FavoriteDto;
import br.com.ibm.tripsparterapi.entities.Favorite;
import br.com.ibm.tripsparterapi.services.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/register-favorites")
public class FavoriteController {

    @Autowired

    private FavoritesService service;

    @GetMapping("/allFavorites")
    public ResponseEntity<List<FavoriteDto>> getAllFavorites(){
         return ResponseEntity.ok(this.service.getAllFavorites());
    }


    @RequestMapping(value = "/requestById",method = RequestMethod.POST)
    public ResponseEntity<Object> getFavoritById(@RequestBody FavoriteDto favoriteDto){
        Favorite favorite = service.getFavoriteByFavoriteId(favoriteDto.getFavoriteId());
        if(favorite != null){
            return ResponseEntity.ok().body(favorite);
        }else{
            return ResponseHandler.generateResponse("Not Authenticated Trip", HttpStatus.NOT_FOUND, " Ñ OK");
        }
    }

    @RequestMapping(value = "/requestByUserId",method = RequestMethod.POST)
    public ResponseEntity<Object> getUserFavoriteById(@RequestBody FavoriteDto favoriteDto){
        List<Favorite> favorite = service.getFavoriteByUserId(favoriteDto.getUserId());
        if(favorite != null){
            return ResponseEntity.ok().body(favorite);
        }else{
            return ResponseHandler.generateResponse("Not Authenticated Trip", HttpStatus.NOT_FOUND, " Ñ OK");
        }
    }

    @RequestMapping(value = "/requestByUserIdAndTripId", method = RequestMethod.POST)
    public ResponseEntity<Object> getFavoriteByUserAndTripId(@RequestBody FavoriteDto favoriteDto){
        Favorite favorite = service.getFavoriteByUserIdAndTripID(favoriteDto.getUserId(), favoriteDto.getTripId());
        if(favorite != null){
            return ResponseEntity.ok().body(favorite);
        }else{
            return ResponseHandler.generateResponse("Not Authenticated Trip", HttpStatus.NOT_FOUND, " Ñ OK");
        }
    }

    @PostMapping
    public ResponseEntity<FavoriteDto> insertUser(@RequestBody FavoriteDto dto){
        dto = this.service.insertFavorite(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getFavoriteId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping(value= "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id){
        service.deleteFavorite(id);
        return ResponseHandler.generateResponse("Favorite deleted", HttpStatus.OK, "id " + id + " succesfuly deleted");
    }




}
