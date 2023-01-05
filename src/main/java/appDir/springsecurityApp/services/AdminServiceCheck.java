package appDir.springsecurityApp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceCheck {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void doAdminStuff() {
        System.out.println("Админ");
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR') or ('ROLE_ADMIN')")
    public void doModeratorStuff() {
        System.out.println("Модератор");
    }
}
