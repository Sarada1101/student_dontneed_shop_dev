package shop.model.service;

import shop.model.bean.AdminBeans;
import shop.model.bean.MemberBeans;
import shop.model.dao.UserDao;

public class UserService extends CommonService {

    private UserDao userDao = new UserDao();

    public AdminBeans fetchAdminLogin(String adminMail, String adminPassword) {
        return userDao.fetchAdminLogin(adminMail, adminPassword);
    }

    public boolean deleteProduct(MemberBeans memberBeans) {
        return userDao.deleteProduct(memberBeans);
    }
}
