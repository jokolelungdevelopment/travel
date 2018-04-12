import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PreOrderComponent } from './pre-order.component';
import { PreOrderDetailComponent } from './pre-order-detail.component';
import { PreOrderPopupComponent } from './pre-order-dialog.component';
import { PreOrderDeletePopupComponent } from './pre-order-delete-dialog.component';

export const preOrderRoute: Routes = [
    {
        path: 'pre-order',
        component: PreOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.preOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pre-order/:id',
        component: PreOrderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.preOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const preOrderPopupRoute: Routes = [
    {
        path: 'pre-order-new',
        component: PreOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.preOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pre-order/:id/edit',
        component: PreOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.preOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pre-order/:id/delete',
        component: PreOrderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.preOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
