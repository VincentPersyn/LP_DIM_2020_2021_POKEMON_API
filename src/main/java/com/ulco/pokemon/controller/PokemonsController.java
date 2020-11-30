package com.ulco.pokemon.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.ulco.pokemon.dto.PokemonDTO;
import com.ulco.pokemon.enums.PokemonTypeEnum;
import com.ulco.pokemon.exception.AlreadyExistException;
import com.ulco.pokemon.exception.NotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/pokemons")
public class PokemonsController {

  private final List<PokemonDTO> pokemonList;

  public PokemonsController() {
    pokemonList = new ArrayList<>();

    pokemonList.add(new PokemonDTO(1, "Carapuce", 1.0, 1.0, PokemonTypeEnum.EAU));
    pokemonList.add(new PokemonDTO(2, "Bulbizare", 1.0, 1.0, PokemonTypeEnum.PLANTE));
    pokemonList.add(new PokemonDTO(3, "Salamèche", 1.0, 1.0, PokemonTypeEnum.FEU));
    pokemonList.add(new PokemonDTO(4, "Carabaffe", 10.0, 10.0, PokemonTypeEnum.EAU));
  }

  @GetMapping
  public List<PokemonDTO> getAll() {
    return pokemonList;
  }

  @GetMapping("/{id}")
  public PokemonDTO findById(@PathVariable Integer id) {
    return pokemonList.stream()
        .filter(pokemon -> pokemon.getId().equals(id))
        .findFirst()
        .orElseThrow(NotFoundException::new);
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody PokemonDTO pokemon) {
    boolean idAlreadyExist = pokemonList.stream()
        .anyMatch(pokemonToSearch -> pokemonToSearch.getId().equals(pokemon.getId()));

    if (idAlreadyExist) {
      throw new AlreadyExistException();
    }

    pokemonList.add(pokemon);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(pokemon.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }


  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody PokemonDTO upgradedPokemon) {
    boolean idAlreadyExist = pokemonList.stream()
        .anyMatch(pokemon -> pokemon.getId().equals(upgradedPokemon.getId()));

    if (idAlreadyExist && !id.equals(upgradedPokemon.getId())) {
      throw new AlreadyExistException();
    }

    PokemonDTO pokemonToUpgrade = pokemonList.stream()
        .filter(pokemon -> pokemon.getId().equals(id))
        .findFirst()
        .orElseThrow(NotFoundException::new);

    pokemonToUpgrade.setId(upgradedPokemon.getId());
    pokemonToUpgrade.setNom(upgradedPokemon.getNom());
    pokemonToUpgrade.setPoids(upgradedPokemon.getPoids());
    pokemonToUpgrade.setTaille(upgradedPokemon.getTaille());
    pokemonToUpgrade.setType(upgradedPokemon.getType());

    return ResponseEntity.noContent().build();
  }

  //  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping
  public ResponseEntity<Void> deleteAll() {
    pokemonList.clear();

    return ResponseEntity.noContent().build();

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
    PokemonDTO pokemonToKill = pokemonList.stream()
        .filter(pokemon -> pokemon.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new NotFoundException("Le pokémon avec l'id " + id + " n'existe pas."));

    pokemonList.remove(pokemonToKill);

    return ResponseEntity.noContent().build();

  }
}
