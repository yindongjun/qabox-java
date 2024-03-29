//package io.fluentqa.qabox.jpa.security.services;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import javax.persistence.EntityNotFoundException;
//import io.fluentqa.qabox.jpa.core.security.userdetails.GrantedAuthority;
//import io.fluentqa.qabox.jpa.core.security.userdetails.SimpleGrantedAuthority;
//import io.fluentqa.qabox.jpa.core.security.userdetails.UserDetails;
//import io.fluentqa.qabox.jpa.core.security.userdetails.UserDetailsService;
//import io.fluentqa.qabox.jpa.model.user.Privilege;
//import io.fluentqa.qabox.jpa.model.user.User;
//import io.fluentqa.qabox.jpa.repositories.UserRepository;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class SimpleUserService implements UserDetailsService {
//
//    private UserRepository usersRepository = new UserRepository();
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
//
//        User user =
//                usersRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
//
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//
//        return new io.fluentqa.qabox.jpa.core.security.userdetails.User(user.getUname(), user.getPassword(),
//                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
//                listUserAuthorities(user));
//    }
//
//    /**
//     * Retrieves an user privileges.
//     *
//     * @param user the user with privileges
//     * @return
//     */
//    private List<GrantedAuthority> listUserAuthorities(User user) {
//        return user.getRole().getPrivileges().stream().map(Privilege::getName)
//                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//    }
//
//}
