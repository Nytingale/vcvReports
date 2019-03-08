package com.vcv.backend.repositories;

import com.vcv.backend.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, User.CompositeKey> {
    User findByEmailAndCompanyName(String email, String companyName);
    User findByCompanyNameAndAdmin(String companyName, Integer admin);

    List<User> findByCompanyNameOrderBySubscriptionStartDateDesc(String companyName);
    List<User> findByAdminOrderBySubscriptionStartDateDesc(Integer admin);
}
