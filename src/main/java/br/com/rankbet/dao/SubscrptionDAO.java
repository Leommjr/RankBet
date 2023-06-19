package br.com.rankbet.dao;

import br.com.rankbet.dao.base.BaseDao;
import br.com.rankbet.model.SubscriptionModel;
import br.com.rankbet.model.UserModel;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SubscrptionDAO extends BaseDao<SubscriptionModel> {

    public SubscrptionDAO(){super(SubscriptionModel.class);}


    public SubscriptionModel getSubscription(long userId) {
        return  new SubscriptionModel();
    }

    public SubscriptionModel findByUser(long id){
        CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
        CriteriaQuery<SubscriptionModel> criteriaQuery = criteriaBuilder.createQuery(persistentClass);
        Root<SubscriptionModel> from = criteriaQuery.from(persistentClass);
        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(criteriaBuilder.equal(from.get("userId"), id));
        criteriaQuery.where(predicateList.toArray(new Predicate[0]));
        TypedQuery<SubscriptionModel> q = this.getSession().createQuery(criteriaQuery);
        List<SubscriptionModel> s = q.getResultList();
        return s.get(s.size() -1);
    }

    public List<SubscriptionModel> findByExpiredAt(){
        CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
        CriteriaQuery<SubscriptionModel> criteriaQuery = criteriaBuilder.createQuery(persistentClass);
        Root<SubscriptionModel> from = criteriaQuery.from(persistentClass);

        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(criteriaBuilder.lessThan(from.get("expiresAt"), LocalDateTime.now()));

        criteriaQuery.where(predicateList.toArray(new Predicate[0]));

        TypedQuery<SubscriptionModel> q = this.getSession().createQuery(criteriaQuery);
        return q.getResultList();
    }
}
