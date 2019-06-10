package kosiorek.michal;

import kosiorek.michal.connection.DbJdbiConnection;
import kosiorek.michal.service.MenuService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        DbJdbiConnection jdbi = DbJdbiConnection.getInstance();

        MenuService menuService = new MenuService();
        menuService.mainMenu();
    }
}
