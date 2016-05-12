package HibFiles;
// Generated May 12, 2016 3:57:54 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SystemStatus generated by hbm2java
 */
public class SystemStatus  implements java.io.Serializable {


     private Integer idsystemStatus;
     private String statusName;
     private Set<UserLogin> userLogins = new HashSet<UserLogin>(0);

    public SystemStatus() {
    }

    public SystemStatus(String statusName, Set<UserLogin> userLogins) {
       this.statusName = statusName;
       this.userLogins = userLogins;
    }
   
    public Integer getIdsystemStatus() {
        return this.idsystemStatus;
    }
    
    public void setIdsystemStatus(Integer idsystemStatus) {
        this.idsystemStatus = idsystemStatus;
    }
    public String getStatusName() {
        return this.statusName;
    }
    
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    public Set<UserLogin> getUserLogins() {
        return this.userLogins;
    }
    
    public void setUserLogins(Set<UserLogin> userLogins) {
        this.userLogins = userLogins;
    }




}


