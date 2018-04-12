import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PreOrder } from './pre-order.model';
import { PreOrderService } from './pre-order.service';

@Component({
    selector: 'jhi-pre-order-detail',
    templateUrl: './pre-order-detail.component.html'
})
export class PreOrderDetailComponent implements OnInit, OnDestroy {

    preOrder: PreOrder;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private preOrderService: PreOrderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPreOrders();
    }

    load(id) {
        this.preOrderService.find(id)
            .subscribe((preOrderResponse: HttpResponse<PreOrder>) => {
                this.preOrder = preOrderResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPreOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'preOrderListModification',
            (response) => this.load(this.preOrder.id)
        );
    }
}
