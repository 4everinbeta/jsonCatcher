package com.foureverinbeta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ryan on 9/8/16.
 */
@RestController
public class JsonCatcherController {

    @Autowired
    JsonCatcherRepository jsonCatcherRepository;

    private static final String template = "Got it!, %s!";

    @RequestMapping(value = "/jsonCaught", method = RequestMethod.GET)
    public List<JsonCaught> jsonCatcher() {

        return jsonCatcherRepository.findAll();
    }

    @RequestMapping(value = "/jsonCaught/{id}", method = RequestMethod.GET)
    public JsonCaught jsonCatcher(@PathVariable Integer id) {

        return jsonCatcherRepository.findUserById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void jsonCatcher(@RequestBody String jsonToCatch) {
        JsonCaught newJsonCaught = new JsonCaught();
        newJsonCaught.setPayload(jsonToCatch);
        JsonCaught newJsoncaughtWithId = jsonCatcherRepository.create(newJsonCaught);
        System.out.println(newJsoncaughtWithId.toString());
    }
}
