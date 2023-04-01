import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

class Connections {
    static Connection buildConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", "root", "root");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    static void closeConnection(Connection con) {
        try {
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Student {
    String roll;
    String name;
    String gender = "";

    /**
     * @param roll
     * @param name
     * @param gender
     */
    public Student(int roll, String name, char gender) {
        this.roll = Integer.toString(roll);
        this.name = name;
        this.gender = Character.toString(gender);
    }

    /**
     * @return the roll
     */
    public String getRoll() {
        return roll;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "Student [roll=" + roll + ", name=" + name + ", gender=" + gender + "]";
    }

}

class Operations {
    public static boolean insertDBS(Student s) {
        try {
            Connection con = Connections.buildConnection();
            PreparedStatement ps = con.prepareStatement("insert into student values(?,?,?);");
            ps.setString(1, s.getRoll());
            ps.setString(2, s.getName());
            ps.setString(3, s.getGender());
            con.prepareStatement("use dbsoperations;").executeUpdate();
            ps.executeUpdate();
            Connections.closeConnection(con);
            return true;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteDBS(String x) {
        try {
            Connection con = Connections.buildConnection();
            con.prepareStatement("use dbsoperations;").executeUpdate();
            PreparedStatement ps = con.prepareStatement("delete from student where roll=?");
            ps.setString(1, x);
            ps.executeUpdate();
            Connections.closeConnection(con);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }

    public static boolean showDBS() {
        try {
            Connection con = Connections.buildConnection();
            con.prepareStatement("use dbsoperations;").executeUpdate();
            ResultSet rs = con.createStatement().executeQuery("select * from student;");
            while (rs.next()) {
                System.out.println("id : " + rs.getString(1));
                System.out.println("name : " + rs.getString(2));
                System.out.println("gender : " + rs.getString(3));
                System.out.println("___________________________________________________________");

            }
            Connections.closeConnection(con);
            return true;

        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return false;
    }

    public static boolean updateDBS(String roll, String name, char gender) {
        try {
            Connection con = Connections.buildConnection();
            PreparedStatement ps = con.prepareStatement("update student set name=?,gender=? where roll=?");
            ps.setString(1, name);
            ps.setString(2, Character.toString(gender));
            ps.setString(3, roll);
            con.prepareStatement("use dbsoperations;").executeUpdate();
            ps.executeUpdate();
            Connections.closeConnection(con);
            return true;
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return false;
    }

    public static boolean updateDBS(String roll, String name) {
        try {
            Connection con = Connections.buildConnection();
            PreparedStatement ps = con.prepareStatement("update student set name=? where roll=?");
            ps.setString(1, name);
            ps.setString(2, roll);
            con.prepareStatement("use dbsoperations;").executeUpdate();
            ps.executeUpdate();
            Connections.closeConnection(con);
            return true;
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return false;
    }

    public static boolean updateDBS(String roll, char gender) {
        try {
            Connection con = Connections.buildConnection();
            PreparedStatement ps = con.prepareStatement("update student set gender=? where roll=?");
            ps.setString(1, Character.toString(gender));
            ps.setString(2, roll);
            con.prepareStatement("use dbsoperations;").executeUpdate();
            ps.executeUpdate();
            Connections.closeConnection(con);
            return true;
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return false;
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("1.insert\n2.delete\n3.show\n4.update\n5.exit");
            int opt = Integer.parseInt(sc.readLine());
            if (opt == 1) {
                System.out.println("Enter roll, name, gender : ");
                Student s = new Student(Integer.parseInt(sc.readLine()), sc.readLine(), sc.readLine().charAt(0));
                System.out.println(s);

                if (Operations.insertDBS(s)) {
                    System.out.println("Succesfully inserted");
                } else {
                    System.out.println("Insert Unsuccesfull");
                }
            } else if (opt == 2) {
                if (Operations.deleteDBS(Integer.toString(Integer.parseInt(sc.readLine())))) {
                    System.out.println("Succesfully deleted");
                } else {
                    System.out.println("Delete Unsuccesfull");
                }

            } else if (opt == 3) {
                Operations.showDBS();

            } else if (opt == 4) {
                System.out.println("Enter roll, name, gender : ");
                String i = sc.readLine();
                String n = sc.readLine();
                String g = sc.readLine();

                if (n.length() == 0 && g.length() == 0) {
                    System.out.println("Invalid Credentials");
                } else if (n.length() == 0) {
                    System.out.println("n null");

                    if (Operations.updateDBS(i, g.charAt(0))) {
                        System.out.println("Successfully updated");
                    } else {
                        System.out.println("Update unsuccessfull");
                    }
                } else if (g.length()== 0) {
                    if (Operations.updateDBS(i, n)) {
                        System.out.println("Successfully updated");
                    } else {
                        System.out.println("Update unsuccessfull");
                    }
                }

                else {
                    if (Operations.updateDBS(i, n, g.charAt(0))) {
                        System.out.println("Successfully updated");
                    } else {
                        System.out.println("Update unsuccessfull");
                    }

                }

            } else {
                break;
            }
        }
        System.out.println("Thank you");
        sc.close();

    }
}
