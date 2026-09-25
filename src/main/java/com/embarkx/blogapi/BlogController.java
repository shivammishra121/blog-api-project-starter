package com.embarkx.blogapi;

import com.embarkx.blogapi.dto.CreatePostRequest;
import com.embarkx.blogapi.dto.PostResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/posts")
public class BlogController {

    // Keyed by a stable id so deleting one post doesn't change the ids of others
    private final Map<Integer, PostResponse> posts = new ConcurrentSkipListMap<>();
    private final AtomicInteger nextId = new AtomicInteger(0);

    @Value("${blog.post.max-content-length}")
    private int maxContentLength;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@Valid @RequestBody CreatePostRequest request) {
        PostResponse post = new PostResponse(nextId.getAndIncrement(), request.title(), request.content());
        posts.put(post.id(), post);
        return post;
    }

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return new ArrayList<>(posts.values());
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable int id) {
        return findPost(id);
    }

    @PostMapping("/validate")
    public String validateContent(@RequestParam String content) {
        if (content.length() > maxContentLength) {
            return "Too long";
        }
        return "OK";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable int id) {
        findPost(id);
        posts.remove(id);
        return "Deleted";
    }

    private PostResponse findPost(int id) {
        PostResponse post = posts.get(id);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id " + id);
        }
        return post;
    }

    @GetMapping("/total")
    public String getTotalWordCount() {
        List<Integer> wordCounts = List.of(100, 200, 300);
        int total = 0;
        for (int count : wordCounts) {
            total += count;
        }
        return "Total words: " + total;
    }
}
