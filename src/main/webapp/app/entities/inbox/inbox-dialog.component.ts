import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Inbox } from './inbox.model';
import { InboxPopupService } from './inbox-popup.service';
import { InboxService } from './inbox.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-inbox-dialog',
    templateUrl: './inbox-dialog.component.html'
})
export class InboxDialogComponent implements OnInit {

    inbox: Inbox;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private inboxService: InboxService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inbox.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inboxService.update(this.inbox));
        } else {
            this.subscribeToSaveResponse(
                this.inboxService.create(this.inbox));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Inbox>>) {
        result.subscribe((res: HttpResponse<Inbox>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Inbox) {
        this.eventManager.broadcast({ name: 'inboxListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-inbox-popup',
    template: ''
})
export class InboxPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inboxPopupService: InboxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inboxPopupService
                    .open(InboxDialogComponent as Component, params['id']);
            } else {
                this.inboxPopupService
                    .open(InboxDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
