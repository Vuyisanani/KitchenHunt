package HibFiles;
// Generated May 12, 2016 3:57:54 PM by Hibernate Tools 4.3.1



/**
 * MyKitchen generated by hbm2java
 */
public class MyKitchen  implements java.io.Serializable {


     private Integer idmyKitchen;
     private Recipe recipe;
     private User user;

    public MyKitchen() {
    }

    public MyKitchen(Recipe recipe, User user) {
       this.recipe = recipe;
       this.user = user;
    }
   
    public Integer getIdmyKitchen() {
        return this.idmyKitchen;
    }
    
    public void setIdmyKitchen(Integer idmyKitchen) {
        this.idmyKitchen = idmyKitchen;
    }
    public Recipe getRecipe() {
        return this.recipe;
    }
    
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }




}


