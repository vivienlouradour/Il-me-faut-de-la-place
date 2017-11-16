package IHM.Test;

import Core.ApplicationDirectoryUtilities;

public class TestTemp {
    public static void main(String[] args){
        String test = ApplicationDirectoryUtilities.getProgramDirectory();
        System.out.println("test = " + test);
    }
}
