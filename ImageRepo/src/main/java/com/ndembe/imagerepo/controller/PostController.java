package com.ndembe.imagerepo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ndembe.imagerepo.domain.Post;
import com.ndembe.imagerepo.repositories.PostRepository;

import javassist.NotFoundException;

@Controller
public class PostController {
	
	@Autowired
	private PostRepository postRepo;
	
	@GetMapping("/")
	public String getPosts(ModelMap model, HttpServletResponse response) {
		
		List<Post> posts = postRepo.findAll();
		model.put("posts", posts);
		return "index";
	}          
	
	@GetMapping("/posts/{postId}")
	public void getPosts(@PathVariable Long postId, ModelMap model, HttpServletResponse response) throws IOException {
		Optional<Post> postOpt = postRepo.findById(postId);
		if (postOpt.isPresent()) {
			Post post = postOpt.get();
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		    response.getOutputStream().write(post.getImage());
		    response.getOutputStream().close();
		} else {
			response.sendError(HttpStatus.NOT_FOUND.value(), "Post with id " + postId + " was not found");
		}
	}
	
//	@PostMapping("/posts")
//	public String createPost() {
//		Post post = new Post();
//		post.setDescription("Test");
//		post.setImage(null);
//		post.setPublic(true);
//		post.setUser(null);
//		
//		post = postRepo.save(post);
//		
//		return "redirect:/posts/" + post.getId();
//	}
	
//	@GetMapping(value = "/posts")
//	public @ResponseBody byte[] getImage() throws IOException {
//		List<Post> posts = postRepo.findAll();
//		if (postOpt.isPresent()) {
//			model.put("post", postOpt.get());
//		} else {
//			response.sendError(HttpStatus.NOT_FOUND.value(), "Post with id " + postId + " was not found");
//			return "post";
//		}
//		return "post";
//	}
	
	@PostMapping("/posts")
    public String singleFileUpload(@RequestParam("image") MultipartFile file) {
        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            
            Post post = new Post();
            post.setImage(bytes);
            post.setTitle(StringUtils.cleanPath(file.getOriginalFilename()));
            post.setPublic(true);
            post = postRepo.save(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:";
    }
	
//	@GetMapping("/uploadStatus")
//    public String uploadStatus() {
//        return "uploadStatus";
//    }
}
