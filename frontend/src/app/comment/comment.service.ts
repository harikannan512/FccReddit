import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentPayload } from './comment-payload';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }

  postComment(comment: CommentPayload){
    return this.http.post<any>('http://localhost:8080/api/comments/', comment);
  }

  getAllCommentsForPost(postId: number): Observable<CommentPayload[]>{
    return this.http.get<CommentPayload[]>('http://localhost:8080/api/comments/by-post/' + postId);
  }


  getAllCommentsByUser(name: string): Observable<CommentPayload[]> {
    return this.http.get<CommentPayload[]>('http://localhost:8080/api/comments/by-user/' + name);
  }
}
