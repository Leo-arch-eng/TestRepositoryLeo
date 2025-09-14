package com.example.leo_forum_project_test.repository.mock;

import com.example.leo_forum_project_test.entity.TopicE;
import com.example.leo_forum_project_test.repository.TopicRepositoryV1;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TopicRepositoryV1Mock implements TopicRepositoryV1 {

    private final Map<Long, TopicE> topicMap;
    private static Long lastCreatedId = 0L;

    public TopicRepositoryV1Mock() {
        this.topicMap = new HashMap<>();
    }

    @Override
    public TopicE create(TopicE topicE) {
            Long newId = lastCreatedId++;
            topicE.setId(newId);
            topicMap.put(newId, topicE);
            System.out.println(
                    "Добавлено топик с идентификатором: + " + lastCreatedId + " = " + topicE
            );
            return topicE;
    }

    @Override
    public TopicE findById(Long topicId) {
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
    public TopicE update(Long topicId, TopicE topicE) {
            if (topicMap.containsKey(topicId)) {
                topicMap.put(topicId, topicE);
                System.out.println("Топик с идентификатором: " + topicId + " был обновлен");
            }
        return topicE;
    }
}
