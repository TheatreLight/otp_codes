package promo.it.services;

import promo.it.dao.DaoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersService {
    private DaoManager daoManager;

    public UsersService(DaoManager dm) {
        daoManager = dm;
    }

    public Map<Long, String> getUsersList() {
        var usersList = daoManager.getDaoUsers().getUsersList();
        Map<Long, String> result = new HashMap<>();
        for (var u : usersList) {
            result.put(u.getId(), u.getLogin());
        }
        return result;
    }
}
