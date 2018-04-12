import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PreOrder } from './pre-order.model';
import { PreOrderPopupService } from './pre-order-popup.service';
import { PreOrderService } from './pre-order.service';

@Component({
    selector: 'jhi-pre-order-delete-dialog',
    templateUrl: './pre-order-delete-dialog.component.html'
})
export class PreOrderDeleteDialogComponent {

    preOrder: PreOrder;

    constructor(
        private preOrderService: PreOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.preOrderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'preOrderListModification',
                content: 'Deleted an preOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pre-order-delete-popup',
    template: ''
})
export class PreOrderDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private preOrderPopupService: PreOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.preOrderPopupService
                .open(PreOrderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
