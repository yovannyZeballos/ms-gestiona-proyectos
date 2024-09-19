package pe.com.yzm.mapper;

import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import pe.com.yzm.expose.request.CompanyCreateRequest;
import pe.com.yzm.expose.request.CompanyUpdateRequest;
import pe.com.yzm.expose.response.CompanyResponse;
import pe.com.yzm.model.Company;

@Mapper(componentModel = "spring")
public abstract class CompanyMapper {

    public abstract CompanyResponse companytoCompanyResponse(Company company);

    public abstract Company companyCreateRequestToCompany(CompanyCreateRequest companyRequest);

    public Company companyUpdateRequestToCompany(Company companyUpdate, CompanyUpdateRequest companyRequest) {
        companyUpdate.setUserId(companyRequest.getUserId());
        companyUpdate.setName(companyRequest.getName());
        companyUpdate.setId(companyRequest.getId());
        return companyUpdate;
    }
}
