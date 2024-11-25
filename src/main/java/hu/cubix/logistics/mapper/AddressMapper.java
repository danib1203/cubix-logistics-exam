package hu.cubix.logistics.mapper;

import hu.cubix.logistics.dto.AddressDto;
import hu.cubix.logistics.model.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto addressToDto(Address address);

    List<AddressDto> addressesToDtos(List<Address> address);

    Address dtoToAddress(AddressDto addressDTO);

    List<Address> dtosToAddresses(List<AddressDto> addressDtos);
}
