/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbtester;
import com.abdullah.LibrarySessionBean;
import   com.abdullah.LibrarySessionBeanRemote;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author mac
 */
public class EjbTester {
    BufferedReader brConsoleReader = null;
    Properties props;
    InitialContext ctx;{
    props = new Properties();  
    try{
        props.load(new  FileInputStream("jndi.properties"));
      //  props.put("jboss.naming.client.ejb.context", true);
    }
    catch(IOException e){
      e.printStackTrace();
    }
    try{
        ctx = new  InitialContext(props);
        
    }
    catch(NamingException e){
        e.printStackTrace();
    }
    
    brConsoleReader = new BufferedReader(new InputStreamReader(System.in));
}
     private void showGUI(){
      System.out.println("**********************");
      System.out.println("Welcome to Book Store");
      System.out.println("**********************");
      System.out.print("Options \n1. Add Book\n2. Exit \nEnter Choice: ");
   }
    public static void main(String[] args) {
       EjbTester ejb = new EjbTester();
       ejb.testStateLessEjb();
    }
    private void  testStateLessEjb(){
        try{
        int choice=1;
        final String appName = "";
        // This is the module name of the deployed EJBs on the server. This is typically the jar name of the
        // EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
        // In this example, we have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module name is
        // jboss-as-ejb-remote-app
        final String moduleName = "EjbComponent";
        // AS7 allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
        // our EJB deployment, so this is an empty string
        final String distinctName = "";
        // The EJB name which by default is the simple class name of the bean implementation class
        final String beanName = LibrarySessionBean.class.getSimpleName();
        // the remote view fully qualified class name
        final String viewClassName = LibrarySessionBeanRemote.class.getName();
        
        LibrarySessionBeanRemote libraryBean = 
            
            (LibrarySessionBeanRemote) ctx.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName + "?stateless");
                  // (LibrarySessionBeanRemote) ctx.lookup("ejb:LibrarySessionBean/remote");
       
        while(choice!=2){
             String bookName;
             showGUI();
             String strChoice = brConsoleReader.readLine();
             choice =  Integer.parseInt(strChoice);
             if(choice==1){
                 System.out.println("Enter Book Name:");
                 bookName = brConsoleReader.readLine();
                 libraryBean.addBook(bookName);
                 System.out.println(libraryBean.getBooks());
             }
             else{
             if(choice==2){
             break; 
           // ctx.close();
            }
             }
          // List<String>   bookList =   libraryBean.getBooks();
           //for(String book: bookList){
           //    System.out.println(book);
             //  ctx.close();
          // }
         }
        }
       
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
