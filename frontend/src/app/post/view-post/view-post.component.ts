import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { faArrowDown, faArrowUp, faComments } from '@fortawesome/free-solid-svg-icons';
import { throwError } from 'rxjs';
import { CommentPayload } from 'src/app/comment/comment-payload';
import { CommentService } from 'src/app/comment/comment.service';
import { PostModel } from 'src/app/shared/post-model';
import { PostService } from 'src/app/shared/post.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {

  postId!: number;
  post!: PostModel;

  commentForm!: FormGroup;
  commentPayload!: CommentPayload;
  comments!: CommentPayload[];

  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  faComments = faComments;



  constructor(
    private postService: PostService,
    private activatedRoute: ActivatedRoute,
    private commentService: CommentService
  ) {
    this.postId = this.activatedRoute.snapshot.params.id;

    this.postService.getPost(this.postId).subscribe(data => {
      this.post = data;
    
    this.commentForm = new FormGroup({
        text: new FormControl('', Validators.required)
    })
    }, error => {
      throwError(error);
    });

    this.commentPayload = {
      text: '',
      postId: this.postId
    }
    
  }

  ngOnInit(): void {
    this.getPostById();
    this.getCommentsForPost();
  }

  postComment(){
    this.commentPayload.text = this.commentForm.get('text')?.value;

    this.commentService.postComment(this.commentPayload).subscribe(data => {
      this.commentForm.get('text')?.setValue('');
      this.getCommentsForPost();
    }, error => {
      throwError(error);
    })
  }
  getCommentsForPost() {
    return this.commentService.getAllCommentsForPost(this.postId).subscribe(data => {
      this.comments = data;
    })
  }

  getPostById(){
    return this.postService.getPost(this.postId).subscribe(data => {
      this.post = data;
    }, error => {
      throwError(error);
    })
  }

}
