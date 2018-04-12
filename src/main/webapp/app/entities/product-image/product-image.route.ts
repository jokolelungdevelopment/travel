import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ProductImageComponent } from './product-image.component';
import { ProductImageDetailComponent } from './product-image-detail.component';
import { ProductImagePopupComponent } from './product-image-dialog.component';
import { ProductImageDeletePopupComponent } from './product-image-delete-dialog.component';

export const productImageRoute: Routes = [
    {
        path: 'product-image',
        component: ProductImageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.productImage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'product-image/:id',
        component: ProductImageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.productImage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productImagePopupRoute: Routes = [
    {
        path: 'product-image-new',
        component: ProductImagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.productImage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-image/:id/edit',
        component: ProductImagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.productImage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-image/:id/delete',
        component: ProductImageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.productImage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
