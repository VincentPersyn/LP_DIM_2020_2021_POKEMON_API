package com.ulco.pokemon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ulco.pokemon.dto.PokemonDTO;
import com.ulco.pokemon.enums.PokemonTypeEnum;
import com.ulco.pokemon.exception.AlreadyExistException;
import com.ulco.pokemon.exception.NotFoundException;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("prod")
@Service("pokemonServiceUsingMap")
public class PokemonServiceV2 implements IPokemonService {

  private Map<Integer, PokemonDTO> pokemonDTOMap;

  public PokemonServiceV2() {
    pokemonDTOMap = new HashMap<>();

    pokemonDTOMap.put(1, new PokemonDTO(1, "Carapuce", 1.0, 1.0, PokemonTypeEnum.EAU));
    pokemonDTOMap.put(2, new PokemonDTO(2, "Bulbizare", 1.0, 1.0, PokemonTypeEnum.PLANTE));
    pokemonDTOMap.put(3, new PokemonDTO(3, "Salamèche", 1.0, 1.0, PokemonTypeEnum.FEU));
    pokemonDTOMap.put(4, new PokemonDTO(4, "Carabaffe", 10.0, 10.0, PokemonTypeEnum.EAU));
  }

  @Override
  public List<PokemonDTO> getAll() {
    log.info("Je stocke mes données dans une MAP");

    return pokemonDTOMap.values()
        .stream()
        .collect(Collectors.toList());
  }

  @Override
  public PokemonDTO findById(Integer id) {
//    Optional<PokemonDTO> maybePokemonDTO = Optional.ofNullable(pokemonDTOMap.get(id));
//
//    if (maybePokemonDTO.isPresent()) {
//      return maybePokemonDTO.get();
//    }
//
//    throw new NotFoundException();

    return Optional.ofNullable(pokemonDTOMap.get(id))
        .orElseThrow(NotFoundException::new);
  }

  @Override
  public PokemonDTO create(PokemonDTO pokemonDTO) {
    boolean idAlreadyExist =
        pokemonDTOMap.keySet().stream()
            .anyMatch(x -> x.equals(pokemonDTO.getId()));

    if (idAlreadyExist) {
      throw new AlreadyExistException();
    }

    pokemonDTOMap.put(pokemonDTO.getId(), pokemonDTO);
    return pokemonDTO;
  }

  @Override
  public void update(Integer id, PokemonDTO updatedPokemonDTO) {
    PokemonDTO pokemonToUpgrade = findById(id);

    boolean idAlreadyExist =
        pokemonDTOMap.keySet().stream()
            .anyMatch(x -> x.equals(updatedPokemonDTO.getId()));

    if (idAlreadyExist && !id.equals(updatedPokemonDTO.getId())) {
      throw new AlreadyExistException();
    }

    deleteById(id);
    create(updatedPokemonDTO);

    pokemonToUpgrade.setId(updatedPokemonDTO.getId());
    pokemonToUpgrade.setNom(updatedPokemonDTO.getNom());
    pokemonToUpgrade.setPoids(updatedPokemonDTO.getPoids());
    pokemonToUpgrade.setTaille(updatedPokemonDTO.getTaille());
    pokemonToUpgrade.setType(updatedPokemonDTO.getType());
  }

  @Override
  public void deleteAll() {
    pokemonDTOMap.clear();
  }

  @Override
  public void deleteById(Integer id) {
    findById(id);
    pokemonDTOMap.remove(id);
  }
}
