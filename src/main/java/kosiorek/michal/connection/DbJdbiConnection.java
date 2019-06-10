package kosiorek.michal.connection;

import org.jdbi.v3.core.Jdbi;

public class DbJdbiConnection {
    private static DbJdbiConnection ourInstance = new DbJdbiConnection();

    public static DbJdbiConnection getInstance() {
        return ourInstance;
    }

    private Jdbi jdbi;

    private DbJdbiConnection() {

        jdbi = Jdbi.create(
                "jdbc:mysql://localhost:3306/jdbi_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "root");

        createTables();
    }

    public Jdbi getJdbi() {
        return jdbi;
    }

    private void createTables() {

        final String moviesSql = SqlCommand.builder()
                .table("movie")
                .primaryKey("id")
                .stringColumn("title", 50, "not null")
                .stringColumn("genre", 50, "not null")
                .decimalColumn("price", 4, 2, "not null")
                .intColumn("duration", "not null")
                .column("release_date", "Date", "not null")
                .build()
                .toSql();

        final String loyaltyCardSql = SqlCommand.builder()
                .table("loyalty_card")
                .primaryKey("id")
                .column("expiration_date", "Date", "not null")
                .decimalColumn("discount", 2, 0, "not null")
                .intColumn("movies_number", "not null")
                .build()
                .toSql();

        final String customersSql = SqlCommand.builder()
                .table("customer")
                .primaryKey("id")
                .stringColumn("name", 50, "not null")
                .stringColumn("surname", 50, "not null")
                .intColumn("age", "not null")
                .stringColumn("email", 50, "not null")
                .intColumn("loyalty_card_id","")
                .foreignKey("loyalty_card_id", "loyalty_card", "id", "on delete cascade on update cascade")
                .build()
                .toSql();


        final String salesStandSql = SqlCommand.builder()
                .table("sales_stand")
                .primaryKey("id")
                .intColumn("customer_id","not null")
                .foreignKey("customer_id","customer","id","on delete cascade on update cascade")
                .intColumn("movie_id","not null")
                .foreignKey("movie_id","movie","id","on delete cascade on update cascade")
                .column("start_date_time","timestamp","not null")
                .build()
                .toSql();


        jdbi.useHandle(handle -> handle.execute(moviesSql));
        jdbi.useHandle(handle -> handle.execute(loyaltyCardSql));
        jdbi.useHandle(handle -> handle.execute(customersSql));
        jdbi.useHandle(handle -> handle.execute(salesStandSql));
    }

}
