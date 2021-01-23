import { Component, Input, OnInit } from '@angular/core';
import { faArrowDown, faArrowUp } from '@fortawesome/free-solid-svg-icons';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';
import { SharedService } from 'src/app/auth/shared.service';
import { PostModel } from '../post-model';
import { PostService } from '../post.service';
import { VotePayload } from '../vote-payload';
import { VoteType } from '../vote-type';
import { VoteService } from '../vote.service';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {

  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;

  votePayload!: VotePayload;

  upvoteColor!: string;
  downvoteColor!: string;

  @Input() post!: PostModel;

  constructor(
    private voteService: VoteService,
    private postService: PostService,
    private sharedService: SharedService,
    private toastr: ToastrService
  ) { 
    this.votePayload = {
      voteType: undefined,
      postId: undefined

      // vote will not work due to two errors 
      // - post model in backend does not have upvote and downvote values defined 
      // - voteType and postId cannot be termed as undefined as it is not a supported value for enum and string
    }
  }

  ngOnInit(): void {
  }

  upvotePost(){
    this.votePayload.voteType = VoteType.UPVOTE;
    this.vote();
  }

  downvotePost(){
    this.votePayload.voteType = VoteType.DOWNVOTE;
    this.vote();
  }

  private vote(){
    this.votePayload.postId = this.post.id;
    this.voteService.vote(this.votePayload).subscribe(data => {
      this.updateVoteDetails();
    }, error => {
      this.toastr.error(error.error.message);
      throwError(error);
    });
  }

  private updateVoteDetails(){
    this.postService.getPost(this.post.id).subscribe( data => {
      this.post = data;
    })
  }

}
