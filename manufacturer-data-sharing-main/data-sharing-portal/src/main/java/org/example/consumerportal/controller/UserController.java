package org.example.consumerportal.controller;

import org.apache.commons.lang3.StringUtils;
import org.example.consumerportal.request.model.UserModel;
import org.example.consumerportal.response.model.UserDataModel;
import org.example.consumerportal.service.UserDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserDataServiceImpl userDataService;

    @GetMapping("/user")
    public String getLogin(@ModelAttribute("userModel") UserModel client, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        if(user != null && user.getAuthorities() != null
                && user.getAuthorities().size() > 0) {
            Optional<GrantedAuthority> authority = user.getAuthorities().stream().findFirst();
            if(authority.isPresent()){
                UserDataModel dataModel = userDataService.getUserDataByProducerUsernme(user.getUsername());
                model.addAttribute("name", user.getUsername());
                model.addAttribute("dataModel", dataModel);

                String getTabNameToDisplay = StringUtils.EMPTY;
                if (StringUtils.isNotBlank(client.getTabNameToDisplayRequestReceived())) {
                    getTabNameToDisplay = client.getTabNameToDisplayRequestReceived();
                } else if (StringUtils.isNotBlank(client.getTabNameToDisplayRequestSent())) {
                    getTabNameToDisplay = client.getTabNameToDisplayRequestSent();
                } else {
                    getTabNameToDisplay = "CommonData";
                }

                model.addAttribute("tabNameToDisplay", getTabNameToDisplay);
                return "auth/user_home";
            }
        }

        model.addAttribute("error", "Login failed");
        return "login";
    }

}
