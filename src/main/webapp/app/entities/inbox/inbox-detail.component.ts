import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Inbox } from './inbox.model';
import { InboxService } from './inbox.service';

@Component({
    selector: 'jhi-inbox-detail',
    templateUrl: './inbox-detail.component.html'
})
export class InboxDetailComponent implements OnInit, OnDestroy {

    inbox: Inbox;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private inboxService: InboxService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInboxes();
    }

    load(id) {
        this.inboxService.find(id)
            .subscribe((inboxResponse: HttpResponse<Inbox>) => {
                this.inbox = inboxResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInboxes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inboxListModification',
            (response) => this.load(this.inbox.id)
        );
    }
}
