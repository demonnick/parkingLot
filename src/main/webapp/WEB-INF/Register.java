import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {
    Connection con;
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }
        try {
            con=DriverManager.getConnection("jdbc:hsqldb:mydatabase","SA","");
            con.createStatement().executeUpdate("create table contacts (name varchar(45),email varchar(45),phone varchar(45))");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        try {
            PreparedStatement pst=con.prepareStatement("insert into contacts values(?,?,?)");
            pst.clearParameters();
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            int i=pst.executeUpdate();
            out.write(i+" records inserted, <a href='ViewRecords'>View Records</a>");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}