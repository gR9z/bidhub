package fr.eni.tp.auctionapp.dal;

import fr.eni.tp.auctionapp.bo.Item;

import java.util.List;
import java.util.Optional;

public interface ItemDao {
    void insert(Item item);

    Optional<Item> findAllPagination(int id);

    void update(Item item);

    void deleteById(int itemId);

    List<Item> findAll();

    List<Item> findAllPaginated(int page, int size);

    List<Item> findAllByUserIdPaginated(int userId, int page, int size);

    int countByUserId(int userId);

    int count();
}
