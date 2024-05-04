package fr.eni.tp.auctionapp.dal;

import fr.eni.tp.auctionapp.bo.Auction;

import java.util.List;
import java.util.Optional;

public interface AuctionDao {
    void insert(Auction auction);
    Optional<Auction> readByAuctionId(int auctionId);
    List<Optional<Auction>> readByItemIdPaginated(int itemId, int page, int size);
    List<Optional<Auction>> readByUserIdPaginated(int userId, int page, int size);
    int countByItemId(int itemId);
    int countByItemIdAndUserId(int itemId, int userId);
    void deleteByAuctionId(int auctionId);
    int count();
}
