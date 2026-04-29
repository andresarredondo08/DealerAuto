package com.andres_arredondo.dealers_auto_center.inventory.shared.exception;

public class TenantAccessDeniedException extends RuntimeException{

    public TenantAccessDeniedException(String message){
        super(message);
    }
}
