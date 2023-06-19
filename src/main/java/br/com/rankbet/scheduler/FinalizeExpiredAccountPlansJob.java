package br.com.rankbet.scheduler;

import br.com.rankbet.enums.AccountType;
import br.com.rankbet.model.SubscriptionModel;
import br.com.rankbet.service.RoleService;
import br.com.rankbet.service.SubscriptionService;
import br.com.rankbet.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

public class FinalizeExpiredAccountPlansJob implements Job{


    private UserService userService;

    private SubscriptionService subscriptionService;

    private RoleService roleService;


    public FinalizeExpiredAccountPlansJob(){
        userService = new UserService();
        subscriptionService = new SubscriptionService();
        roleService =  new RoleService();
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        var expiredsList =  subscriptionService.getUsersExpireds();
        var freeRole = roleService.findRole(AccountType.FREE.getType());

        for(SubscriptionModel subscription : expiredsList){
            subscription.setRoleId(freeRole.getId());
            subscription.setExpiresAt(null);
            subscription.setUpdateAt(LocalDateTime.now());
            subscription.setPrice(0.0f);
        }
    }
}
