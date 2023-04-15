package com.pentazon.betasave.modules.user.repository;

import com.pentazon.betasave.modules.user.model.BetasaveUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBetasaveUserRepository extends JpaRepository<BetasaveUser, Long>
{
    BetasaveUser findByEmailAddress(String emailAddress);
    BetasaveUser findByUsername(String username);
    BetasaveUser findByMobileNUmber(String mobileNumber);
}
