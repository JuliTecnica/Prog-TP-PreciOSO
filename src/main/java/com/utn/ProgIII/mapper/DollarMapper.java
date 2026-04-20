package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.ViewDolarDTO;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
/*
 * Una clase que se dedica a convertir un JSON a un dto
 */
public class DollarMapper {

    public ViewDolarDTO dollarJsonObjectToDTO(JSONObject jsonObject)
    {
        String moneda = jsonObject.getString("moneda");
        String nombre = jsonObject.getString("nombre");
        String casa = jsonObject.getString("casa");
        Double venta = jsonObject.getDouble("venta");
        Double compra = jsonObject.getDouble("compra");
        String ultima_actualizacion = jsonObject.getString("fechaActualizacion");

        return new ViewDolarDTO(moneda,nombre,casa,venta,compra,ultima_actualizacion);
    }
}
