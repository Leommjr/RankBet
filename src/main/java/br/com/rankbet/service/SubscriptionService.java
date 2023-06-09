package br.com.rankbet.service;

import br.com.rankbet.dao.SubscrptionDAO;
import br.com.rankbet.model.SubscriptionModel;

import java.util.List;

public class SubscriptionService {
    private static SubscrptionDAO subscrption = new SubscrptionDAO();


    public SubscriptionModel getSubscription(long userId){


        return subscrption.findByUser(userId);

    }

    public List<SubscriptionModel> getUsersExpireds() {
        return subscrption.findByExpiredAt();
    }
}