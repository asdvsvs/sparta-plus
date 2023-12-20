package com.sparta.plus.service.scheduler;

import com.sparta.plus.entity.Post;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.service.interfaces.PostService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j(topic = "PostScheduler")
@Component
@RequiredArgsConstructor
public class PostScheduler {

    private final PostService postService;
    private final PostRepository postRepository;

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 * * * *") // 매 시간 정각에
    public void deletePost() throws InterruptedException {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            LocalDateTime createdTime = post.getCreatedTime();
            LocalDateTime scheduleTime = LocalDateTime.now().minusHours(1);
            if (scheduleTime.isAfter(createdTime)) {
                postRepository.delete(post);
            }
        }

    }
}