package com.xyw.dfood.entityDao;


import com.xyw.dfood.Model.User;

/**
 * Created by Do on 2015/4/23.
 */
public interface UserDao extends BaseDao<User> {
    public boolean registUser(User user);
    public boolean login(User user);
}
