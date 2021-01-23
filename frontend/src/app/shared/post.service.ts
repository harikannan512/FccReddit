import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreatePostPayload } from '../post/create-post/create-post.payload';
import { PostModel } from './post-model';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  getPost(postId: number): Observable<PostModel> {
    return this.httpClient.get<PostModel>('http://localhost:8080/api/posts/'+postId)
  }

  constructor(
    private httpClient: HttpClient
    ) { }


  getAllPosts(): Observable<any>{
    return this.httpClient.get('http://localhost:8080/api/posts');
  } 

  createPost(post: CreatePostPayload){
    return this.httpClient.post('http://localhost:8080/api/posts/', post);
  }

  getAllPostsByUser(name: string): Observable<PostModel[]> {
    return this.httpClient.get <PostModel[]> ('http://localhost:8080/api/posts/by-user/' + name);
  }
}