package ru.yakubov.vote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import ru.yakubov.vote.AuthorizedUser;
import ru.yakubov.vote.model.Role;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.repository.UserVoteRepository;

import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.checkNotFound;
import static ru.yakubov.vote.util.ValidationUtil.checkNotFoundWithId;

@Service
//@Service("userVoteService")
//@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserVoteService implements UserDetailsService {

    private final UserVoteRepository repository;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserVoteService(UserVoteRepository repository
//            , PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
//        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(value = "users", allEntries = true)
    public UserVote create(UserVote userVote) {
        Assert.notNull(userVote, "user must not be null");
        return repository.save(userVote);
//        return repository.save(savePassword(userVote, passwordEncoder));
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public UserVote get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public UserVote getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    public List<UserVote> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(UserVote userVote) {
        Assert.notNull(userVote, "user must not be null");
        checkNotFoundWithId(repository.save(userVote), userVote.id());
//        checkNotFoundWithId(repository.save(savePassword(userVote, passwordEncoder)), userVote.id());
    }

    public List<UserVote> getByRoles (Role role){
        return repository.getByRoles(role);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserVote user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

//    public static UserVote savePassword(UserVote userVote, PasswordEncoder passwordEncoder) {
//        String password = userVote.getPassword();
//        userVote.setPassword(StringUtils.isEmpty(password) ? password : passwordEncoder.encode(password));
//        userVote.setEmail(userVote.getEmail().trim().toLowerCase());
//        return userVote;
//    }

}
