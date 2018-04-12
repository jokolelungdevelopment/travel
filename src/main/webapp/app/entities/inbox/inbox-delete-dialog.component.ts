import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Inbox } from './inbox.model';
import { InboxPopupService } from './inbox-popup.service';
import { InboxService } from './inbox.service';

@Component({
    selector: 'jhi-inbox-delete-dialog',
    templateUrl: './inbox-delete-dialog.component.html'
})
export class InboxDeleteDialogComponent {

    inbox: Inbox;

    constructor(
        private inboxService: InboxService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inboxService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inboxListModification',
                content: 'Deleted an inbox'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inbox-delete-popup',
    template: ''
})
export class InboxDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inboxPopupService: InboxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inboxPopupService
                .open(InboxDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
