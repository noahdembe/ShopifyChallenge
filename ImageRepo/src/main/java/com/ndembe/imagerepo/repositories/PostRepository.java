package com.ndembe.imagerepo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ndembe.imagerepo.domain.Post;

public interface PostRepository extends JpaRepository <Post, Long>{

}
