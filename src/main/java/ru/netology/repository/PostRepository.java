package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private final List<Post> posts = new CopyOnWriteArrayList<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  public List<Post> all() {
    return Collections.unmodifiableList(posts);
  }

  public Optional<Post> getById(long id) {
    return posts.stream()
            .filter(post -> post.getId() == id)
            .findFirst();
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      // New post, assign a new id
      post.setId(idCounter.getAndIncrement());
      posts.add(post);
    } else {
      // Update existing post
      posts.stream()
              .filter(p -> p.getId() == post.getId())
              .findFirst()
              .ifPresent(existingPost -> {
                existingPost.setContent(post.getContent());
              });
    }
    return post;
  }

  public void removeById(long id) {
    posts.removeIf(post -> post.getId() == id);
  }
}
