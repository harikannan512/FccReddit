import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { SubredditModel } from '../subreddit-model';
import { SubredditService } from '../subreddit.service';

@Component({
  selector: 'app-create-subreddit',
  templateUrl: './create-subreddit.component.html',
  styleUrls: ['./create-subreddit.component.css']
})
export class CreateSubredditComponent implements OnInit {

  subredditForm!: FormGroup;

  subredditModel!: SubredditModel;

  constructor(
    private router: Router, 
    private subredditService: SubredditService
    ) {
    this.subredditModel = {
      name: '',
      description: ''
    }
  }

  ngOnInit(): void {
    this.subredditForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
  }

  discard(){
    this.router.navigateByUrl('/');
  }

  createSubreddit(){
    this.subredditModel.name = this.subredditForm.get('title')?.value;
    this.subredditModel.description = this.subredditForm.get('description')?.value;
    
    this.subredditService.createSubreddit(this.subredditModel).subscribe(() => {
      this.router.navigateByUrl('/list-subreddits')
    }, error => {
      console.log('Error in creating Subreddit');
      throwError(error);
    })
  }
  

}
