package com.siva.expense_approval_system.api.mapper;

import com.siva.expense_approval_system.api.dto.request.CreateTenantRequest;
import com.siva.expense_approval_system.api.dto.request.UpdateTenantRequest;
import com.siva.expense_approval_system.api.dto.response.TenantResponse;
import com.siva.expense_approval_system.domain.model.Tenant;

public class TenantMapper {

    public Tenant toEntity(CreateTenantRequest request){

         Tenant tenant = new Tenant();

         tenant.setName(request.getName());

         return tenant;
    }

    public void updateEntity(UpdateTenantRequest request,Tenant tenant){

         tenant.setName(request.getName());
    }

    public TenantResponse toResponse(Tenant tenant){

         TenantResponse response = new TenantResponse();

         response.setId(tenant.getId());
         response.setName(tenant.getName());

         return response;
    }

}
