package io.fluentqa.qabox.jpa.app.seeder;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import io.fluentqa.qabox.jpa.base.EntityManagerOperation;
import io.fluentqa.qabox.jpa.base.TransactionalOperation;
import io.fluentqa.qabox.jpa.base.WithGlobalEntityManager;
import io.fluentqa.qabox.jpa.core.security.crypto.BCryptPasswordEncoder;
import io.fluentqa.qabox.jpa.model.user.Privilege;
import io.fluentqa.qabox.jpa.model.user.Role;
import io.fluentqa.qabox.jpa.model.user.User;
import io.fluentqa.qabox.jpa.repositories.PrivilegeRepository;
import io.fluentqa.qabox.jpa.repositories.RoleRepository;
import io.fluentqa.qabox.jpa.repositories.UserRepository;

public class AuthSeeder implements WithGlobalEntityManager, EntityManagerOperation,
        TransactionalOperation {

    private final UserRepository userRepository = new UserRepository();

    private final RoleRepository roleRepository = new RoleRepository();

    private final PrivilegeRepository privilegeRepository = new PrivilegeRepository();


    /**
     * The auth seeds follows a hirachy for roles and privileges.
     */
    public void seed() {

        generatePrivielges();

        generateRoles();

        generateUsers();

    }


    public void generatePrivielges() {

        withTransaction(() -> {
            createPrivilegeIfNotExists("READ");
            createPrivilegeIfNotExists("CREATE");
            createPrivilegeIfNotExists("DELETE");
        });
    }

    public void generateRoles() {

        withTransaction(() -> {

            // User Role => Granted with ONLY the [READ] privilege
            Collection<Privilege> userPrivileges =
                    Arrays.asList(privilegeRepository.findByName("READ").get());
            Role roleUser = createRoleIfNotExists("USER", userPrivileges);

            // Staff Role => Granted with all privileges of an User and with the [CREATE] privilege
            Collection<Privilege> staffPrivileges = new HashSet<>();

            staffPrivileges.add(privilegeRepository.findByName("CREATE").get());
            staffPrivileges.addAll(roleUser.getPrivileges());

            Role staffRole = createRoleIfNotExists("STAFF", staffPrivileges);

            // Admin Role => Granted with all priviles of a Staff, and includes the [DELETE]
            // privilege
            Collection<Privilege> adminPrivileges = new HashSet<>();
            adminPrivileges.add(privilegeRepository.findByName("DELETE").get());
            adminPrivileges.addAll(staffRole.getPrivileges());
            createRoleIfNotExists("ADMIN", adminPrivileges);

        });
    }

    public void generateUsers() {

        withTransaction(() -> {
            createUserIfNotExists("admin", "admin",
                    "https://images.pexels.com/photos/2726111/pexels-photo-2726111.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                    roleRepository.findByName("ADMIN").get());
            createUserIfNotExists("staff", "staff",
                    "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                    roleRepository.findByName("STAFF").get());
            createUserIfNotExists("user", "user",
                    "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
                    roleRepository.findByName("USER").get());
        });

    }


    /**
     * 
     * @param name
     * @return
     */
    private Privilege createPrivilegeIfNotExists(String name) {

        return privilegeRepository.findByName(name).orElseGet(() -> {
            Privilege privilege = new Privilege();
            privilege.setName(name);

            return privilegeRepository.createEntity(privilege);
        });

    }


    /**
     * Persists a new Role when does not exist.
     * 
     * @param name the role name
     * @param privileges the associated privileges
     * @return a new Role or the existing one.
     */
    private Role createRoleIfNotExists(String name, Collection<Privilege> privileges) {

        return roleRepository.findByName(name).orElseGet(() -> {

            Role role = new Role();

            role.setName(name);

            // Creates a new privilege for convenience, a ROLE_<roleName>
            Privilege rolePrivilege = createPrivilegeIfNotExists(String.format("ROLE_%s", name));

            Set<Privilege> newPrivileges = new HashSet<>();
            newPrivileges.addAll(privileges);
            newPrivileges.add(rolePrivilege);
            role.setPrivileges(newPrivileges);

            return roleRepository.createEntity(role);
        });

    }


    /**
     * Persists an User if does not exist.
     * 
     * @param username The username to be used.
     * @param password a raw password.
     * @param profile an image profile
     * @param role a new Role
     * @return a new User or retrieves the existing one
     */
    public User createUserIfNotExists(String username, String password, String profile, Role role) {
        return userRepository.findByUsername(username).orElseGet(() -> {

            User user = new User(username, new BCryptPasswordEncoder().encode(password));
            user.setRole(role);
            user.setProfileUrl(profile);

            return userRepository.createEntity(user);
        });
    }

}
