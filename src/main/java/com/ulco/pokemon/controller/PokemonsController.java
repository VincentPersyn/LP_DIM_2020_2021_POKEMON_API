package com.ulco.pokemon.controller;

import java.net.URI;
import java.util.List;

import com.ulco.pokemon.dto.PokemonDTO;
import com.ulco.pokemon.service.IPokemonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/pokemons")
public class PokemonsController {

  @Autowired
  private IPokemonService pokemonService;

  @GetMapping
  public List<PokemonDTO> getAll() {
    return pokemonService.getAll();
  }

  @GetMapping("/{id}")
  public PokemonDTO findById(@PathVariable Integer id) {
    return pokemonService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody PokemonDTO pokemon) {
    pokemonService.create(pokemon);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(pokemon.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void update(@PathVariable Integer id, @RequestBody PokemonDTO upgradedPokemon) {
    pokemonService.update(id, upgradedPokemon);
  }


  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping
  public void deleteAll() {
    pokemonService.deleteAll();
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Integer id) {
    pokemonService.deleteById(id);
  }
}
