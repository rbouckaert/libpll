package pll;

import java.util.List;

/**
 * @author Marc A. Suchard
 * @author Andrew Rambaut
 */
public class PLLInfo {

    public static String getVersion() {
        return PLLFactory.getVersion();
    }

    public static void printVersionInformation() {

        System.out.println(PLLFactory.getVersionInformation());
        System.out.println();
    }

    public static void printResourceList() {

        List<ResourceDetails> resourceDetails = PLLFactory.getResourceDetails();

        System.out.println("PLL resources available:");
        for (ResourceDetails resource : resourceDetails) {
            System.out.println(resource.toString());
            System.out.println();

        }
    }

    public static void main(String[] argv) {
        printVersionInformation();
        printResourceList();
    }

}
