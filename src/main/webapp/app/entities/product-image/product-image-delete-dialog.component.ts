import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProductImage } from './product-image.model';
import { ProductImagePopupService } from './product-image-popup.service';
import { ProductImageService } from './product-image.service';

@Component({
    selector: 'jhi-product-image-delete-dialog',
    templateUrl: './product-image-delete-dialog.component.html'
})
export class ProductImageDeleteDialogComponent {

    productImage: ProductImage;

    constructor(
        private productImageService: ProductImageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productImageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productImageListModification',
                content: 'Deleted an productImage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-image-delete-popup',
    template: ''
})
export class ProductImageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productImagePopupService: ProductImagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.productImagePopupService
                .open(ProductImageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
