package com.manos.prototype.finder;
 
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Activity;
import com.pastelstudios.db.AbstractFinder;
 
@Repository
public class ActivityFinder extends AbstractFinder {
    
    public List<Activity> getActivitiesByUserId(long userId) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from Activity a "
                + "join fetch a.action "
                + "join fetch a.user "
                + "where a.user.id = :userId "
                + "order by a.id desc");
        Query<Activity> theQuery = createQuery(queryBuilder.toString(), Activity.class);
        theQuery.setParameter("userId", userId);
        theQuery.setMaxResults(15);
        
        return theQuery.getResultList();
    }
 
}