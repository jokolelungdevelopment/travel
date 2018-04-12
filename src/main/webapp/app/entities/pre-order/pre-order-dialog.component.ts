import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PreOrder } from './pre-order.model';
import { PreOrderPopupService } from './pre-order-popup.service';
import { PreOrderService } from './pre-order.service';
import { User, UserService } from '../../shared';
import { Product, ProductService } from '../product';
import { Trip, TripService } from '../trip';

@Component({
    selector: 'jhi-pre-order-dialog',
    templateUrl: './pre-order-dialog.component.html'
})
export class PreOrderDialogComponent implements OnInit {

    preOrder: PreOrder;
    isSaving: boolean;

    users: User[];

    products: Product[];

    trips: Trip[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private preOrderService: PreOrderService,
        private userService: UserService,
        private productService: ProductService,
        private tripService: TripService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.productService.query()
            .subscribe((res: HttpResponse<Product[]>) => { this.products = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tripService.query()
            .subscribe((res: HttpResponse<Trip[]>) => { this.trips = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.preOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.preOrderService.update(this.preOrder));
        } else {
            this.subscribeToSaveResponse(
                this.preOrderService.create(this.preOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PreOrder>>) {
        result.subscribe((res: HttpResponse<PreOrder>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PreOrder) {
        this.eventManager.broadcast({ name: 'preOrderListModification', content: 'OK'});
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

    trackProductById(index: number, item: Product) {
        return item.id;
    }

    trackTripById(index: number, item: Trip) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pre-order-popup',
    template: ''
})
export class PreOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private preOrderPopupService: PreOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.preOrderPopupService
                    .open(PreOrderDialogComponent as Component, params['id']);
            } else {
                this.preOrderPopupService
                    .open(PreOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
