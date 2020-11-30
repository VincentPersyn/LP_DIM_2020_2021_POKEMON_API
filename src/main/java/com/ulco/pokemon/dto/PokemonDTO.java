package com.ulco.pokemon.dto;

import com.ulco.pokemon.enums.PokemonTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonDTO {

  @ApiModelProperty("L'id du pokémon")
  private Integer id;

  @ApiModelProperty("Le nom du pokémon")
  private String nom;

  @ApiModelProperty("La taille du pokémon")
  private Double taille;

  @ApiModelProperty("Le poids du pokémon")
  private Double poids;

  @ApiModelProperty("Le type du pokémon")
  private PokemonTypeEnum type;

}
