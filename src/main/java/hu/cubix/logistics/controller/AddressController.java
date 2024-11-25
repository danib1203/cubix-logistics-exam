package hu.cubix.logistics.controller;

import hu.cubix.logistics.dto.AddressDto;
import hu.cubix.logistics.mapper.AddressMapper;
import hu.cubix.logistics.model.Address;
import hu.cubix.logistics.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    AddressMapper addressMapper;


    @PostMapping
    public AddressDto createAddress(@RequestBody @Valid AddressDto addressDto) {
        Address address = addressMapper.dtoToAddress(addressDto);
        Address createdAddress = addressService.create(address);
        if (Objects.isNull(createdAddress)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return addressMapper.addressToDto(createdAddress);
    }

    @GetMapping
    public List<AddressDto> getAllAddresses() {
        return addressMapper.addressesToDtos(addressService.findAll());
    }

    @GetMapping("/findByExample")
    public ResponseEntity<List<AddressDto>> getAddressesByExample(@RequestBody AddressDto addressDto,
                                                                  Pageable pageable) {
        Address address = addressMapper.dtoToAddress(addressDto);
        Page<Address> addressesByExample = addressService.findAddressesByExample(address, pageable);


        List<AddressDto> addressDtos = addressesByExample.getContent()
                .stream()
                .map(addressMapper::addressToDto)
                .toList();
        long totalCount = addressesByExample.getTotalElements();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(totalCount))
                .body(addressDtos);
    }

    @GetMapping("/{id}")
    public AddressDto getAnAddresses(@PathVariable int id) {
        Address address = addressService.findById(id);
        if (Objects.isNull(address)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return addressMapper.addressToDto(addressService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable int id) {
        addressService.delete(id);
    }

    @PutMapping("/{id}")
    public AddressDto updateAddress(@RequestBody @Valid AddressDto addressDto,
                                    @PathVariable long id) {
        if (!addressDto.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Address address = addressMapper.dtoToAddress(addressDto);
        address.setId(id);
        Address updatedAddress = addressService.update(address);
        return addressMapper.addressToDto(updatedAddress);
    }


}

