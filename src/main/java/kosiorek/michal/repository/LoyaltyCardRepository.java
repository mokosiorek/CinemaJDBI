package kosiorek.michal.repository;

import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.model.LoyaltyCard;

public class LoyaltyCardRepository extends AbstractCrudRepository<LoyaltyCard, Integer> {

    @Override
    public void add(LoyaltyCard loyaltyCard) {

        if (loyaltyCard == null) {
            throw new MyException(ExceptionCode.OTHER, "LOYALTY CARD OBJECT IS NULL");
        }

        //final String sql = "insert into loyalty_card ( movies_number, discount, expiration_date ) values (?,?,?)";
        final String sql = "insert into loyalty_card ( movies_number, discount, expiration_date ) values (:movies_number,:discount,:expiration_date)";

        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("movies_number", loyaltyCard.getMoviesNumber())
                .bind("discount", loyaltyCard.getDiscount())
                .bind("expiration_date", loyaltyCard.getExpirationDate())
                .execute());

    }

    @Override
    public void update(LoyaltyCard loyaltyCard) {

        if (loyaltyCard == null) {
            throw new MyException(ExceptionCode.OTHER, "LOYALTY CARD OBJECT IS NULL");
        }

        //final String sql = "update loyalty_card set movies_number=?,discount=?,expiration_date=? where id=?";
        final String sql = "update loyalty_card set movies_number=:movies_number,discount=:discount,expiration_date=:expiration_date where id=:id";

        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("movies_number", loyaltyCard.getMoviesNumber())
                .bind("discount", loyaltyCard.getDiscount())
                .bind("expiration_date", loyaltyCard.getExpirationDate())
                .bind("id", loyaltyCard.getId())
                .execute());

    }

    public int addAndReturnId(LoyaltyCard loyaltyCard) {

        if (loyaltyCard == null) {
            throw new MyException(ExceptionCode.OTHER, "LOYALTY CARD OBJECT IS NULL");
        }

        final String sql = "insert into loyalty_card ( movies_number, discount, expiration_date ) values (:movies_number, :discount, :expiration_date)";

        return jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("movies_number", loyaltyCard.getMoviesNumber())
                .bind("discount", loyaltyCard.getDiscount())
                .bind("expiration_date", loyaltyCard.getExpirationDate())
                .executeAndReturnGeneratedKeys()
                .mapToBean(LoyaltyCard.class))
                .findFirst()
                .map(LoyaltyCard::getId).orElseThrow(()->new MyException(ExceptionCode.OTHER,"LOYALTY CARD GET ID ERROR"));

    }

}
