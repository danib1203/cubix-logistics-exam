package hu.cubix.logistics.repository;

import hu.cubix.logistics.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepository extends JpaRepository<Address, Long>,
        PagingAndSortingRepository<Address, Long>, JpaSpecificationExecutor<Address> {

}
