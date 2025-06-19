package com.example.leo_forum_project_test.repository.mock;

import com.example.leo_forum_project_test.dto.Topic;
import com.example.leo_forum_project_test.repository.TopicRepositoryV1;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TopicRepositoryV1Mock implements TopicRepositoryV1 {

    private final Map<Long, Topic> topicMap;
    private static Long lastCreatedId = 0L;

    public TopicRepositoryV1Mock() {
        this.topicMap = new HashMap<>();
    }

    @Override
    public Topic create(Topic topic) {
            Long newId = lastCreatedId++;
            topic.setId(newId);
            topicMap.put(newId,topic);
            System.out.println(
                    "Добавлено топик с идентификатором: + " + lastCreatedId + " = " + topic
            );
            return topic;
    }

    @Override
    public Topic findById(Long topicId) {
        return topicMap.get(topicId);
    }

    @Override
    public void deleteById(Long topicId) {
        if (topicMap.remove(topicId) != null) {
            System.out.println("Был удален топик c идентификатором: " + topicId);
        } else {
            System.out.println("Топик с идентификатором: " + topicId + " не найден");
        }
    }

    @Override
    public Topic update(Long topicId, Topic topic) {
            if (topicMap.containsKey(topicId)) {
                topicMap.put(topicId, topic);
                System.out.println("Топик с идентификатором: " + topicId + " был обновлен");
            }
        return topic;
    }
}
