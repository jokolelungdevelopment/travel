import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TravelUserInfoModule } from './user-info/user-info.module';
import { TravelCountryModule } from './country/country.module';
import { TravelCityModule } from './city/city.module';
import { TravelJourneyModule } from './journey/journey.module';
import { TravelTripModule } from './trip/trip.module';
import { TravelProductImageModule } from './product-image/product-image.module';
import { TravelCategoryModule } from './category/category.module';
import { TravelCurrencyModule } from './currency/currency.module';
import { TravelProductModule } from './product/product.module';
import { TravelOfferModule } from './offer/offer.module';
import { TravelRequestModule } from './request/request.module';
import { TravelPreOrderModule } from './pre-order/pre-order.module';
import { TravelTransactionModule } from './transaction/transaction.module';
import { TravelNotificationModule } from './notification/notification.module';
import { TravelDiscussionModule } from './discussion/discussion.module';
import { TravelInboxModule } from './inbox/inbox.module';
import { TravelMessagesModule } from './messages/messages.module';
import { TravelFavoriteModule } from './favorite/favorite.module';
import { TravelSlideModule } from './slide/slide.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TravelUserInfoModule,
        TravelCountryModule,
        TravelCityModule,
        TravelJourneyModule,
        TravelTripModule,
        TravelProductImageModule,
        TravelCategoryModule,
        TravelCurrencyModule,
        TravelProductModule,
        TravelOfferModule,
        TravelRequestModule,
        TravelPreOrderModule,
        TravelTransactionModule,
        TravelNotificationModule,
        TravelDiscussionModule,
        TravelInboxModule,
        TravelMessagesModule,
        TravelFavoriteModule,
        TravelSlideModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelEntityModule {}
