package by.bsu.zakharankou.restservices.controller.topic;

import by.bsu.zakharankou.restservices.controller.view.ListView;
import by.bsu.zakharankou.restservices.model.topic.Topic;
import by.bsu.zakharankou.restservices.service.serviceapi.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing topics.
 */
@Controller
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /**
     * Gets list of all topics.
     *
     * @return {@link List} of {@link Topic}
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ListView<Topic> getTopics() {
        List<Topic> topics = topicService.getTopics();
        return new ListView<>(new ArrayList<>(topics), topics.size());
    }
}
