package com.ulco.pokemon.service;

import java.util.ArrayList;
import java.util.List;

import com.ulco.pokemon.dto.PokemonDTO;
import com.ulco.pokemon.enums.PokemonTypeEnum;
import com.ulco.pokemon.exception.AlreadyExistException;
import com.ulco.pokemon.exception.NotFoundException;

import org.springframework.stereotype.Service;

@Service
public class PokemonService implements IPokemonService {

  private final List<PokemonDTO> pokemonList;

  public PokemonService() {
    pokemonList = new ArrayList<>();

    pokemonList.add(new PokemonDTO(1, "Carapuce", 1.0, 1.0, PokemonTypeEnum.EAU));
    pokemonList.add(new PokemonDTO(2, "Bulbizare", 1.0, 1.0, PokemonTypeEnum.PLANTE));
    pokemonList.add(new PokemonDTO(3, "Salamèche", 1.0, 1.0, PokemonTypeEnum.FEU));
    pokemonList.add(new PokemonDTO(4, "Carabaffe", 10.0, 10.0, PokemonTypeEnum.EAU));
  }

  @Override
  public List<PokemonDTO> getAll() {
    return pokemonList;
  }

  @Override
  public PokemonDTO findById(Integer id) {
    return pokemonList.stream()
        .filter(pokemon -> pokemon.getId().equals(id))
        .findFirst()
        .orElseThrow(NotFoundException::new);
  }

  @Override
  public PokemonDTO create(PokemonDTO pokemonDTO) {

    boolean idAlreadyExist = pokemonList.stream()
        .anyMatch(pokemon -> pokemon.getId().equals(pokemonDTO.getId()));

    if (idAlreadyExist) {
      throw new AlreadyExistException();
    }

//    try {
//      findById(pokemonDTO.getId());
//      throw new AlreadyExistException();
//    } catch (NotFoundException e) {
//      pokemonList.add(pokemonDTO);
//      return pokemonDTO;
//    }

    pokemonList.add(pokemonDTO);
    return pokemonDTO;

  }

  @Override
  public void update(Integer id, PokemonDTO updatedPokemonDTO) {

    PokemonDTO pokemonToUpgrade = findById(id);

    boolean idAlreadyExist = pokemonList.stream()
        .anyMatch(pokemon -> pokemon.getId().equals(updatedPokemonDTO.getId()));

    if (idAlreadyExist && !id.equals(updatedPokemonDTO.getId())) {
      throw new AlreadyExistException();
    }

    pokemonToUpgrade.setId(updatedPokemonDTO.getId());
    pokemonToUpgrade.setNom(updatedPokemonDTO.getNom());
    pokemonToUpgrade.setPoids(updatedPokemonDTO.getPoids());
    pokemonToUpgrade.setTaille(updatedPokemonDTO.getTaille());
    pokemonToUpgrade.setType(updatedPokemonDTO.getType());
  }

  @Override
  public void deleteAll() {
    pokemonList.clear();
  }

  @Override
  public void deleteById(Integer id) {
    PokemonDTO pokemonToKill = findById(id);

    pokemonList.remove(pokemonToKill);
  }
}
