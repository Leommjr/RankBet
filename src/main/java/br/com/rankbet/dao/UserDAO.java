package br.com.rankbet.dao;

import br.com.rankbet.dao.base.BaseDao;
import br.com.rankbet.model.SubscriptionModel;
import br.com.rankbet.model.UserModel;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserDAO extends BaseDao<UserModel> {

    public UserDAO(){super(UserModel.class);}

    public UserModel findByEmail(String email){
        try{
            CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
            CriteriaQuery<UserModel> criteriaQuery = criteriaBuilder.createQuery(persistentClass);
            Root<UserModel> from = criteriaQuery.from(persistentClass);
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.equal(from.get("email"), email));
            criteriaQuery.where(predicateList.toArray(new Predicate[0]));
            TypedQuery<UserModel> q = this.getSession().createQuery(criteriaQuery);
            return q.getSingleResult();
        }
        catch (Exception e){
            return null;
        }
    }
}
