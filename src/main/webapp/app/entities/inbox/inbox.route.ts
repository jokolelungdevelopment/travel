import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { InboxComponent } from './inbox.component';
import { InboxDetailComponent } from './inbox-detail.component';
import { InboxPopupComponent } from './inbox-dialog.component';
import { InboxDeletePopupComponent } from './inbox-delete-dialog.component';

export const inboxRoute: Routes = [
    {
        path: 'inbox',
        component: InboxComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.inbox.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inbox/:id',
        component: InboxDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.inbox.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inboxPopupRoute: Routes = [
    {
        path: 'inbox-new',
        component: InboxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.inbox.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inbox/:id/edit',
        component: InboxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.inbox.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inbox/:id/delete',
        component: InboxDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.inbox.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
