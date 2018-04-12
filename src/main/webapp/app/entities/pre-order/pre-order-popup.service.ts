import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { PreOrder } from './pre-order.model';
import { PreOrderService } from './pre-order.service';

@Injectable()
export class PreOrderPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private preOrderService: PreOrderService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.preOrderService.find(id)
                    .subscribe((preOrderResponse: HttpResponse<PreOrder>) => {
                        const preOrder: PreOrder = preOrderResponse.body;
                        preOrder.orderDate = this.datePipe
                            .transform(preOrder.orderDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.preOrderModalRef(component, preOrder);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.preOrderModalRef(component, new PreOrder());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    preOrderModalRef(component: Component, preOrder: PreOrder): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.preOrder = preOrder;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
