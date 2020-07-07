
package shop.model.dao;

import shop.model.bean.AdminBeans;
import shop.model.bean.MemberBeans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends DaoBase {
    public AdminBeans fetchAdminLogin(String adminMail, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //戻り値を初期化
        AdminBeans adminBeans = null;
        try {
            //DBのオープン
            this.connect();
            //SQLを作成
            String sql = "SELECT * FROM admin WHERE admin_mail = ? AND admin_password = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, adminMail);
            stmt.setString(2, password);

            //SQLの発行
            rs = stmt.executeQuery();
            rs.next();

            //beanへ格納(検索失敗ならrs.nextでcatchへ飛ぶ)
            adminBeans = new AdminBeans();
            adminBeans.setAdminMail(rs.getString("admin_mail"));
            adminBeans.setAdminName(rs.getString("admin_name"));
            adminBeans.setAdminPassword(rs.getString("admin_password"));
            adminBeans.setPostalCode(rs.getInt("postal_code"));
            adminBeans.setAddress(rs.getString("address"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminBeans;
    }

    public boolean deleteMember(MemberBeans memberBeans) {
        PreparedStatement stmt = null;
        int deleteLine = 0;

        try {
            this.connect();

            String sql = "DELETE FROM member WHERE member_mail = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, memberBeans.getMemberMail());
            deleteLine = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deleteLine != 0;
    }
}
