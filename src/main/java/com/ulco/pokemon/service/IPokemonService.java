package com.ulco.pokemon.service;

import java.util.List;

import com.ulco.pokemon.dto.PokemonDTO;

public interface IPokemonService {

  List<PokemonDTO> getAll();

  PokemonDTO findById(Integer id);

  PokemonDTO create(PokemonDTO pokemonDTO);

  void update(Integer id, PokemonDTO updatedPokemonDTO);

  void deleteAll();

  void deleteById(Integer id);
}
