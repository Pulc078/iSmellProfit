package fr.denoyel.luc.application.model;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(toBuilder = true)
public class MagicBazarCard {

    private String nameVf;
    private String nameVo;
    private String extension;
    private String lang;
    private String etat;
    private Double buyPrice;
    private String foil;

}
