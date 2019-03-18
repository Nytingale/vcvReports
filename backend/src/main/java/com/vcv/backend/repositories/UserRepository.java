package com.vcv.backend.repositories;

import com.vcv.backend.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    User findByEmail(String email);
    User findByCompanyId(Long companyId);

    List<User> findByCompanyIdOrderBySubscriptionStartDateDesc(Long companyId);
}
