package com.embarkx.blogapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogController {

    private static final int MAX_CONTENT_LENGTH = 5000;

    private static List<String> posts = new ArrayList<>();

    @PostMapping
    public String createPost(@RequestParam String title, @RequestParam String content) {
        if (title.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title must not be empty");
        }
        if (content.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content must not be empty");
        }
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Content must be at most " + MAX_CONTENT_LENGTH + " characters");
        }
        String post = title + ":" + content;
        posts.add(post);
        return "Post created";
    }

    @GetMapping
    public List<String> getAllPosts() {
        return posts;
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable int id) {
        checkPostExists(id);
        return posts.get(id);
    }

    @PostMapping("/validate")
    public String validateContent(@RequestParam String content) {
        if (content.length() > MAX_CONTENT_LENGTH) {
            return "Too long";
        }
        return "OK";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable int id) {
        checkPostExists(id);
        posts.remove(id);
        return "Deleted";
    }

    private void checkPostExists(int id) {
        if (id < 0 || id >= posts.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id " + id);
        }
    }

@GetMapping("/total")
public String getTotalWordCount() {
    List<String> wordCounts = List.of("100", "200", "300");
    String total = "";
    for (String count : wordCounts) {
        total += count;
    }
    return "Total words: " + total;
}
}