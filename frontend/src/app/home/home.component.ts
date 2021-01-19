import { Component, OnInit } from '@angular/core';
import { SharedService } from '../auth/shared.service';
import { PostModel } from '../shared/post-model';
import { PostService } from '../shared/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  posts$: Array<PostModel> = [];

  constructor(
    private sharedService: SharedService, 
    private postService: PostService
    ) {
    this.postService.getAllPosts().subscribe(post => {
      this.posts$ = post;
    });
   }

  ngOnInit(): void {
  
  }

}
