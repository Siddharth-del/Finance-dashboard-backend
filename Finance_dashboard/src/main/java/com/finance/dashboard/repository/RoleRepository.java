package com.finance.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finance.dashboard.model.AppRole;
import com.finance.dashboard.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

  Optional<Role> findByRoleName(AppRole appRole);
}
