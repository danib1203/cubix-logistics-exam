package hu.cubix.logistics.service;

import hu.cubix.logistics.model.Address;
import hu.cubix.logistics.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import static hu.cubix.logistics.service.AddressSpecifications.*;

import java.util.List;
import java.util.Objects;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Transactional
    public Address create(Address address) {
        if (Objects.isNull(address)) {
            return null;
        }

        if (address.getId() != null && addressRepository.existsById(address.getId())) {
            throw new IllegalArgumentException("Address with id " + address.getId() + " already " + "exists");
        }
        return save(address);
    }

    @Transactional
    public Address update(Address address) {
        if (Objects.isNull(address)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (findById(address.getId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Address with id " + address.getId() + " not found");
        }
        return save(address);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address findById(final long id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Transactional
    public Page<Address> findAddressesByExample(Address exampleAddress, Pageable pageable) {

        if (Objects.isNull(exampleAddress)) {
            return null;
        }
        Pageable defaultPageable = pageable;
        if (pageable == null) {
            defaultPageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by("id").ascending());
        } else if (!pageable.getSort().isSorted()) {
            defaultPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by("id").ascending());
        }

        String city = exampleAddress.getCity();
        String street = exampleAddress.getStreet();
        String countryCode = exampleAddress.getCountryCode();
        int zipCode = exampleAddress.getZipCode();


        Specification<Address> specs = Specification.where(null);

        if (StringUtils.hasLength(city)) {
            specs = specs.and(cityPrefix(city));
        }
        if (StringUtils.hasLength(street)) {
            specs = specs.and(streetPrefix(street));
        }
        if (StringUtils.hasLength(countryCode)) {
            specs = specs.and(countryCodeExactMatch(countryCode));
        }
        if (zipCode != 0) {
            specs = specs.and(zipCodeExactMatch(zipCode));
        }


        return addressRepository.findAll(specs, defaultPageable);
    }

    @Transactional
    public void delete(final long id) {
        addressRepository.deleteById(id);
    }

    private Address save(Address address) {
        return addressRepository.save(address);
    }

}
