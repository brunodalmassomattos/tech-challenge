package br.com.fiap.entrega.mocks;

import br.com.fiap.entrega.application.dto.LocalizacaoDto;

public class LocalizacaoDtoMock {

    public static LocalizacaoDto getLocalizacaoDto() {
        return LocalizacaoDto.builder()
                       .latitude("-20.820878")
                       .longitude("-49.514063")
                       .build();
    }
}
