package model;

/*
 * Singleton class
 */

public class SuperAdmin extends Admin {
    private static final long serialVersionUID = 6L;
    //Author : Xi Zhang. Copying my code will be a serious issue :)
    private static SuperAdmin superAdmin = new SuperAdmin(
            "superAdmin", "r9h2b", "Xi","Male",20);
    public static final SuperAdmin SUPER_ADMIN = superAdmin;

    private SuperAdmin(String username, String password, String name, String gender, int age) {
        super(username, password, name, gender, age);
    }
}
