package hu.cubix.logistics.security;

import hu.cubix.logistics.model.LogisticsUser;
import hu.cubix.logistics.repository.LogisticsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LogisticsUserDetailsService implements UserDetailsService {

    @Autowired
    LogisticsUserRepository logisticsUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LogisticsUser logisticsUser = logisticsUserRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(username, logisticsUser.getPassword(),
                logisticsUser.getRoles().stream().map(SimpleGrantedAuthority::new).toList());
    }
}
