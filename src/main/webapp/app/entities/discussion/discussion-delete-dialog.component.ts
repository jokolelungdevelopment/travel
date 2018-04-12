import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Discussion } from './discussion.model';
import { DiscussionPopupService } from './discussion-popup.service';
import { DiscussionService } from './discussion.service';

@Component({
    selector: 'jhi-discussion-delete-dialog',
    templateUrl: './discussion-delete-dialog.component.html'
})
export class DiscussionDeleteDialogComponent {

    discussion: Discussion;

    constructor(
        private discussionService: DiscussionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.discussionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'discussionListModification',
                content: 'Deleted an discussion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-discussion-delete-popup',
    template: ''
})
export class DiscussionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private discussionPopupService: DiscussionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.discussionPopupService
                .open(DiscussionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
