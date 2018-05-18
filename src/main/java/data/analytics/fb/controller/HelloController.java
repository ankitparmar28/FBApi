package data.analytics.fb.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@ComponentScan("data.analytics.fb")
public class HelloController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public HelloController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            System.out.println("Connection not found");
        	return "facebookConnect";
        }
        System.out.println("Connected");
        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("feed", feed);
        return "hello";
    }

}