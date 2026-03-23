package com.utn.ProgIII.exceptions;

public class ProductSupplierNotExistException extends NotFoundException {
    public ProductSupplierNotExistException(String msg){
        super(msg);
    }
}
