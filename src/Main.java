import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

/**
 * Created by scofieldservices on 12/5/16.
 */
public class Main {
    public static User user;

    public static void main (String[]args){

        Spark.init();
        // lambda to build an object for handing hashmap data to the html file
        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    if(user == null) {
                        return new ModelAndView(m, "login.html");
                    }else{
                        m.put("name", user.name);
                        return new ModelAndView(m, "home.html");
                    }
        }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    user = new User(name);
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/logout",
                ((request, response) -> {
                    user = null;
                    response.redirect("/");
                    return "";
                })
        );
    }
}
