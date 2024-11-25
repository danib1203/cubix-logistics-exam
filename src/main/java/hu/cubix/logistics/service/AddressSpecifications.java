package hu.cubix.logistics.service;

import hu.cubix.logistics.model.Address;
import hu.cubix.logistics.model.Address_;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecifications {

    public static Specification<Address> cityPrefix(String cityPrefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.city)),
                cityPrefix.toLowerCase() + "%");
    }

    public static Specification<Address> streetPrefix(String streetPrefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.street)),
                streetPrefix.toLowerCase() + "%");
    }

    public static Specification<Address> countryCodeExactMatch(String countryCode) {
        return (root, cq, cb) -> cb.equal(root.get(Address_.countryCode), countryCode);

    }

    public static Specification<Address> zipCodeExactMatch(int zipCode) {
        return (root, cq, cb) -> cb.equal(root.get(Address_.zipCode), zipCode);

    }


}
