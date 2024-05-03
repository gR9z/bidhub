package fr.eni.tp.auctionapp.bll.impl;

import fr.eni.tp.auctionapp.bll.ItemService;
import fr.eni.tp.auctionapp.bo.Category;
import fr.eni.tp.auctionapp.bo.Item;
import fr.eni.tp.auctionapp.dal.ItemDao;
import fr.eni.tp.auctionapp.dal.WithdrawalDao;
import fr.eni.tp.auctionapp.exceptions.BusinessCode;
import fr.eni.tp.auctionapp.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemDao itemDao;
    private WithdrawalDao withdrawalDao;

    public ItemServiceImpl(ItemDao itemDao, WithdrawalDao withdrawalDao) {

        this.itemDao = itemDao;
        this.withdrawalDao = withdrawalDao;
    }

    @Override
    @Transactional
    public void createItem(Item item) {
        BusinessException businessException = new BusinessException();

        boolean isValid = true;
        isValid &= isItemNameValid(item.getItemName(), businessException);
        isValid &= isDescriptionValid(item.getDescription(), businessException);
        isValid &= isAuctionStartingDateValid(item.getAuctionStartingDate(), businessException);
        isValid &= isAuctionEndingDateValid(item.getAuctionEndingDate(), item.getAuctionEndingDate(), businessException);
        isValid &= isCategoryValid(item.getCategory(), businessException);

        if(isValid){
            try{





                // Ajouter la partie où on sépare item de withdrawal pour faire deux objets

                //withdrawalDao.insert(withdrawal)

                itemDao.insert(item);

            } catch(BusinessException dalBusinessException){
                throw dalBusinessException;
            }
        } else {
            throw businessException;
        }

    }

    //Item Name
    private boolean isItemNameValid(String itemName, BusinessException businessException){
        if(itemName == null){
            businessException.addKey(BusinessCode.VALIDATION_ITEM_NAME_NULL);
            return false;
        }

        if(itemName.length() > 30){
            businessException.addKey(BusinessCode.VALIDATION_ITEM_NAME_SIZE);
            return false;
        }

        return true;
    }


    //Description
    private boolean isDescriptionValid(String description, BusinessException businessException){
        if(description == null){
            businessException.addKey(BusinessCode.VALIDATION_DESCRIPTION_NULL);
            return false;
        }

        if(description.length() > 300){
            businessException.addKey(BusinessCode.VALIDATION_DESCRIPTION_SIZE);
            return false;
        }

        return true;
    }


    //Auction Starting Date
    private boolean isAuctionStartingDateValid(LocalDateTime auctionStartingDate, BusinessException businessException){
        if(auctionStartingDate == null){
            businessException.addKey(BusinessCode.VALIDATION_AUCTION_STARTING_DATE_NULL);
            return false;
        }

        return true;
    }


    //Auction Ending Date
    private boolean isAuctionEndingDateValid(LocalDateTime auctionStartingDate, LocalDateTime auctionEndingDate, BusinessException businessException){
        if(auctionEndingDate == null){
            businessException.addKey(BusinessCode.VALIDATION_AUCTION_ENDING_DATE_NULL);
            return false;
        }

        if(auctionEndingDate.isBefore(auctionStartingDate) ){
            businessException.addKey(BusinessCode.VALIDATION_AUCTION_ENDING_DATE_AFTER_AUCTION_STARTING_DATE);
            return false;
        }

        return true;
    }


    //Category
    private boolean isCategoryValid(Category category, BusinessException businessException){
        if(category == null){
            businessException.addKey(BusinessCode.VALIDATION_CATEGORY_NULL);
            return false;
        }

        return true;
    }

    @Override
    public Optional<Item> read(int id) {
        return itemDao.read(id);
    }
}
