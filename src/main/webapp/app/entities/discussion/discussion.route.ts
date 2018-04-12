import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DiscussionComponent } from './discussion.component';
import { DiscussionDetailComponent } from './discussion-detail.component';
import { DiscussionPopupComponent } from './discussion-dialog.component';
import { DiscussionDeletePopupComponent } from './discussion-delete-dialog.component';

export const discussionRoute: Routes = [
    {
        path: 'discussion',
        component: DiscussionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.discussion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'discussion/:id',
        component: DiscussionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.discussion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const discussionPopupRoute: Routes = [
    {
        path: 'discussion-new',
        component: DiscussionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.discussion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'discussion/:id/edit',
        component: DiscussionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.discussion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'discussion/:id/delete',
        component: DiscussionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.discussion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
