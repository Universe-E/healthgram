package com.example.gp.Items;

public class Notification {
    private Post post;
    private boolean visited;

    public Notification(Post post, boolean visited) {
        this.post = post;
        this.visited = visited;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
