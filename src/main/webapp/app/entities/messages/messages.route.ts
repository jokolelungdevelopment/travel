import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MessagesComponent } from './messages.component';
import { MessagesDetailComponent } from './messages-detail.component';
import { MessagesPopupComponent } from './messages-dialog.component';
import { MessagesDeletePopupComponent } from './messages-delete-dialog.component';

export const messagesRoute: Routes = [
    {
        path: 'messages',
        component: MessagesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.messages.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'messages/:id',
        component: MessagesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.messages.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const messagesPopupRoute: Routes = [
    {
        path: 'messages-new',
        component: MessagesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.messages.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'messages/:id/edit',
        component: MessagesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.messages.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'messages/:id/delete',
        component: MessagesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.messages.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
