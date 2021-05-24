package repo;

import model.User;

public interface IUserRepository extends IRepository<Long, User> {
    User findOneByUsername(String username);
}
