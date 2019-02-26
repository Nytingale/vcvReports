package com.vcv.backend.repositories;

import com.vcv.backend.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, User.CompositeKey> {
}
