package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.ViewDolarDTO;
import com.utn.ProgIII.exceptions.UnexpectedServerErrorException;
import com.utn.ProgIII.mapper.DollarMapper;
import com.utn.ProgIII.service.interfaces.MiscService;
import org.json.JSONObject;
import org.juliganga.Exceptions.BadRequestException;
import org.juliganga.Exceptions.IncorrectParseMethodException;
import org.juliganga.Model.Request.BackendRequest;
import org.juliganga.Model.Request.JSONConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
/*
 * Un servicio que se encarga de buscar el valor del dólar usando la api de dolarapi.com
 */
public class MiscServiceImpl implements MiscService {
    private final String dolar_api_url = "https://dolarapi.com/v1/";

    @Autowired
    private DollarMapper dollarmapper;

    /**
     * Busca el valor del dólar actual según cotización, valor por defecto es oficial
     *
     * @param exchange_rate Cotización del dólar en dolarapi.com
     * @return Un DTO con los datos de esa cotización
     */
    @Override
    public ViewDolarDTO searchDollarPrice(String exchange_rate) {
        JSONObject dolar;
        try {
            BackendRequest dollarrequest = new BackendRequest(dolar_api_url, "dolares/" + exchange_rate);
            dolar = JSONConverter.makeJsonObject(dollarrequest.searchData());
        } catch (IOException e) {
            throw new UnexpectedServerErrorException("Ocurrió un error conectando a la API del precio del dólar", 500);
        } catch (InterruptedException | IncorrectParseMethodException e) {
            throw new UnexpectedServerErrorException("Un error inesperado ocurrió en el servicio.",500);
        } catch (BadRequestException e) {

            if(e.getHttpCode() != 404)
            {
                throw new UnexpectedServerErrorException("Un error inesperado ocurrió en la API, puede no estar disponible", e.getHttpCode());
            } else {
                throw new UnexpectedServerErrorException("Esa cotización no existe",e.getHttpCode());
            }

        }
        return dollarmapper.dollarJsonObjectToDTO(dolar);
    }

}
